package sc.ustc.controller;

import JavaBean.Userbean;
import gordon.ustc.parser.xmlParser;
import org.dom4j.Element;
import water.ustc.interceptor.CglibProxy;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import JavaBean.classPathXmlApplicationContext;

public class SimpleController extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String uN = request.getParameter("username");//取得用户名和密码
		String uP = request.getParameter("password");
		PrintWriter out = response.getWriter();
		//截取请求的URL最后的/后面的内容作为Action
		String url =request.getRequestURI();
		String actionName = url.substring(url.lastIndexOf("/") + 1,url.indexOf(".sc"));

        xmlParser saxXml = new xmlParser();
		String path="/Users/gordon/GitHub/UseSC/src/controller.xml";
		List<HashMap> Elements = saxXml.saxParser(path);
        try {
            classPathXmlApplicationContext ctx = new classPathXmlApplicationContext();
            if (ctx.getBean(actionName) == null){
                out.write("<p>不可识别该请求<p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        HashMap<Integer, Element> actions = Elements.get(0);
		HashMap<Integer, Element> interceptors = Elements.get(1);//在E2的基础上，新增拦截器HashMap
		boolean action_found = false;//判断action是否被找到
		for(int i=0;i<actions.size();i++){
			Element action = (Element) actions.get(i);
			String resultName = null;

			if(action.attributeValue("name").equals(actionName)){

				try {
					//E2反射机制
					Class<?> myclass = Class.forName(action.attributeValue("class"));
					Method method = myclass.getMethod(action.attributeValue("method"),String.class,String.class);
					//判断action中有无ref的拦截器
					if(action.element("interceptor-ref") != null){
						for(int k=0;k<interceptors.size();k++){
							Element interceptor = (Element)interceptors.get(k);
							//动态代理相应的拦截器
							if(interceptor.attributeValue("name").equals(action.element("interceptor-ref").attributeValue("name"))){
								CglibProxy cglibAction = new CglibProxy();			//使用cglib类创建代理
								Object actionObject = (Object)cglibAction.getInstance(myclass.newInstance());
                                cglibAction.setActionName(actionName);
                                //执行代理，返回action信息
                                resultName = (String)cglibAction.intercept(actionObject, method, new String[]{uN,uP}
                                , null);//有拦截器时的action,传入uN,uP
								System.out.println("拦截后返回的result：" + resultName);
							}else{

								System.out.println("找不到对应的拦截器：" + interceptor.attributeValue("name"));
							}

						}
					}else{
						resultName = (String)method.invoke(myclass.newInstance(),uN,uP);//没有拦截器时的action,传入uN,uP
						System.out.println("直接执行action返回的result：" + resultName);
					}

					//反射机制
					//Class myclass = Class.forName(actionValue);
					//Method method = myclass.getMethod(action.attributeValue("method"),null);
					//resultName = (String)method.invoke(myclass.newInstance(),null);
					//System.out.println("返回的result的名称：" + resultName);

					//创建results节点列表
					List<Element> results = action.elements("result");
					//System.out.println(results.size());
					for(int j=0;j<results.size();j++){
						Element result = (Element) results.get(j);
						System.out.println("result的名称：" + result.attributeValue("name"));
						if(result.attributeValue("name").equals(resultName)){
							//System.out.println("type的名称：" + result.attributeValue("type"));
							if(result.attributeValue("type").equals("forward"))
							{
								request.getRequestDispatcher(result.attributeValue("value")).forward(request,response);
							}else if(result.attributeValue("type").equals("redirect")){
								response.sendRedirect(result.attributeValue("value"));
							}
						}
					}
					out.write("<p>没有请求的资源<p>");

					} catch (Exception e) {
						e.printStackTrace();
					} catch (Throwable throwable) {
                    throwable.printStackTrace();

                }
            }
			}
			if(action_found!=true){
				out.write("<p>不可识别该请求<p>");
			}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);

	}

}