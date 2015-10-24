package shared.communication;

import shared.model.*;

public class ValidateUser_Result {
	public ValidateUser_Result() {
		user=null;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
