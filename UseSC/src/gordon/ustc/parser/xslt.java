package gordon.ustc.parser;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;


public class xslt {
    public static void main(String args[]) {
        //建立transformerFactory对象
        TransformerFactory factory = TransformerFactory.newInstance();
        //以样式表文件输出建立Transformer对象
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(new StreamSource("cdcatalog.xsl"));
            System.out.print(transformer);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //使用streamsource加载xml文件
        StreamSource xmlsource = new StreamSource("cdcatalog.xml");
        //输出streamResult创建与输出文档html文件的关联
        StreamResult output = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        output = new StreamResult(baos);
        //调用transformer对那个transform方法生成html文件
        //转xml文档
        try {
            transformer.transform(xmlsource, output);
            String str = baos.toString();
            System.out.println(str);

        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}