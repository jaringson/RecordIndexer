package server.facade;

import java.util.*;

import server.database.*;
import shared.model.*;
import server.*;

public class ServerFacade {

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static User validateUser(String username, String password) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			User user = db.getUserAccess().validateUser(username, password);
			db.endTransaction(true);
			return user;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<Project> getProjects(User user) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			User resultUser = db.getUserAccess().validateUser(user.getUsername(),user.getPassword());
			List<Project> projects = db.getProjectAccess().getAllProjects();
			db.endTransaction(true);
			return projects;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static String getSampleImage(User user, int projectID) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			List<Batch> batches = db.getBatchAccess().getBatchByProjectID(projectID);
			db.endTransaction(true);
			return batches.get(0).getFile();
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void downloadBatch(User user,int projectID) throws ServerException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			List<Batch> batches = db.getBatchAccess().getBatchByProjectID(projectID);
			Batch batch = batches.get(0);
			List<Field> fields = db.getFieldAccess().getFieldByProject(projectID);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

}
