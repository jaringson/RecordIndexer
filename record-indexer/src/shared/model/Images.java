package shared.model;

/**
 * Holds the information for the Image class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */

public class Images {
	private int id;
	private int projectid;
	private String file;
	public int getId() {
		return id;
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
