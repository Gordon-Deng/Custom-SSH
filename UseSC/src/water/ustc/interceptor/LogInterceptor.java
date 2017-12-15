package water.ustc.interceptor;

import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class LogInterceptor {
    private long s_Time;
    private long e_Time;
    public String action_Name;
    public String action_Result;

    public String getInterceptor_ActionName() {
        return action_Name;
    }

    public String getInterceptor_ActionResult() {
        return action_Result;
    }

    public void setInterceptor_ActionName(String action_Name) {
        this.action_Name = action_Name;
    }

    public void setInterceptor_ActionResult(String action_Result) {
        this.action_Result = action_Result;
    }



    private void preAction() throws InterruptedException {
        s_Time = System.currentTimeMillis();
        Thread.sleep(1000);
    }


    private void afterAction(){
        XMLWriter writer = null;
        SAXReader reader = new SAXReader();

        
    }
}
