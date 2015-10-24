package shared.communication;

import shared.model.User;

/**
 * Contains an objects for Imputing Results of DownloadImage
 * @author jaronce
 *
 */
public class DownloadBatch_Params {
	private User user;
	private int projectID;
	
	
	public DownloadBatch_Params() {
		user = null;
		projectID =-1;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
}
