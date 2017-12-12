package javaBean;

public class UserBean {
    private static final String  username="hello";
    private static final String  password="sa17225054";
    private String  name;
    private String  psw;

    public UserBean(String name,String psw){
        this.name = name;
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public String getPsw() {
        return psw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public boolean isUser(){
        if(name.equals(username)&&psw.equals(password))
            return true;
        else
            return false;
    }
}
