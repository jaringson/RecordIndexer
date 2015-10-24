package shared.communication;

import shared.model.Batch;
import shared.model.User;

public class GetSampleImg_Params {
	private User user;
	private int projectID;
	
	
	public GetSampleImg_Params() {
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
