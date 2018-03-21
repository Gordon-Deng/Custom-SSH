package gordon.ustc.parser;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class xmlParser {
	public  HashMap<Integer, Element>
            hashMapActions=new HashMap<Integer, Element>();
	public  HashMap<Integer, Element>
            hashMapInterceptor=new HashMap<Integer, Element>();//增加拦截器HashMap

	public int actionCount = 0;
	public int interceptorCount = 0;
	public  List<HashMap> saxParser(String path) {
		try {   
			
			File f = new File(path);   
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			//遍历根节点下的子节点
	        listNodes(root);
	        
		} catch (Exception e) {   
			e.printStackTrace();   
		  }
		List<HashMap> Elements = new ArrayList<HashMap>();
		Elements.add(hashMapActions);
		Elements.add(hashMapInterceptor);

		return  Elements;   
	}
	
    public  void listNodes(Element node){

        //如果当前节点为action，则存入hashMapActions
        if(node.getName().equals("action")){  
             
             hashMapActions.put(actionCount++,node);
        } 
        
        //如果当前节点为Interceptor，则存入Interceptor
        if(node.getName().equals("interceptor")){  

             hashMapInterceptor.put(interceptorCount++,node);
        } 

        /*
        *  遍历ArrayList集合找子节点：
        *  1）第一种遍历方法使用foreach遍历List
        *  2）第二种遍历，把链表变为数组相关的内容进行遍历
        *  3）第三种遍历 使用迭代器进行相关遍历
         */

        //使用迭代器遍历更快
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){  
            Element e = iterator.next();
            listNodes(e);  
        }

    }  
}