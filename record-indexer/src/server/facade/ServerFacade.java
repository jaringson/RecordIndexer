package server.facade;

import java.util.*;

import server.database.*;
import shared.communication.*;
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
	
	public static ValidateUser_Result validateUser(ValidateUser_Params params) throws ServerException {	

		Database db = new Database();
		ValidateUser_Result result = new ValidateUser_Result();
		try {
			db.startTransaction();
			User user = db.getUserAccess().validateUser(params.getUsername(), params.getPassword());
			db.endTransaction(true);
			result.setUser(user);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static GetProjects_Result getProjects(GetProjects_Params params) throws ServerException {

		Database db = new Database();
		GetProjects_Result result = new GetProjects_Result();
		try {
			db.startTransaction();
			db.getUserAccess().validateUser(params.getUsername(),params.getPassword());
			List<Project> projects = db.getProjectAccess().getAllProjects();
			db.endTransaction(true);
			result.setProjects(projects);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static GetSampleImg_Result getSampleImage(GetSampleImg_Params params) throws ServerException {

		Database db = new Database();
		GetSampleImg_Result result = new GetSampleImg_Result();
		try {
			db.startTransaction();
			List<Batch> batches = db.getBatchAccess().getBatchByProjectID(params.getProjectID());
			db.endTransaction(true);
			result.setFile(batches.get(0).getFile());
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ServerException {

		Database db = new Database();
		DownloadBatch_Result result = new DownloadBatch_Result();
		try {
			db.startTransaction();
			List<Batch> batches = db.getBatchAccess().getBatchByProjectID(params.getProjectID());
			Batch batch = batches.get(0);
			List<Field> fields = db.getFieldAccess().getFieldByProject(params.getProjectID());
			db.endTransaction(true);
			result.setBatch(batch);
			result.setFields(fields);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	public static SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ServerException{
		//Ask if this type of logic works with catching the error if the record doesn't exist
		
		Database db = new Database();
		SubmitBatch_Result result = new SubmitBatch_Result();
		User user;
		try {
			db.startTransaction();
			user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			for(Record record:params.getRecords()){
				try{
					db.getRecordAccess().updateRecord(record);
				}
				catch(DatabaseException e){
					db.getRecordAccess().addRecord(record);
				}
				
				user.setIndexrecords(user.getIndexrecords()+1);
				
			}
			user.setCurBatch(-1);
			db.getUserAccess().updateUser(user);
			result.setSuccess(true);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static GetFields_Result getFields(GetFields_Params params) throws ServerException{
		Database db = new Database();
		GetFields_Result result = new GetFields_Result();
		try {
			db.startTransaction();
			db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			List<Field> fields = db.getFieldAccess().getFieldByProject(params.getProjectID());
			result.setFields(fields);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	public static Search_Result search(Search_Params params) throws ServerException{
		Database db = new Database();
		Search_Result result = new Search_Result();
		ArrayList<ArrayList<String>> tuples = new ArrayList<ArrayList<String>>();
		try {
			db.startTransaction();
			db.getUserAccess().validateUser(params.getUsername(),params.getPassword());

			List<Field> fields = db.getFieldAccess().getAllFields();
			
			for(String search:params.getSearchValues()){
				for(int x :params.getFieldIDs()){
					List<Inputvalue> values = new ArrayList<Inputvalue>();
					if(x > fields.size() || x <= 0)
						throw new ServerException("Invalid Field ID");
					try{
						values = db.getInputValAccess().getValByFieldID(x);
					}
					catch (DatabaseException e){
						System.out.println(e.getMessage());
					}
					
					for(Inputvalue value:values){
						if(search.equals(value)){
							tuples.add(db.getInputValAccess().search(value.getId()));
						}
					}
				}
			}
			
			result.setTuples(tuples);

			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

}
