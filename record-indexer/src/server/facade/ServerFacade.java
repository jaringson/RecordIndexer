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
			User user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			if(user == null){
				db.endTransaction(false);
				throw new ServerException("Didn't Validate User");
			}
			
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
			User user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			if(user == null){
				db.endTransaction(false);
				throw new ServerException("Didn't Validate User");
			}
			ArrayList<Batch> batches = db.getBatchAccess().getBatchByProjectID(params.getProjectID());
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
			User user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword());
			if(user == null){
				db.endTransaction(false);
				throw new ServerException("Didn't Validate User");
			}

			List<Batch> batches = db.getBatchAccess().getBatchByProjectID(params.getProjectID());
			if(batches.size()==0){
				db.endTransaction(false);
				throw new ServerException("Incorrect Project ID");
			}
			
			Batch batch = batches.get(0);
			if(user.getCurBatch() != 0 && user.getCurBatch()!= -1){
				db.endTransaction(false);
				throw new ServerException("User Already has a Batch");
			}
			
			if(batch.isCheckedout()){
				db.endTransaction(false);
				throw new ServerException("batch is already check out");
			}
			batch.setCheckedout(true);
			db.getBatchAccess().updateBatch(batch);
			
			user.setCurBatch(batch.getId());
			db.getUserAccess().updateUser(user);
			
			List<Field> fields = db.getFieldAccess().getFieldByProject(params.getProjectID());
			Project project = db.getProjectAccess().getProjectByID(batch.getProjectid());
			
			result.setBatch(batch);
			result.setFields(fields);
			result.setProject(project);
			
			db.endTransaction(true);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	public static SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ServerException{
		
		Database db = new Database();
		SubmitBatch_Result result = new SubmitBatch_Result();
		User user;
		Batch batch;
		try {

			db.startTransaction();
			user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			
			if(user == null){
				db.endTransaction(false);
				throw new ServerException("Didn't Validate User");
			}

			batch = db.getBatchAccess().getBatchByID(params.getBatchID());
			Project project = db.getProjectAccess().getProjectByID(batch.getProjectid());

			//Check if User own batch
			if(user.getCurBatch()!=params.getBatchID()){ 
				db.endTransaction(false);
				throw new ServerException("User Doesn't Own Batch");
			}
			ArrayList<Field> fields = db.getFieldAccess().getFieldByProject(batch.getProjectid());

			//Check if Correct Number of Values were Entered
			if(params.getValues().size() != project.getRecordsperimage()){
				db.endTransaction(false);
				throw new ServerException("Not Enough Values Entered");
			}
			db.endTransaction(true);
			
			for(int i =0;i<params.getValues().size();i++){
				
				Record newRecord = new Record(-1,batch.getId(),i+1);
				db.startTransaction();
				Record record = db.getRecordAccess().addRecord(newRecord);
				db.endTransaction(true);
				
				for(int j = 0;j<fields.size();j++){
					String single_value =params.getValues().get(i).get(j);
					Inputvalue value = new Inputvalue(-1,record.getId(),fields.get(j).getId(),single_value);
					db.startTransaction();
					db.getInputValAccess().addValue(value);
					db.endTransaction(true);
				}
			}
			user.setCurBatch(-1);
			user.setIndexrecords(user.getIndexrecords()+params.getValues().size());
			batch.setCheckedout(false);
			db.startTransaction();
			db.getBatchAccess().updateBatch(batch);
			db.getUserAccess().updateUser(user);
			db.endTransaction(true);
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
			User user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			if(user == null){
				db.endTransaction(false);
				throw new ServerException("Didn't Validate User");
			}
			ArrayList<Field> fields = new ArrayList<Field>();
			if(params.getProjectID() == -1){
				fields = db.getFieldAccess().getAllFields();
			}
			else{
				fields = db.getFieldAccess().getFieldByProject(params.getProjectID());
			}
			if(fields.size() ==0){
				db.endTransaction(false);
				throw new ServerException("Didn't Find Project");
			}
			result.setFields(fields);
			db.endTransaction(true);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	public static Search_Result search(Search_Params params) throws ServerException{
		
		Database db = new Database();
		if(params.getSearchValues().length ==0){
			db.endTransaction(false);
			throw new ServerException("No Search Values");
		}
		
		
		Search_Result result = new Search_Result();
		ArrayList<ArrayList<String>> tuples = new ArrayList<ArrayList<String>>();
		try {
			db.startTransaction();
			User user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			if(user == null){
				db.endTransaction(false);
				throw new ServerException("Didn't Validate User");
			}
			for(int x :params.getFieldIDs()){
				for(String search:params.getSearchValues()){
					ArrayList<String> temp =db.getInputValAccess().search(x, search);
					for(int k = 0;k<temp.size();k=k+4){
						ArrayList<String> temp2 = new ArrayList<String>();
						for(int h = 0;h<4;h++){
							temp2.add(temp.get(k+h));
						}
						tuples.add(temp2);
					}
				}
			}

			result.setTuples(tuples);
			db.endTransaction(true);
			return result;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

}
