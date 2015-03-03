package server.database;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class DataImporter {
	
	public static void importDataFromFile(String filename) {
		File xmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile); //Can use URI instead of xmlFile
		//optional, but recommended. Read this
		// http://stackoverflow.com/questions/13786607/normalization-in-domparsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		indexerData = new IndexerData(root);
	}
	
	public static String getValue(Element element) {
		String result = "";
		Node child = element.getFirstChild();
		result = child.getNodeValue();
		return result;
	}

}
