package sc.ustc.controller;

import gordon.ustc.xml.ActionMappingManager;
import javaBean.ActionBean;
import javaBean.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "SimpleController")
public class SimpleController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,NullPointerException {

        String url = request.getRequestURI();
        String actionName = url.substring(url.lastIndexOf("/") + 1,url.indexOf(".sc"));
        ActionBean action_bean = ActionMappingManager.getInstance().getAction().get(actionName);
        String className = action_bean.getClassname();
        String method = action_bean.getMethod();

        try {
            /*发射创建执行
             *Class.forName()返回指定类名对象
             *clazz.newInstance()相当于 New xxx()
             */
            Class<?> clazz = Class.forName(className);
            java.lang.Object obj = clazz.newInstance();
            //动态加载类方法
            Method mtd = clazz.getDeclaredMethod(method,HttpServletRequest.class,HttpServletResponse.class);
            String flag_rtn = (String) mtd.invoke(obj, request, response);
            System.out.println(flag_rtn);
            //因为如果不匹配，使之跳转NoResource.jsp，显示没有资源提示
            if(flag_rtn.equals("fail")){
                request.getRequestDispatcher("NoResource.jsp").forward(request,response);
            }
            //读取Bean
            Result result = action_bean.getResults().get(flag_rtn);
            String resultName = result.getName();
            String type = result.getType();
            String value = result.getValue();
            //根据类名动态新建对象，调用其方法，依照返回字符串的不同，绑定不同result下的方法，跳转不同页面
            if("forward".equals(type)){
                request.getRequestDispatcher(value).forward(request,response);
            }
            else
                response.sendRedirect(request.getContextPath()+ "/"+ value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
