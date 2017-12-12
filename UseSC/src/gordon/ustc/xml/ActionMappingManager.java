package gordon.ustc.xml;

import javaBean.ActionBean;
import javaBean.Result;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ActionMappingManager {
    //依次SAX迭代法，以Map方式储存解析XML出来的action,result以及其内容
    public Map<String, ActionBean> getAction() {
        Map<String, ActionBean> allActions = new HashMap<String, ActionBean>();
        try {
            //reder读入文档
            SAXReader reader = new SAXReader();
            //获取src目录下的controller.xml，放在src下可以直接引用，也可以直接写绝对地址
            InputStream instream = this.getClass().getResourceAsStream("/controller.xml");
            //生成Document
            Document doc = reader.read(instream);
            //获得根节点
            Element root = doc.getRootElement();
            //获得controller节点
            Element root_next = root.element("controller");
            //以List的形式存入根节点下的子节点
            List<Element> list_action = root_next.elements("action");
            //胖空，找不到标签时返回   "不可识别的action请求"  的错误，需求1.5
            if (list_action.size() == 0){
                System.out.println("不可识别的action请求");
            }
            //循环取出action,result,method，为避免解析器功能冗余，将数据存入Bean中。
            for (Element ele_action : list_action) {
                ActionBean actionbean = new ActionBean();
                Element ele_name = ele_action.element("name");
                actionbean.setName(ele_name.getText());
                //对症下药
                Element ele_class = ele_action.element("class");
                actionbean.setClassname(ele_class.element("name").getText());
                actionbean.setMethod(ele_class.element("method").getText());

                Map<String, Result> results = new HashMap<String, Result>();
                //没有匹配资源，响应客户端："没有请求的资源"，需求1。7
                Iterator<Element> ite = ele_action.elementIterator("result");
                if (ite == null){
                    System.out.println("没有请求的资源");
                }
                while (ite.hasNext()) {
                    Element ele_result = ite.next();
                    Result result = new Result();
                    result.setName(ele_result.element("name").getText());
                    result.setType(ele_result.element("type").getText());
                    result.setValue(ele_result.element("value").getText());
                    results.put(result.getName(), result);
                }
                actionbean.setResults(results);
                allActions.put(actionbean.getName(), actionbean);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("error", e);
        }
        return allActions;
    }

    private ActionMappingManager(){}
    private static ActionMappingManager manager = null;

    public static ActionMappingManager getInstance(){
        if (manager == null){
            manager = new ActionMappingManager();
        }
        return manager;
    }
}
