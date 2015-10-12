package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.User;

public class DatabaseAccessObj {
private static Logger logger;
	
	static {
		logger = Logger.getLogger("contactmanager");
	}

	private Database db;
	
	DatabaseAccessObj(Database db) {
		this.db = db;
	}
	
	public List<User> GetAllUsers() throws DatabaseException {
		
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, username, password, firstname, lastname, indexrecords, curBatch_id from users";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				int indexrecords = rs.getInt(6);
				int curBatch = rs.getInt(7);
				
				User tempUser = new User();
				tempUser.setUsername(username);
				tempUser.setPassword(password);
				tempUser.setFirstname(firstname);
				tempUser.setLastname(lastname);
				tempUser.setIndexrecords(indexrecords);
				tempUser.setCurBatch(curBatch);
				
				result.add(tempUser);
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
	
	public void add(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into contact (name, phone, address, email, url) values (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			/*stmt.setString(1, user.getName());
			stmt.setString(2, user.getPhone());
			stmt.setString(3, user.getAddress());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getUrl());*/
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert contact");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert contact", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public void update(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update contact set name = ?, phone = ?, address = ?, email = ?, url = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			/*stmt.setString(1, user.getName());
			stmt.setString(2, user.getPhone());
			stmt.setString(3, user.getAddress());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getUrl());*/
			stmt.setInt(6, user.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update contact");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update contact", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from contact where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete contact");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete contact", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}
