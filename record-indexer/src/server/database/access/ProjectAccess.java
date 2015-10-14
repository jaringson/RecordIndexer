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
	
	ProjectAccess(Database db) {
		this.db = db;
	}
	
	
	/**
	 * This queries the database for all current projects.
	 * @param username
	 * @param password
	 * @return An ArrayList of Project Objects if not failed, null otherwise.
	 */
	public ArrayList<Project> getProjects(String username, String password){
		return null;
	}
	/**
	 * Gets a Project by its ID
	 * @param id
	 * @return project
	 */
	public Project getProjectByID(int id){
		logger.entering("server.database.Contacts", "getAll");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, name, phone, address, email, url from projects";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				String address = rs.getString(4);
				String email = rs.getString(5);
				String url = rs.getString(6);

				result.add(new Contact(id, name, phone, address, email, url));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Contacts", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "getAll");
		
		return result;	
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
