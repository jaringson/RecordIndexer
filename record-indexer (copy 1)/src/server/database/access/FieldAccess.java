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
				String title = rs.getString(3);
				int xcoord = rs.getInt(4);
				int width= rs.getInt(5);
				String helpthtml= rs.getString(6);
				String knowndata = rs.getString(7);
				int columnnumber= rs.getInt(8);

				result.add(new Field(id, project_id, title,xcoord,width,helpthtml,knowndata, columnnumber));
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
				String title = rs.getString(3);
				int xcoord = rs.getInt(4);
				int width= rs.getInt(5);
				String helpthtml= rs.getString(6);
				String knowndata = rs.getString(7);
				int columnnumber= rs.getInt(8);

				result.add(new Field(id, project_id, title,xcoord,width,helpthtml,knowndata, columnnumber));
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
			String query = "insert into fields (project_id,title,xcoord,width,helphtml,knowndata,columnnumber) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newField.getProject_id());
			stmt.setString(2, newField.getTitle());
			stmt.setInt(3, newField.getXcoord());
			stmt.setInt(4, newField.getWidth());
			stmt.setString(5, newField.getHelphtml());
			stmt.setString(6, newField.getKnowndata());
			stmt.setInt(7, newField.getColumnnumber());
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
			String query = "update fields set project_id=?,title=?,xcoord=?,width=?,helphtml=?,knowndata=?,columnnumber=? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, field.getProject_id());
			stmt.setString(2, field.getTitle());
			stmt.setInt(3, field.getXcoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelphtml());
			stmt.setString(6, field.getKnowndata());
			stmt.setInt(7, field.getColumnnumber());
			
			stmt.setInt(8, field.getId());
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
	
	public Field getFieldByID(int id) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Field field = null;
		try {
			String query = "select * from fields where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
		
			rs = stmt.executeQuery();
			if (rs.next()) {
				field = new Field();
				field.setId(rs.getInt(1));
				field.setProject_id(rs.getInt(2));
				field.setTitle(rs.getString(3));
				field.setXcoord(rs.getInt(4));
				field.setWidth(rs.getInt(5));
				field.setHelphtml(rs.getString(6));
				field.setKnowndata(rs.getString(7));
				field.setColumnnumber(rs.getInt(8));
			}
			else {
				throw new DatabaseException("Could not retrieve field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not retrieve field", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
		return field;
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
