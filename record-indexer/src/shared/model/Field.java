package shared.model;


/**
 * Holds the information for the Field class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */

public class Field {
	private int id;
	int project_id;
	private String title;
	private int xcoord;
	private int width;
	private String helphtml;
	private String knowndata;
	private int columnnumber;
	
	
	public Field(int id, int project_id, String title,
			int xcoord, int width, String helphtml, String knowndata,
			int columnnumber) {
		super();
		this.id = id;
		this.project_id = project_id;
		this.title = title;
		this.xcoord = xcoord;
		this.width = width;
		this.helphtml = helphtml;
		this.knowndata = knowndata;
		this.columnnumber = columnnumber;
	}
	
	public Field() {
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	
	public int getColumnnumber() {
		return columnnumber;
	}
	public void setColumnnumber(int columnnumber) {
		this.columnnumber = columnnumber;
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
	public int getXcoord() {
		return xcoord;
	}
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getHelphtml() {
		return helphtml;
	}
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}
	public String getKnowndata() {
		return knowndata;
	}
	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
	}
}
