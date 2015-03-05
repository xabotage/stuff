package server.database;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class DataImporter {
	
	public static IndexerData importDataFromFile(String filename) {
		File xmlFile = new File(filename);
		xmlFile.getAbsolutePath();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
		} catch(Exception e) {
			System.out.println("Error: could not load xml");
			e.printStackTrace();
			return null;
		}
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		return new IndexerData(root);
	}
	
	public static ArrayList<Element> getChildElements(Node node) {
		ArrayList <Element> result = new ArrayList<Element>();
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i ++) {
			Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE){
				result.add((Element)child);
			}
		}
		return result;
	}
	
	public static String getValue(Element element) {
		String result = "";
		Node child = element.getFirstChild();
		result = child.getNodeValue();
		return result;
	}
	
	public static void main(String[] args) {
		DataImporter.importDataFromFile(args[0]);
	}

}
