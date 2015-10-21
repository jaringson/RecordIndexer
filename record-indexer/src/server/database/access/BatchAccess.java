package server.database.access;



import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.Batch;

public class BatchAccess {
	
	private Database db;
	
	public BatchAccess(Database db) {
		this.db = db;
	}
	
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
	 * @throws DatabaseException 
	 */
	public ArrayList<Batch> getAllBatches() throws DatabaseException{
		ArrayList<Batch> result = new ArrayList<Batch>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from batches";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int project_id = rs.getInt(2);
				String file = rs.getString(3);
				Boolean complete= rs.getBoolean(4);
				Boolean available  = rs.getBoolean(5);
				Boolean checkedout= rs.getBoolean(6);

				result.add(new Batch(id, project_id,file,complete,available,checkedout));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		
		return result;	
		
	}
	/**
	 * Gets Batch by Id
	 * @param id
	 * @return the batch that matches that ID
	 * @throws DatabaseException 
	 */
	public Batch getBatchByID(int id) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Batch batch = null;
		try {
			String query = "select * from batches where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
		
			rs = stmt.executeQuery();
			if (rs.next()) {
				batch = new Batch();
				batch.setId(rs.getInt(1));
				batch.setProjectid(rs.getInt(2));
				batch.setFile(rs.getString(3));
				batch.setComplete(rs.getBoolean(4));
				batch.setAvailable(rs.getBoolean(5));
				batch.setCheckedout(rs.getBoolean(6));
			}
			else {
				throw new DatabaseException("Could not retrieve batch");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not retrieve batch", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
		return batch;
	}
	/**
	 * Gets a batches for a Project
	 * @param project_id
	 * @return list of batches
	 * @throws DatabaseException 
	 */
	public ArrayList<Batch> getBatchByProjectID(int newproject_id) throws DatabaseException{
		ArrayList<Batch> result = new ArrayList<Batch>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from batches where project_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newproject_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int project_id = rs.getInt(2);
				String file = rs.getString(3);
				Boolean complete= rs.getBoolean(4);
				Boolean available  = rs.getBoolean(5);
				Boolean checkedout= rs.getBoolean(6);

				result.add(new Batch(id, project_id,file,complete,available,checkedout));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		
		return result;	
	}
	/**
	 * 
	 * @param newbatch
	 * @return returns a batch that was added, null otherwise
	 * @throws DatabaseException 
	 */
	public Batch addBatch(Batch newBatch) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into batches (project_id,file,complete,available,checkedout) values (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newBatch.getProjectid());
			stmt.setString(2, newBatch.getFile());
			stmt.setBoolean(3, newBatch.isComplete());
			stmt.setBoolean(4, newBatch.isAvailable());
			stmt.setBoolean(5, newBatch.isCheckedout());
			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				newBatch.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert batch");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert batch", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		return newBatch;
		
	}
	/**
	 * Updates a batch's progress
	 * @param batch
	 * @throws DatabaseException 
	 */
	public void updateBatch(Batch batch) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "update batches set project_id =?,file=?,complete=?,available=?,checkedout=? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, batch.getProjectid());
			stmt.setString(2, batch.getFile());
			stmt.setBoolean(3, batch.isComplete());
			stmt.setBoolean(4, batch.isAvailable());
			stmt.setBoolean(5, batch.isCheckedout());
			
			stmt.setInt(6, batch.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update batch");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update batch", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	public void delete(Batch batch) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "delete from batches where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, batch.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete batch");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete batch", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
}
