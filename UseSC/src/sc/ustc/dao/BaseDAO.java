package sc.ustc.dao;

import JavaBean.Userbean;

import java.sql.*;

public abstract class BaseDAO {
    protected String driver, url, userName, userPassword;//protected属性可通过UserBean继承

    public BaseDAO(String driver, String url, String userName, String userPassword) {
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Connection openDBConnection() throws ClassNotFoundException, SQLException {

        /**
         * 获取数据库连接对象
         * @return 数据库连接对象
         * @throws ClassNotFoundException
         * @throws SQLException
         */

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url,userName,userPassword);
        System.out.print("当前连接是"+ conn);
        return conn;
    }

    public boolean closeDBConnection(Statement st, ResultSet rs, Connection conn)  {
        /**
         * @Description: 消除st,rs,conn之间错误时的耦合，正确关闭连接
         * @param st :
         * @param rs :
         * @param conn :
         * @return: boolean
         * @Date:   04:46 2017/12/28
         */

        try{
            if(rs != null){
                rs.close();
                rs = null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (st != null) {
                    st.close();
                    st = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("成功关闭数据库连接");
                    return true;
                }
            }
        }
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    public abstract Userbean query(String sql) throws SQLException, ClassNotFoundException;

    public abstract boolean insert(String sql);//这次作业还没用的以下几个方法，暂不做实现

    public abstract boolean update(String sql);

    public abstract boolean dalete(String sql);

}
