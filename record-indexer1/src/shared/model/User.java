package shared.model;
/**
 * Holds the information for the User class.
 * @author jaronce
 *
 */

public class User {
	private int id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int indexrecords;
	private int curBatch;
	
	

	public User(int id, String username, String password, String firstname,
			String lastname, String email, int indexrecords, int curBatch) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.indexrecords = indexrecords;
		this.curBatch = curBatch;
	}
	
	public User() {
		this.id = 0;
		this.username = null;
		this.password = null;
		this.firstname = null;
		this.lastname = null;
		this.email = null;
		this.indexrecords = 0;
		this.curBatch = 0;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getIndexrecords() {
		return indexrecords;
	}
	public void setIndexrecords(int indexrecords) {
		this.indexrecords = indexrecords;
	}
	public int getCurBatch() {
		return curBatch;
	}
	public void setCurBatch(int curBatch) {
		this.curBatch = curBatch;
	}
}
