package gordon.ustc.transformer;

import java.io.*;
import org.w3c.dom.Document;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.*;

public class xmlTransformer {
    private String myXml,myXsl,outputFile;

    public xmlTransformer(String xmlfile,String xslfile,String outputfile){
        this.myXml = xmlfile;
        this.myXsl = xslfile;
        this.outputFile = outputfile;
    }

    public void translateHtml(){

        try{
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(myXml);
            TransformerFactory factory=TransformerFactory.newInstance();

            Transformer transformer=factory.newTransformer(new StreamSource(myXsl));
            transformer.setOutputProperty(OutputKeys.ENCODING,"GB2312");
            PrintWriter pw=new PrintWriter(new FileOutputStream(outputFile));
            StreamResult result=new StreamResult(pw);

            transformer.transform(new DOMSource(doc),result);

        }catch(Exception exp){
            exp.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        xmlTransformer myto=new xmlTransformer(
//                "src/success_view.xml",
//                "src/success_view.xsl",
//                "src/success_view.html"
//        );
//        myto.translateHtml();
//
//    }
}