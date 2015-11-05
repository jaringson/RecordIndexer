package shared.communication;



/**
 * Contains an objects for Imputing Results of DownloadImage
 * @author jaronce
 *
 */
public class DownloadBatch_Params {
	
	
	public DownloadBatch_Params() {
		username = null;
		password = null;
		projectID =-1;
	}
	private String username;
	private String password;
	private int projectID;
	
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
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
}
