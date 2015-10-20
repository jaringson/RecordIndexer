package shared.model;
/**
 * Holds the information for the Records class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */

public class Record {
	
	private int id;
	private int batch_id;
	private int row_number;
	
	public Record(){
		
	}
	
	public Record(int id, int batch_id, int row_number) {
		super();
		this.id = id;
		this.batch_id = batch_id;
		this.row_number = row_number;
	}
	public int getRow_number() {
		return row_number;
	}
	public void setRow_number(int row_number) {
		this.row_number = row_number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(int image_id) {
		this.batch_id = image_id;
	}
}
