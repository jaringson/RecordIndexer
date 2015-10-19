package shared.model;

import org.w3c.dom.Element;

import server.importer.DataImporter;

/**
 * Holds the information for the Batch class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */

public class Batch {
	private int id;
	private int projectid;
	private String file;
	boolean complete;
	boolean available;
	boolean checkedout;
	
	
	//	public Batch(Element item) {
//		file = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("firstycoord").item(0)));
//	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isCheckedout() {
		return checkedout;
	}
	public void setCheckedout(boolean checkedout) {
		this.checkedout = checkedout;
	}
	
	public int getId() {
		return id;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
}
