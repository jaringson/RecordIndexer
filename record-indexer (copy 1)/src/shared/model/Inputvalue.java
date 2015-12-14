package shared.model;

/**
 * Holds the information for the Inputvalues class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */

public class Inputvalue {
	private int id;
	private int record_id;
	private int field_id;
	private String inputvalue;
	
	public Inputvalue(int id, int record_id, int field_id, String inputvalue) {
		super();
		this.id = id;
		this.record_id = record_id;
		this.field_id = field_id;
		this.inputvalue = inputvalue;
	}
	public Inputvalue() {

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getField_id() {
		return field_id;
	}
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}
	public String getInputvalue() {
		return inputvalue;
	}
	public void setInputvalue(String inputvalue) {
		this.inputvalue = inputvalue;
	}
}
