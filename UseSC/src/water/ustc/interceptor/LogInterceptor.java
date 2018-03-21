package water.ustc.interceptor;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class LogInterceptor {
	private long s_time;
	private long e_time;
	public String action_name;
	public String action_result;

	public void preAction() throws InterruptedException{
		s_time = System.currentTimeMillis();//获得aciton执行前系统的当前时间
	}

	public void afterAction() throws IOException {

		//格式化写入XML样式

		SimpleDateFormat formattime = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");//格式化时间戳
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");// 格式化编码格式
		FileWriter outStream = new FileWriter(
				"/Users/gordon/GitHub/UseSC/src/log.xml");

		e_time = System.currentTimeMillis();//获得aciton执行后系统的当前时间

		/*
		 * 新建Document，使用addElemnet("node")建立标签
		 * 生成Log.xml
		 */
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("log");
		Element action = root.addElement("action");

		Element name = action.addElement("name");
		name.setText(action_name);

		Element start = action.addElement("s-time");
		start.setText(formattime.format(s_time));

		Element end = action.addElement("e-time");
		end.setText(formattime.format(e_time));

		Element result = action.addElement("result");
		result.setText(action_result);;

		XMLWriter writer = new XMLWriter(outStream, format);
		writer.write(doc);
		writer.close();
	}

	public void setActionName(String action_name){
		this.action_name = action_name;
	}

	public String getActionName(){
		return this.action_name;
	}

	public void setActionResult(String action_result){
		this.action_result = action_result;
	}

	public String getActionResult(){
		return this.action_result;
	}

}
