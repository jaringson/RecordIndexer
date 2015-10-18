package server.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import server.database.access.*;

public class Database {
	
	private UserAccess useraccess;
	private RecordAccess recordaccess;
	private ProjectAccess projectaccess;
	private InputValAccess inputvalaccess;
	private FieldAccess fieldaccess;
	private BatchAccess batchaccess;
	
	
	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "Indexer.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
												File.separator + DATABASE_FILE;
	

	public static void initialize() throws DatabaseException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			//logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	}


	private Connection connection;
	
	public Database() {
		connection = null;
		useraccess = new UserAccess(this);
		recordaccess = new RecordAccess(this);
		projectaccess= new ProjectAccess(this);
		inputvalaccess = new InputValAccess(this);
		fieldaccess = new FieldAccess(this);
		batchaccess = new BatchAccess(this);
	}
	

	public UserAccess getUserAccess(){
		return useraccess;
	}
	public RecordAccess getRecordAccess(){
		return recordaccess;
	}
	public ProjectAccess getProjectAccess(){
		return projectaccess;
	}
	public InputValAccess getInputValAccess(){
		return inputvalaccess;
	}
	public FieldAccess getFieldAccess(){
		return fieldaccess;
	}
	public BatchAccess getBatchAccess(){
		return batchaccess;
	}
	
	
	public void deleteData(){
		 File to = new File("Database" + File.separator + "Indexer.sqlite");
	        File from = new File("Database" + File.separator + "blankIndexer.sqlite");

	        to.delete();

	        try{
	            Files.copy(from.toPath(), to.toPath());
	        }catch(IOException e){
	            //e.printStackTrace();
	        }
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void startTransaction() throws DatabaseException {
		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	
	public void endTransaction(boolean commit) {
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
}
