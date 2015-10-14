package server.database.access;



import java.io.File;
import java.util.ArrayList;

import shared.model.*;

public class BatchAccess {
	/**
	 * Queries the database for a specific image file.
	 * @param username
	 * @param password
	 * @param projectId
	 * @return File Object if found, null if failed.
	 */
	public File getSampleImage(String username, String password, int projectId){
		return null;
	}
	
	/**
	 * 
	 * @return list of all batches
	 */
	public ArrayList<Batch> getBatches(){
		return null;
		
	}
	/**
	 * Gets Batch by Id
	 * @param id
	 * @return the batch that matches that ID
	 */
	public Batch getBatchByID(int id){
		return null;
	}
	/**
	 * Gets a batches for a Project
	 * @param project_id
	 * @return list of batches
	 */
	public ArrayList<Batch> getBatchByProjectID(int project_id){
		return null;
	}
	/**
	 * 
	 * @param newbatch
	 * @return returns a batch that was added, null otherwise
	 */
	public Batch addBatch(Batch newbatch){
		return newbatch;
		
	}
	/**
	 * Updates a batch's progress
	 * @param batch
	 */
	public void updateBatch(Batch batch){
		
	}
	
}
