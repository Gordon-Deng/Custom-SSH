package JavaBean;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class classPathXmlApplicationContext implements BeanFactory {
    @Override
    public Object getBean(String id) {
        return beans.get(id);
    }

    private Map<String ,Object> beans = new HashMap<String ,Object>();//构造方法

    public classPathXmlApplicationContext() throws Exception{
        SAXReader reader = new SAXReader();
        Document doc = reader.read("/Users/gordon/GitHub/UseSC/src/di.xml");
        Element root = doc.getRootElement();
        List list  = root.elements("bean");

        //这里没使用迭代器，考试完后修改
        for (int i = 0; i < list.size(); i++) {
            Element element = (Element) list.get(i);//取第i个bean元素，以id为凭证
            String id = element.attributeValue("id");//获得反射属性
            String clazz = element.attributeValue("class");
            Object o = Class.forName(clazz).newInstance();//反射生成类的对象

            System.out.println(id);
            System.out.println(clazz);
            beans.put(id, o);//将id和对象o存入Map中

            if(element.elements("field") != null){
                //遍历field域
                for (Element fieldElement : (List<Element>)element.elements("field"))
                {
                    //获得依赖属性
                    String name = fieldElement.attributeValue("name");
                    //获得依赖
                    String beanInstance = fieldElement.attributeValue("bean-ref");
                    Object beanObject = beans.get(beanInstance);//取得被注入实例
                    Map<String ,String[] > befBeanMap = new HashMap<>();
                    befBeanMap.put("loginAction",new String []{"handleBean"});
                    populate(befBeanMap, (Userbean) new classPathXmlApplicationContext().getBean("loginAction"));

                }
            }
        }
    }

    private void populate(Map<String,String[]> map, Userbean u){
        try {
            Map<String,String[]> params=map;
            //1 获得 java Bean的描述信息
            BeanInfo info = Introspector.getBeanInfo(Userbean.class);
            //2 获得 Userbean中的属性信息
            PropertyDescriptor[] pds =info.getPropertyDescriptors();
            //3 遍历属性信息
            for (PropertyDescriptor pd : pds) {
                String[] param = params.get(pd.getName());
                if (param!=null && param.length>0) {
                    pd.getWriteMethod().invoke(u, param[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
