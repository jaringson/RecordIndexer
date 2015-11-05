package shared.communication;

import java.util.ArrayList;

public class Search_Params {
	
	
	public Search_Params() {
		username=null;
		password=null;
		fieldIDs = null;
		searchValues=null;
	}
	private String username;
	private String password;
	private ArrayList<Integer> fieldIDs;
	private String[] searchValues;
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
	public ArrayList<Integer> getFieldIDs() {
		return fieldIDs;
	}
	public void setFieldIDs( ArrayList<Integer>  fieldIDs) {
		this.fieldIDs = fieldIDs;
	}
	public String[] getSearchValues() {
		return searchValues;
	}
	public void setSearchValues(String[] searchValues) {
		this.searchValues = searchValues;
	}
}
