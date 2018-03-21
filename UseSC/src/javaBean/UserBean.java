package JavaBean;

import sc.ustc.dao.UserDAO;

import java.sql.SQLException;

public class Userbean {
    private String  userId, userName, userPass;//userId可有可无

    /*
    移除作业5中UserBean对象属性初始化代码语句
     */
    public Userbean(String username,String password)//构造方法中初始化父类域
    {
        this.userName = username;
        this.userPass = password;
    }

    public boolean signIn() throws SQLException, ClassNotFoundException {
        UserDAO queryDao = new UserDAO("root", "123456");
        Userbean queryObject = queryDao.query(userName);//数据库中取出 password 属性，构造一个新的 UserBean 对象
        if (queryObject == null)//判断是否为 NULL
        {
            return false;
        }
        else if (queryObject.getUserPass().equals(userPass)){
            return true;
        }
        else {
            return false;
        }

    };

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
