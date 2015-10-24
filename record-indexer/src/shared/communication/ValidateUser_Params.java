package shared.communication;

import shared.model.*;

public class ValidateUser_Params {
	
	public ValidateUser_Params() {
		user= null;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
