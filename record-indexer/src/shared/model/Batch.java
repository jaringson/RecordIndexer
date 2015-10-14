package shared.model;

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
