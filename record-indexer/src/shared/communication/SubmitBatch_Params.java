package shared.communication;

import java.util.ArrayList;

public class SubmitBatch_Params {
	private String username;
	private String password;
	private int batchID;
	private ArrayList<ArrayList<String>> values;
	
	public SubmitBatch_Params() {
		username= null;
		password =null;
		batchID=-1;
		values =null;
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

	public int getBatchID() {
		return batchID;
	}
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}
	public ArrayList<ArrayList<String>> getValues() {
		return values;
	}
	public void setValues(ArrayList<ArrayList<String>> values) {
		this.values = values;
	}
	
}
