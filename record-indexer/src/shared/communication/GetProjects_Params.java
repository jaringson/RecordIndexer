package shared.communication;

import shared.model.User;

public class GetProjects_Params {
	private User user;
	
	public GetProjects_Params() {
		user = null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
