package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.*;

public class ProjectAccess {
	
private static Logger logger;
	
//	static {
//		logger = Logger.getLogger("contactmanager");
//	}

	private Database db;
	
	public ProjectAccess(Database db) {
		this.db = db;
	}
	
	
	/**
	 * This queries the database for all current projects.
	 * @param username
	 * @param password
	 * @return An ArrayList of Project Objects if not failed, null otherwise.
	 * @throws DatabaseException 
	 */
	public ArrayList<Project> getProjects() throws DatabaseException{
		ArrayList<Project> result = new ArrayList<Project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from projects";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsperimage = rs.getInt(3);
				int firstycoord = rs.getInt(4);
				int recordheight = rs.getInt(5);

				result.add(new Project(id, title, recordsperimage, firstycoord, recordheight));
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
	 * Gets a Project by its ID
	 * @param id
	 * @return project
	 */
	public Project getProjectByID(int id){
		
		return null;
	}
	
	/**
	 * 
	 * @param newProject
	 * @return the new project that was added
	 */
	public Project addProject(Project newProject){
		return newProject;
		
	}
	
	/**
	 * Updates a project
	 * @param project
	 */
	public void updateProject(Project project){
		
	}
}
