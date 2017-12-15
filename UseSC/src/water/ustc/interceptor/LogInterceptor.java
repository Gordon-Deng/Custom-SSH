package water.ustc.interceptor;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

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


    private void afterAction() throws IOException {


        XMLWriter writer = null;
        SAXReader reader = new SAXReader();

        FileWriter output = new FileWriter("/Users/gordon/GitHub/Custom-SSH/UseSC/src/log.xml");
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("GBK");
        SimpleDateFormat format_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        e_Time = System.currentTimeMillis();
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("log");
        Element action = root.addElement("action");

        Element name = action.addElement("name");
        name.setText(action_Name);

        Element start = action.addElement("s-time");
        start.setText(format_time.format(s_Time));

        Element end = action.addElement("e-time");
        end.setText(format_time.format(e_Time));

        Element result = action.addElement("result");
        result.setText(action_Name);

        writer = new XMLWriter(output, format);
        writer.write(doc);
        writer.close();


    }
}
