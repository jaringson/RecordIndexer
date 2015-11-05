package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.*;

public class UserAccess {
	
	private Database db;
	
	public UserAccess(Database db){
		this.db = db;
	}
	
	
	/**
	 * Checks to see if a username and password are contained in the database.
	 * @param username
	 * @param password
	 * @return User object if found, null if failed or false
	 * @throws DatabaseException 
	 */
	public User validateUser(String username, String password) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			String query = "select * from users where username = ? and password = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstname(rs.getString(4));
				user.setLastname(rs.getString(5));
				user.setEmail(rs.getString(6));
			
				user.setIndexrecords(rs.getInt(7));
				user.setCurBatch(rs.getInt(8));
			}
//			else {
//				throw new DatabaseException("Could not find user");
//			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not find user", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(rs);
		}
		return user;
	}
	/**
	 * Gets all Current Users
	 * @return list of all users
	 * @throws DatabaseException 
	 */
	public ArrayList<User> getAllUsers() throws DatabaseException{
		
		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from users";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String email = rs.getString(6);
				int indexrecords = rs.getInt(7);
				int curBatch_id = rs.getInt(8);

				result.add(new User(id, username, password, firstname, lastname, email, indexrecords, curBatch_id));
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
	 * Adds a new User
	 * @param newUser
	 * @return the User that was added
	 * @throws DatabaseException 
	 */
	public User addUser(User newUser) throws DatabaseException{
		
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into users (username, password, firstname, lastname, email, indexrecords, curBatch_id) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, newUser.getUsername());
			stmt.setString(2, newUser.getPassword());
			stmt.setString(3, newUser.getFirstname());
			stmt.setString(4, newUser.getLastname());
			stmt.setString(5, newUser.getEmail());
			stmt.setInt(6, newUser.getIndexrecords());
			stmt.setInt(7, newUser.getCurBatch());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				newUser.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert user", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		return newUser;
		
	}
	
	/**
	 * Updates a User
	 * @param user
	 * @throws DatabaseException 
	 */
	public void updateUser(User user) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "update users set username = ?, password= ?, firstname= ?, lastname= ?, email= ?, indexrecords= ?, curBatch_id= ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexrecords());
			stmt.setInt(7, user.getCurBatch());
			
			stmt.setInt(8, user.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	/**
	 * Updates the User's CurBatch and How Many Records Indexed
	 * @param user
	 * @throws DatabaseException
	 */
	public void updateCurBatch(User user) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "update users set indexrecords= ?, curBatch_id= ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);

			stmt.setInt(1, user.getIndexrecords());
			stmt.setInt(2, user.getCurBatch());
			stmt.setInt(3, user.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(User user) throws DatabaseException{
		PreparedStatement stmt = null;
		try {
			String query = "delete from users where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
