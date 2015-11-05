package shared.communication;


/**
 * Contains an objects for Inputing Results of GetFeilds
 * @author jaronce
 *
 */
public class GetFields_Params {
	private String username;
	private String password;
	private int projectID;

	public GetFields_Params() {
		username =null;
		password =null;
		projectID=-1;
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

	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
}
