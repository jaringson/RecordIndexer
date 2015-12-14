package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.Inputvalue;

public class InputValAccess {
	
	private Database db;
	
	public InputValAccess(Database db) {
		this.db = db;
	}
	
	/**
	 * Gets all inputValues
	 * @return List of InputValues
	 * @throws DatabaseException 
	 */
	public ArrayList<Inputvalue> getAllValues() throws DatabaseException{
		ArrayList<Inputvalue> result = new ArrayList<Inputvalue>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from inputvalues";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int record_id = rs.getInt(2);
				int field_id = rs.getInt(3);
				String inputvalue= rs.getString(4);

				result.add(new Inputvalue(id, record_id, field_id, inputvalue));
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
	 * Gets Values by Project ID
	 * @param project_id
	 * @return List of Inputvalues
	 * @throws DatabaseException 
	 */
	public ArrayList<Inputvalue> getValByRecordID(int newrecord_id) throws DatabaseException{
		ArrayList<Inputvalue> result = new ArrayList<Inputvalue>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from inputvalues where record_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newrecord_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int record_id = rs.getInt(2);
				int field_id = rs.getInt(3);
				String inputvalue= rs.getString(4);

				result.add(new Inputvalue(id, record_id, field_id, inputvalue));
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
	 * Gets Values by Field ID
	 * @param field_id
	 * @return List of Inputvalues
	 * @throws DatabaseException 
	 */
	public ArrayList<Inputvalue> getValByFieldID(int newfield_id) throws DatabaseException{
		ArrayList<Inputvalue> result = new ArrayList<Inputvalue>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from inputvalues where field_id = ?";
			stmt = db.getConnection().prepareStatement(query);
		
			stmt.setInt(1, newfield_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int record_id = rs.getInt(2);
				int field_id = rs.getInt(3);
				String inputvalue= rs.getString(4);

				result.add(new Inputvalue(id, record_id, field_id, inputvalue));
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
	 * Adds Values
	 * @param newValue
	 * @return new inputValue
	 * @throws DatabaseException 
	 */
	public Inputvalue addValue(Inputvalue newValue) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into inputvalues (record_id,field_id,inputvalue) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newValue.getRecord_id());
			stmt.setInt(2, newValue.getField_id());
			stmt.setString(3, newValue.getInputvalue());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				newValue.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert inputvalue");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert inputvalue", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		return newValue;
	}
	/**
	 * Update a Value
	 * @param value
	 * @throws DatabaseException 
	 */
	public void updateValue(Inputvalue value) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "update inputvalues set record_id= ?, field_id= ?, inputvalue= ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, value.getRecord_id());
			stmt.setInt(2, value.getField_id());
			stmt.setString(3, value.getInputvalue());
			
			stmt.setInt(4, value.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update inputvalues");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update inputvalue", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(Inputvalue value) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "delete from inputvalues where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, value.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete inputvalue");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete inputvalue", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public ArrayList<String> search(int valueFieldID,String inputvalue) throws DatabaseException{
		ArrayList<String> tuple = new ArrayList<String>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "SELECT batch_id, file, row_number, field_id "
					+"FROM inputvalues, records, batches " 
					+"WHERE inputvalues.inputvalue = ? and "
					+ "inputvalues.record_id = records.id and "
					+ "records.batch_id= batches.id and "
					+ "inputvalues.field_id =?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, inputvalue);
			stmt.setInt(2, valueFieldID);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				StringBuilder batch_id = new StringBuilder();
				batch_id.append(rs.getInt(1));		
				String file = rs.getString(2);
				StringBuilder row_number = new StringBuilder();
				row_number.append(rs.getInt(3));
				StringBuilder field_id = new StringBuilder();
				field_id.append(rs.getInt(4));

				tuple.add(batch_id.toString());
				tuple.add(file);
				tuple.add(row_number.toString());
				tuple.add(field_id.toString());
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
		
		return tuple;	
	}
}
