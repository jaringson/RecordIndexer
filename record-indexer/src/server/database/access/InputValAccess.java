package server.database.access;

import java.util.ArrayList;

import server.database.Database;
import shared.model.Inputvalues;

public class InputValAccess {
	
	private Database db;
	
	public InputValAccess(Database db) {
		this.db = db;
	}
	
	/**
	 * Gets all inputValues
	 * @return List of InputValues
	 */
	public ArrayList<Inputvalues> getAllValues(){
		return null;
	}
	/**
	 * Gets Values by Project ID
	 * @param project_id
	 * @return List of Inputvalues
	 */
	public ArrayList<Inputvalues> getValByProjectID(int project_id){
		return null;
	}
	/**
	 * Gets Values by Field ID
	 * @param field_id
	 * @return List of Inputvalues
	 */
	public ArrayList<Inputvalues> getValByFieldID(int field_id){
		return null;
	}
	
	/**
	 * Adds Values
	 * @param newValue
	 * @return new inputValue
	 */
	public Inputvalues addValue(Inputvalues newValue){
		return null;
	}
	/**
	 * Update a Value
	 * @param value
	 */
	public void updateValue(Inputvalues value){
	}
}
