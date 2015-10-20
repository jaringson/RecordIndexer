package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.Project;

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
	public ArrayList<Project> getAllProjects() throws DatabaseException{
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
	 * @throws DatabaseException 
	 */
	public Project getProjectByID(int id) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Project project = null;
		try {
			String query = "select * from projects where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
		
			rs = stmt.executeQuery();
			if (rs.next()) {
				project = new Project();
				project.setId(rs.getInt(1));
				project.setTitle(rs.getString(2));
				project.setRecordsperimage(rs.getInt(3));
				project.setFirstycoordinate(rs.getInt(4));
				project.setRecordheight(rs.getInt(5));
				
			}
			else {
				throw new DatabaseException("Could not retrieve project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not retrieve project", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
		return project;
	}
	
	/**
	 * 
	 * @param newProject
	 * @return the new project that was added
	 * @throws DatabaseException 
	 */
	public Project addProject(Project newProject) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into projects (title, recordsperimage, firstycoord, recordheight) values (?, ?, ?, ?)";   
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, newProject.getTitle());
			stmt.setInt(2, newProject.getRecordsperimage());
			stmt.setInt(3, newProject.getFirstycoordinate());
			stmt.setInt(4, newProject.getRecordheight());
			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				newProject.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert project", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		return newProject;
		
	}
	
	/**
	 * Updates a project
	 * @param project
	 * @throws DatabaseException 
	 */
	public void updateProject(Project project) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "update projects set title = ?, recordsperimage = ?, firstycoord= ?, recordheight= ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsperimage());
			stmt.setInt(3, project.getFirstycoordinate());
			stmt.setInt(4, project.getRecordheight());
			
			
			stmt.setInt(5, project.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update project", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(Project project) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "delete from projects where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, project.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete project", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
