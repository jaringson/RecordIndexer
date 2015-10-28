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
	
	@Override
	public String toString(){
		StringBuilder ss = new StringBuilder();
		ss.append("TRUE" + "\n");
		ss.append(user.getFirstname() +"\n");
		ss.append(user.getLastname()+"\n");
		ss.append(user.getIndexrecords());
		return ss.toString();
	}
}
