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
			User user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			if(user == null){
				throw new ServerException("Didn't Validate User");
			}
			if(user.getCurBatch()!= 0 || user.getCurBatch()!=-1){
				throw new ServerException("User Already has a Batch");
			}
			
			List<Batch> batches = db.getBatchAccess().getBatchByProjectID(params.getProjectID());
			Batch batch = batches.get(0);
			user.setCurBatch(batch.getId());
			List<Field> fields = db.getFieldAccess().getFieldByProject(params.getProjectID());
			Project project = db.getProjectAccess().getProjectByID(batch.getProjectid());
			db.endTransaction(true);
			result.setBatch(batch);
			result.setFields(fields);
			result.setProject(project);
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
//			System.out.println("Here in Facade");
			db.startTransaction();
			user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			if(user == null){
				throw new ServerException("Didn't Validate User");
			}
//			System.out.println("Here in Facade1");
			Batch batch = db.getBatchAccess().getBatchByID(params.getBatchID());
			Project project = db.getProjectAccess().getProjectByID(batch.getProjectid());
		
			//Check if User own batch
//			if(user.getCurBatch()!=params.getBatchID()){ 
//				throw new ServerException("User Doesn't Own Batch");
//			}
			ArrayList<Field> fields = db.getFieldAccess().getFieldByProject(batch.getProjectid());
			ArrayList<Record> records = new ArrayList<Record>();
//			System.out.println("Here in Facade2");
//			System.out.println(params.getValues().size());
//			System.out.println(project.getRecordsperimage());
			//Check if Correct Number of Values were Entered
			if(params.getValues().size() != project.getRecordsperimage()){
				throw new ServerException("Not Enough Values Entered");
			}
			
			records = db.getRecordAccess().getRecordsByBatch(batch.getId());
			Boolean flag =false; //Flag to see if any Records have been created already
			int size = 0;
			if(records.size()==0){
				flag = true;
			}
			else{
				size = records.size();
			}
			records = db.getRecordAccess().getAllRecords();
			
			for(int i =0;i<params.getValues().size();i++){
//				System.out.println("Here in Facade3");
		
				if(flag){
//					System.out.println("Here in Facade3.5");
					//First Time So i is appropriate for Column Number
					Record newRecord = new Record(-1,batch.getId(),i);
					Record record = db.getRecordAccess().addRecord(newRecord);
				}
				else{
//					System.out.println("Here in Facade3.75");
					Record newRecord = new Record(-1,batch.getId(),i+size);
					Record record = db.getRecordAccess().addRecord(newRecord);
				}
			
				for(int j = 0;j<fields.size();j++){
					String single_value =params.getValues().get(i).get(j);
					Inputvalue value = new Inputvalue(-1,records.size()+i,fields.get(j).getId(),single_value);
					
//					System.out.println(value.getInputvalue());
//					System.out.println("Here in Facade4");
					Inputvalue addedValue= db.getInputValAccess().addValue(value);
				}
			}
			user.setCurBatch(-1);
			user.setIndexrecords(params.getValues().size());
			db.getUserAccess().updateUser(user);
			result.setSuccess(true);
			db.endTransaction(true);
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
				throw new ServerException("Didn't Validate User");
			}
			ArrayList<Field> fields = new ArrayList<Field>();
			if(params.getProjectID() == -1){
				fields = db.getFieldAccess().getAllFields();
			}
			else{
				fields = db.getFieldAccess().getFieldByProject(params.getProjectID());
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
		if(params.getSearchValues().length ==0){
			throw new ServerException("No Search Values");
		}
		
		Database db = new Database();
		Search_Result result = new Search_Result();
		ArrayList<ArrayList<String>> tuples = new ArrayList<ArrayList<String>>();
		try {
			db.startTransaction();
			User user = db.getUserAccess().validateUser(params.getUsername(),params.getPassword() );
			if(user == null){
				throw new ServerException("Didn't Validate User");
			}
//			System.out.println("Here is Facade1");

			for(int x :params.getFieldIDs()){
				Field field = db.getFieldAccess().getFieldByID(x);
				for(String search:params.getSearchValues()){
//				System.out.println("Here is Facade2");
				
//					System.out.println("Here is Facade3");
					
					ArrayList<Inputvalue> values = new ArrayList<Inputvalue>();
					try{
						values = db.getInputValAccess().getValByFieldID(x);
						for(Inputvalue value:values){
							//System.out.println("Here is Facade5");
							//System.out.println(value.getInputvalue()+" "+search);
							if(search.equals(value.getInputvalue())){
//								System.out.println("Found "+ value.getInputvalue() +" " +value.getId());
								ArrayList<String> temp =db.getInputValAccess().search(value.getId());
//								System.out.println(temp.get(0));
//								System.out.println(temp.get(1));
//								System.out.println(temp.get(2));
//								System.out.println(temp.get(3));
								tuples.add(temp);
							}
						}
					}
					catch (DatabaseException e){
						System.out.println(e.getMessage());
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
