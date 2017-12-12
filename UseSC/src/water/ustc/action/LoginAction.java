package water.ustc.action;
import javaBean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAction {
    public String handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rt_flag = null;
        String username = request.getParameter("username");
        String psw = request.getParameter("psw");
        UserBean userBean = new UserBean(username, psw);
        if ("1".equals(userBean.getName())&&"1".equals(userBean.getPsw())){
            rt_flag = "success";
        }
        else {
            rt_flag = "failure";
        }
        return rt_flag;
    }
}