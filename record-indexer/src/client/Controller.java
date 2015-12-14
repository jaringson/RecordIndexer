package client;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;

public class Controller {
	
	public static void initialize(String host,int port){
		cc = new ClientCommunicator(host,port);
	}
	private static ClientCommunicator cc;
	private static BatchState BState;
	
	private static User user = new User();
	private static Project project = new Project();
	private static Batch batch = null;

	
	public static void Save() throws FileNotFoundException{
		
		BState.setSaveData();
		XStream xs = new XStream(new DomDriver());
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(user.getUsername()+".xml"));
		xs.toXML(BState, os);
	}
	public static void reinitialize(BatchState bState){
		BState = bState;
		if(bState.user!=null){
			batch = bState.batch;
			user = bState.user;
			project = bState.project;
		}
	}
	public static Boolean validateUser(String username, String password) {
		
		ValidateUser_Params params = new ValidateUser_Params();
		params.setPassword(password);
		params.setUsername(username);
		ValidateUser_Result result;
		try {
			result = cc.validateUser(params);
			if(result != null && result.toString() != "FALSE"){
				user = result.getUser();
				return true;
			}
			else{
				return false;
			}
				
		} catch (ClientException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Project> getProjects(){
		GetProjects_Params params = new GetProjects_Params();
		params.setPassword(user.getPassword());
		params.setUsername(user.getUsername());

		GetProjects_Result result;
		ArrayList<Project> allprojects = new ArrayList<Project>();
		try {
			result = cc.getProjects(params);
			allprojects = (ArrayList<Project>) result.getProjects();
			return allprojects;
			
				
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getSampleImage(int projectID){
		GetSampleImg_Params params = new GetSampleImg_Params();
		params.setPassword(user.getPassword());
		params.setUsername(user.getUsername());

		params.setProjectID(projectID);
		GetSampleImg_Result result;
		try {
			result = cc.getSampleImage(params);
			String url = result.toString();
			return url;
			
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static DownloadBatch_Result downloadBatch(int projectID){
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setPassword(user.getPassword());
		params.setUsername(user.getUsername());
		params.setProjectID(projectID);
		DownloadBatch_Result result;
		try {
			result = cc.downloadImage(params);
			batch = result.getBatch();
			project = result.getProject();
			return result;
			
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public static void submitBatch(ArrayList<ArrayList<String>> values){
		SubmitBatch_Params params = new SubmitBatch_Params();
		params.setPassword(user.getPassword());
		params.setUsername(user.getUsername());
		params.setBatchID(batch.getId());
		params.setValues(values);
		SubmitBatch_Result result;
		try {
			result = cc.submitBatch(params);
			batch = null;
			
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<Field> getFields(){
		GetFields_Params params = new GetFields_Params();
		params.setPassword(user.getPassword());
		params.setUsername(user.getUsername());
		params.setProjectID(project.getId());
		GetFields_Result result;
		try {
			result = cc.getFeilds(params);
			return (ArrayList<Field>) result.getFields();
			
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void search(){
	
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User u) {
		user = u;
	}

	public static Project getProject() {
		return project;
	}

	public static void setProject(Project proj) {
		project = proj;
	}

	public static Batch getBatch() {
		return batch;
	}

	public static void setBatch(Batch b) {
		batch = b;
	}
	

}


