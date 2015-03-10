package server.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;

import org.w3c.dom.*;

import server.ServerException;

public class DataImporter {
	
	public static IndexerData importDataFromFile(String filename) throws ServerException {
		File xmlFile = new File(filename);

		// copy the data files
		try {
			File sourceDir = xmlFile.getParentFile();
			File targetDir = new File("./projectData");
			FileUtils.copyDirectory(sourceDir, targetDir);
		} catch(IOException e) {
			System.out.println("Error: could not copy directories");
			e.printStackTrace();
			return null;
		}

		// Parse the xml
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
		try {
			IndexerData iData = new IndexerData(root);
			return iData;
		} catch(DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}
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
		try {
			IndexerData iData = DataImporter.importDataFromFile(args[0]);
			iData.populateDatabase();
		} catch(ServerException e) {
			System.out.println("Error: failed to import data from file");
			e.printStackTrace();
		}
	}

}
