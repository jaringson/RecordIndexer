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
	
	
	private static Database database;
	
	private void create(String sql){
		
	}
	
	public static void main(String[] args) {	
		try{
			Database.initialize();
			database = new Database();
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
			parseUsers(doc.getElementsByTagName("user"));
            parseProjects(doc.getElementsByTagName("project"));
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
		
	private static void parseProjects(NodeList projectList) throws DatabaseException {
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
	
	private static void parseUsers(NodeList userList) throws DatabaseException {
		for(int i =0;i<userList.getLength();i++){
            Element userElem = (Element) userList.item(i);
            String username = userElem.getElementsByTagName("username").item(0).getTextContent();
            String password = userElem.getElementsByTagName("password").item(0).getTextContent();
            String firstname = userElem.getElementsByTagName("firstname").item(0).getTextContent();
            String lastname = userElem.getElementsByTagName("lastname").item(0).getTextContent();
            String email = userElem.getElementsByTagName("email").item(0).getTextContent();
            String indexedrecords = userElem.getElementsByTagName("indexedrecords").item(0).getTextContent();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setIndexrecords(Integer.parseInt(indexedrecords));
            
            int projectId = database.getUserAccess().addUser(user).getId();

            ArrayList<Field> fields = parseFields(userElem.getElementsByTagName("field"),projectId);
            parseBatches(userElem.getElementsByTagName("batches"),projectId,fields);
        }
	}

	private static void parseBatches(NodeList batchList, int projectId, ArrayList<Field> fields) {
		for(int i =0;i<batchList.getLength();i++){
			Element batchElem = (Element) batchList.item(i);
			String title = batchElem.getElementsByTagName("title").item(0).getTextContent();
	        String xcoord = batchElem.getElementsByTagName("xcoord").item(0).getTextContent();
	        String width = batchElem.getElementsByTagName("width").item(0).getTextContent();
	        String helphtml = batchElem.getElementsByTagName("helphtml").item(0).getTextContent();
	        String knowndata = batchElem.getElementsByTagName("knowndata").item(0).getTextContent();
	        
	        Batch batch = new Batch();
	        
	         
		}
	}

	private static ArrayList<Field> parseFields(NodeList fieldList,int projectId) {
		for(int i =0;i<fieldList.getLength();i++){
			Element fieldElem = (Element) fieldList.item(i);
			String title = fieldElem.getElementsByTagName("title").item(0).getTextContent();
	        String xcoord = fieldElem.getElementsByTagName("xcoord").item(0).getTextContent();
	        String width = fieldElem.getElementsByTagName("width").item(0).getTextContent();
	        String helphtml = fieldElem.getElementsByTagName("helphtml").item(0).getTextContent();
	        String knowndata = fieldElem.getElementsByTagName("knowndata").item(0).getTextContent();
	        
	        Batch batch = new Batch();
	        
	         
		}
		return null;
	}



	/*public static ArrayList<Element> getChildElements(Node node) {
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
	}*/
}

