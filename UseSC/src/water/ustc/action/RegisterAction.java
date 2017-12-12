package water.ustc.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterAction {

    public String handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String []Rstr = {"success", "fail"};
        int Rnum = (int)(Math.random()*2);
        return Rstr[Rnum];
    }
}
