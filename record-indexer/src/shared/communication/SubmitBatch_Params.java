package shared.communication;

import java.util.List;

import shared.model.*;

public class SubmitBatch_Params {
	private String username;
	private String password;
	private int batchID;
	private List<Record> records;
	
	public SubmitBatch_Params() {
		username= null;
		password =null;
		batchID=-1;
		records =null;
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
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
}
