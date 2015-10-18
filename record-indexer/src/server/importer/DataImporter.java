package server.importer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.*;



public class DataImporter {
	private static Database database = new Database();
	
	public static void main(String[] args) {	
		try{
			database.deleteData();
			database.startTransaction();
			// When in conflict use the class from org.w3c.dom.
			// Examples: Document, Element, Node, NodeList
			File xmlFile = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile); //Can use URI instead of xmlFile
			//optional, but recommended. Read this
			// http://stackoverflow.com/questions/13786607/normalization-in-domparsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			//parseUsers(doc.getElementsByTagName("user"));
            //parseProjects(doc.getElementsByTagName("project"));
            database.endTransaction(true);
			
			
//			Element root = doc.getDocumentElement();
//			IndexerData indexerData = new IndexerData(root);
		}
		catch(SAXException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ParserConfigurationException e){
            e.printStackTrace();
        } catch (DatabaseException e) {
			e.printStackTrace();
		}
		
	}
		
	private static void parseProjects(NodeList projectList) {
		 for(int i =0;i<projectList.getLength();i++){
	            Element projElem = (Element) projectList.item(i);
	            String title = projElem.getElementsByTagName("title").item(0).getTextContent();
	            String recordsPerImage = projElem.getElementsByTagName("recordsperimage").item(0).getTextContent();
	            String firstYCoord = projElem.getElementsByTagName("firstycoord").item(0).getTextContent();
	            String recordHeight = projElem.getElementsByTagName("recordheight").item(0).getTextContent();

	            Project project = new Project();
	            project.setTitle(title);
	            project.setRecordheight(Integer.parseInt(recordHeight));
	            project.setRecordsperimage(Integer.parseInt(recordsPerImage));
	            project.setFirstycoordinate(Integer.parseInt(firstYCoord));
	            int projectId = database.getProjectAccess().addProject(project).getId();

	            ArrayList<Field> fields = parseFields(projElem.getElementsByTagName("field"),projectId);
	            parseBatches(projElem.getElementsByTagName("image"),projectId,fields);
	        }
	}
	
	private static void parseUsers(NodeList elementsByTagName) {
		// TODO Auto-generated method stub
		
	}

	private static void parseBatches(NodeList elementsByTagName, int projectId, ArrayList<Field> fields) {
		
	}

	private static ArrayList<Field> parseFields(NodeList elementsByTagName,int projectId) {
		return null;
	}



	public static ArrayList<Element> getChildElements(Node node) {
		ArrayList<Element> result = new ArrayList<Element>();
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
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
}

