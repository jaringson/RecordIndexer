package shared.model;

import java.util.ArrayList;

/**
 * Holds the information for the Project class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */
public class Project {
	private int id;
	private int batch_id;
	private String title;
	private int recordsperimage;
	private int firstycoordinate;
	private int recordheight;
	private ArrayList<Field> fields;
	private ArrayList<Batch> images;
	
	public ArrayList<Batch> getImages() {
		return images;
	}
	public void setImages(ArrayList<Batch> images) {
		this.images = images;
	}
	public ArrayList<Field> getFields() {
		return fields;
	}
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
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
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRecordsperimage() {
		return recordsperimage;
	}
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}
	public int getFirstycoordinate() {
		return firstycoordinate;
	}
	public void setFirstycoordinate(int firstycoordinate) {
		this.firstycoordinate = firstycoordinate;
	}
	public int getRecordheight() {
		return recordheight;
	}
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
}
