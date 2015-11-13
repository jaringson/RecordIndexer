package client;

import java.util.ArrayList;

import shared.communication.*;
import shared.model.Batch;
import shared.model.Project;
import shared.model.User;

public class Controller {
	
	public static void initialize(String host,int port){
		cc = new ClientCommunicator(host,port);
	}
	private static ClientCommunicator cc;
	
	private static User user = new User();
	private static Project project = new Project();
	
	private static Batch batch = new Batch();

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
//		params.setPassword(user.getPassword());
//		params.setUsername(user.getUsername());
		params.setPassword("parker");
		params.setUsername("sheila");
		GetProjects_Result result;
		ArrayList<Project> allprojects = new ArrayList<Project>();
//		System.out.println("here 1");
		try {
			result = cc.getProjects(params);
			
			allprojects = (ArrayList<Project>) result.getProjects();
//			System.out.println(allprojects.size());
			return allprojects;
			
				
		} catch (ClientException e) {
			e.printStackTrace();
//			System.out.println("here 2");
			return null;
		}
	}
	public static String getSampleImage(int projectID){
		GetSampleImg_Params params = new GetSampleImg_Params();
//		params.setPassword(user.getPassword());
//		params.setUsername(user.getUsername());
		params.setPassword("parker");
		params.setUsername("sheila");
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
	public static void downloadBatch(){
		
	}
	public static void submitBatch(){
		
	}
	public static void getFields(){
		
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


