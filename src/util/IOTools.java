package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.xml.sax.SAXException;

import vo.Point;

public class IOTools {

	public static String configureFilePath="./src/configure.xml";
	public static Map<String,Object> configure = null;
	static {
		try {
			configure = XMLUtil.readConfig(configureFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static List<Point> inputData() throws IOException{
		List<Point> points = new ArrayList<Point>();
		//1. 获取参数信息
		String dataPath = configure.get("dataPath").toString();
		String separator = configure.get("delimiter").toString();
		int start = Integer.valueOf(configure.get("start").toString());
		int dimension = Integer.valueOf(configure.get("dimension").toString());
		int pointIdPosition = Integer.valueOf(configure.get("pointId").toString());
		
		//2. 读取文件内容
		//1.读取文本文件
				String encoding = "utf-8";
				File file = new File(dataPath);
				if(file.isFile() && file.exists()){
					InputStreamReader read = 
							new InputStreamReader(new FileInputStream(file),encoding);
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineText = null;
					int counter = 0;
					while((lineText = bufferedReader.readLine()) != null){
						Point point = new Point();
						String []attrs_text = lineText.split(separator);
						if(-1 == pointIdPosition){
							point.setId(""+(counter++));
						}else{
							point.setId(attrs_text[pointIdPosition]);
						}
						double []attrs = new double[dimension];
						for(int i = start ; i < dimension+start; i++){
							attrs[i] = Double.valueOf(attrs_text[i]);
						}
						point.setAttrs(attrs);
						points.add(point);
						
					}
				}
//		Random random = new Random();
//		int deleteSize = (int)(0.0*8000);
//		for(int i = deleteSize; i >0 ; i--){
//			int index = (int)random.nextDouble()*8000;
//			points.remove(index);
//		}
				
		return points;
	}
	
}
