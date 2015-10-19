package shared.model;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import server.importer.DataImporter;

/**
 * Holds the information for the Project class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */
public class Project {
	private int id;

	private String title;
	private int recordsperimage;
	private int firstycoordinate;
	private int recordheight;
	
	//private ArrayList<Field> fields;
	//private ArrayList<Batch> batches;
	

	
	public Project(){
		
	}
	
	public Project(int id, String title, int recordsperimage,
			int firstycoordinate, int recordheight) {
		this.id = id;
		this.title = title;
		this.recordsperimage = recordsperimage;
		this.firstycoordinate = firstycoordinate;
		this.recordheight = recordheight;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
