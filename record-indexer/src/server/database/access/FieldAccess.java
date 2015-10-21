package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.Field;

public class FieldAccess {
	
	private Database db;
	
	public FieldAccess(Database db) {
		this.db = db;
	}
	
	/**
	 * 
	 * @return list of All Fields
	 * @throws DatabaseException 
	 */
	public ArrayList<Field> getAllFields() throws DatabaseException{
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from fields";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int project_id = rs.getInt(2);
				int field_number = rs.getInt(3);
				String title = rs.getString(4);
				int xcoord = rs.getInt(5);
				int width= rs.getInt(6);
				String helpthtml= rs.getString(7);
				String knowndata = rs.getString(8);
				int columnnumber= rs.getInt(9);

				result.add(new Field(id, project_id, field_number,title,xcoord,width,helpthtml,knowndata, columnnumber));
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
	 * Gets Field by Project ID
	 * @param newproject_id
	 * @return list of fields
	 * @throws DatabaseException 
	 */
	public ArrayList<Field> getFieldByProject(int newproject_id) throws DatabaseException{
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from fields where project_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newproject_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int project_id = rs.getInt(2);
				int field_number = rs.getInt(3);
				String title = rs.getString(4);
				int xcoord = rs.getInt(5);
				int width= rs.getInt(6);
				String helpthtml= rs.getString(7);
				String knowndata = rs.getString(8);
				int columnnumber= rs.getInt(9);

				result.add(new Field(id, project_id, field_number,title,xcoord,width,helpthtml,knowndata, columnnumber));
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
	  * Add a new Field
	  * @param newField
	  * @return The Field that was added, null otherwise
	 * @throws DatabaseException 
	  */
	public Field addField(Field newField) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into fields (project_id,field_number,title,xcoord,width,helphtml,knowndata,columnnumber) values (?, ?, ?, ?, ?, ?, ?,?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newField.getProject_id());
			stmt.setInt(2, newField.getField_number());
			stmt.setString(3, newField.getTitle());
			stmt.setInt(4, newField.getXcoord());
			stmt.setInt(5, newField.getWidth());
			stmt.setString(6, newField.getHelphtml());
			stmt.setString(7, newField.getKnowndata());
			stmt.setInt(8, newField.getColumnnumber());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				newField.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert field", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		return newField;
		
	}
	/**
	 * Updates a Field
	 * @param field
	 * @throws DatabaseException 
	 */
	public void updateField(Field field) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "update fields set project_id=?,field_number=?,title=?,xcoord=?,width=?,helphtml=?,knowndata=?,columnnumber=? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, field.getProject_id());
			stmt.setInt(2, field.getField_number());
			stmt.setString(3, field.getTitle());
			stmt.setInt(4, field.getXcoord());
			stmt.setInt(5, field.getWidth());
			stmt.setString(6, field.getHelphtml());
			stmt.setString(7, field.getKnowndata());
			stmt.setInt(8, field.getColumnnumber());

			stmt.setInt(9, field.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(Field field) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "delete from fields where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, field.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
