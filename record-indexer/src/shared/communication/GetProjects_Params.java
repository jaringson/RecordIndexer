package shared.communication;


public class GetProjects_Params {
	
	public GetProjects_Params() {
		username = null;
		password = null;
	}
	
	private String username;
	private String password;
	
	
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
