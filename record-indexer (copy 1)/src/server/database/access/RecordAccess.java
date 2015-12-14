package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.Record;

public class RecordAccess {
	
	private Database db;
	
	public RecordAccess(Database db) {
		this.db = db;
	}
	
	/**
	 * 
	 * @return a list of Records
	 * @throws DatabaseException 
	 */
	public ArrayList<Record> getAllRecords() throws DatabaseException{
		ArrayList<Record> result = new ArrayList<Record>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from records";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int batch_id = rs.getInt(2);
				int row_number = rs.getInt(3);

				result.add(new Record(id, batch_id, row_number));
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
	 * Gets Records by Batch ID
	 * @param batch_id
	 * @return list of records
	 * @throws DatabaseException 
	 */
	public ArrayList<Record> getRecordsByBatch(int batch_id) throws DatabaseException{
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Record record = null;
		ArrayList<Record> records = new ArrayList<Record>();
		try {
			String query = "select * from records where batch_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, batch_id);
		
			rs = stmt.executeQuery();
			while (rs.next()) {
				record = new Record();
				record.setId(rs.getInt(1));
				record.setBatch_id(rs.getInt(2));
				record.setRow_number(rs.getInt(3));
				records.add(record);
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not retrieve record", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
		return records;
	}
	
	/**
	 * This Methods allows for adding a new record
	 * @param newRecord-
	 * @return the Record that was added
	 * @throws DatabaseException 
	 */
	public Record addRecord(Record newRecord) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into records (batch_id, row_number) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newRecord.getBatch_id());
			stmt.setInt(2, newRecord.getRow_number());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				newRecord.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert record", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		return newRecord;
	}
	
	/**
	 * Updates a record
	 * @param record
	 * @throws DatabaseException 
	 */
	public void updateRecord(Record record) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "update records set batch_id = ?, row_number= ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, record.getBatch_id());
			stmt.setInt(2, record.getRow_number());
			
			stmt.setInt(3, record.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(Record record) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "delete from records where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, record.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
}
