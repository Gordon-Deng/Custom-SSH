package water.ustc.action;

import javax.servlet.http.HttpServletRequest;

public class RegisterAction {
	 public String handleRegister(HttpServletRequest request){
	        System.out.println("register success!");
	        return "success";
	    }
}
