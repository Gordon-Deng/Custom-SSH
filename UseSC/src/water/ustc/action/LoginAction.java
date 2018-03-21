package water.ustc.action;

import JavaBean.Userbean;
import java.sql.SQLException;

public class LoginAction {
	public Userbean handleBean = null;//添加空对象

	public String handleLogin(String userName, String userPassword)
            throws SQLException, ClassNotFoundException {

	   /* UserBean 对象属性初始化代码语句移除
          Userbean handleBean = new Userbean(userName, userPassword);
        */

        if (handleBean.signIn()) {
            System.out.println("login success!");
            return "success";
        } else {
            System.out.println("login fail!");
            return "failure";
        }
    }


    public void setHandleBean(Userbean handleBean) //提供set方法实现依赖注入
    {
        this.handleBean = handleBean;
    }
}
