package server.importer;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.*;

import org.apache.commons.io.*;


public class DataImporter {
	
	
	private static Database database;
	
	private void create(String sql){
		
	}
	
	public static void main(String[] args) {	
		try{
			copyfiles(args[0]);
			
			Database.initialize();
			database = new Database();
			database.startTransaction();
			database.recreateTables("database.sql");
			
			File xmlFile = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile); 
		
			doc.getDocumentElement().normalize();
			parseUsers(doc.getElementsByTagName("user"));
            parseProjects(doc.getElementsByTagName("project"));
            database.endTransaction(true);
			
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
	
	static void copyfiles(String xmlFilename) throws IOException{
		File xmlFile = new File(xmlFilename);
		File dest = new File("Records");
		if(!xmlFile.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
			FileUtils.deleteDirectory(dest);
		FileUtils.copyDirectory(xmlFile.getParentFile(), dest);
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
	            parseBatches(projElem.getElementsByTagName("images"),projectId,fields);
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
            
            database.getUserAccess().addUser(user);
        }
	}

	private static void parseBatches(NodeList batchList, int projectId, ArrayList<Field> fields) throws DatabaseException {
		for(int i =0;i<batchList.getLength();i++){
			Element batchElem = (Element) batchList.item(i);
			
			String file = batchElem.getElementsByTagName("image").item(0).getTextContent();
			int projectid = projectId;
			Boolean complete = false;
			Boolean available = false;
			Boolean checkedout = false;
			
	        Batch batch = new Batch();
	        batch.setFile(file);
	        batch.setProjectid(projectid);
	        batch.setComplete(complete);
	        batch.setAvailable(available);
	        batch.setCheckedout(checkedout);
	        
	        int batchId = database.getBatchAccess().addBatch(batch).getId();
	        
	        parseRecords(batchElem.getElementsByTagName("records"), batchId, fields);
		}
	}

	private static void parseRecords(NodeList recordList, int batchId, ArrayList<Field> fields) throws DatabaseException {
		for(int i =0;i<recordList.getLength();i++){
			Element recordElem = (Element) recordList.item(i);
			int batch_id = batchId;
			int row_number = i;
			
			Record record = new Record();
			record.setBatch_id(batch_id);
			record.setRow_number(row_number);
			int recordId = database.getRecordAccess().addRecord(record).getId();
		
			parseValues(recordElem.getElementsByTagName("values"),recordId,fields);
		}
	}

	private static void parseValues(NodeList multivalueList, int recordId, ArrayList<Field> fields) throws DatabaseException {
		for(int i =0;i<multivalueList.getLength();i++){
			Element multivalueElem = (Element) multivalueList.item(i);
			NodeList valueList = multivalueElem.getElementsByTagName("value");
//			System.out.println(valueList.getLength());
			for(int j = 0; j< valueList.getLength();j++){
				Element valueElem = (Element)valueList.item(j);
				String inputvalue = valueElem.getTextContent();
				int record_id = recordId;
				
				Inputvalue value = new Inputvalue();
				value.setInputvalue(inputvalue);
				value.setRecord_id(record_id);
				value.setField_id(fields.get(j).getId());
				
				database.getInputValAccess().addValue(value);
//				System.out.println(fields.get(j).getId());
//				System.out.println(inputvalue);
//
//				System.out.println("Press Any Key To Continue...");
//		        new java.util.Scanner(System.in).nextLine();
			}
		}
	}

	private static ArrayList<Field> parseFields(NodeList fieldList,int projectId) throws DatabaseException {
		
		ArrayList<Field> fields = new ArrayList<Field>();
		int columnnumber= 0;
		for(int i =0;i<fieldList.getLength();i++){
			Element fieldElem = (Element) fieldList.item(i);
			int project_id = projectId;
			columnnumber = i;
			String title = fieldElem.getElementsByTagName("title").item(0).getTextContent();
	        String xcoord = fieldElem.getElementsByTagName("xcoord").item(0).getTextContent();
	        String width = fieldElem.getElementsByTagName("width").item(0).getTextContent();
	        String helphtml = fieldElem.getElementsByTagName("helphtml").item(0).getTextContent();
	        String knowndata = "";
	        if(fieldElem.getElementsByTagName("knowndata").item(0) != null){
	        	knowndata = fieldElem.getElementsByTagName("knowndata").item(0).getTextContent();
	        }
	    
	        Field field = new Field();
	        field.setProject_id(project_id);
	        field.setTitle(title);
	        field.setXcoord(Integer.parseInt(xcoord));
	        field.setWidth(Integer.parseInt(width));
	        field.setHelphtml(helphtml);
	        field.setKnowndata(knowndata);
	        field.setColumnnumber(columnnumber);
	        
	        fields.add(field);
	        database.getFieldAccess().addField(field);
		}
		return fields;
	}

}

