package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class XMLUtil {

	/**
	 * 读取程序执行参数
	 * @param xmlPath 配置文件路径
	 * @return HashMap key:参数名， value：参数值
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws DocumentException 
	 */
	public static Map<String, Object> readConfig(String xmlPath) throws ParserConfigurationException, SAXException, IOException, DocumentException{
		Map<String, Object> map = new HashMap<String,Object>();
		//读取xml
		 SAXReader reader = new SAXReader();                
	     Document   doc = reader.read(xmlPath);
         Element root = doc.getRootElement();
         List<Element>list = root.elements();
         for(Element e:list){
        	 String key = e.attributeValue("name");
        	 String value = e.getText();
        	 map.put(key,value);
         }
		 
		return map;
	}
}
