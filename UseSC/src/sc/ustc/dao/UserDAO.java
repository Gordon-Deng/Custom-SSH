package sc.ustc.dao;

import JavaBean.Userbean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends BaseDAO{
    private Connection conn = null;//多个方法用到该参数，消除重复代码
    private Statement st = null;
    private ResultSet rs = null;
    private static final String driver = "com.mysql.jdbc.Driver";//Mysql
    private static final String url = "jdbc:mysql://localhost:3306/ustc?useUnicode=true&characterEncoding=utf-8";
//    private static final String driver = "org.sqlite.JDBC";//Sqlite
//    private static final String url = "jdbc:sqlite:/Users/gordon/Desktop/ustc.sqlite";

    public UserDAO( String userName, String userPassword) {
        super(driver, url, userName, userPassword);
    }

    @Override
    public Userbean query(String sql) throws SQLException, ClassNotFoundException {
      conn = openDBConnection();
      String querySql = "select * from j2ee where user_name = '" + sql + "'";
      System.out.println("当前sql :" + querySql);
      st = conn.createStatement();
      rs = st.executeQuery(querySql);
      Userbean ub = null;
      if (rs.next())//到第一行记录
      {
          System.out.println("uN : " + rs.getString(2) + "uP : " + rs.getString(3));
          ub = new Userbean(rs.getString(2), rs.getString(3));//返回新的 UserBean 对象
      }

      return ub;//否则返回NULL
    }

    @Override
    public boolean insert(String sql) {
        return false;
    }

    @Override
    public boolean update(String sql) {
        return false;
    }

    @Override
    public boolean dalete(String sql) {
        return false;
    }
}
