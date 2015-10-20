package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	 * @param project_id
	 * @return list of fields
	 */
	public ArrayList<Field> getFieldByProject(int project_id){
		return null;
	}
	
	 /**
	  * Add a new Field
	  * @param newField
	  * @return The Field that was added, null otherwise
	  */
	public Field addField(Field newField){
		return null;
		
	}
	/**
	 * Updates a Field
	 * @param field
	 */
	public void updateField(Field field){
		
	}
}
