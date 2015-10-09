package shared.model;

/**
 * Holds the information for the Field class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */

public class Fields {
	private int id;
	private String title;
	private int xcoord;
	private int width;
	private String helphtml;
	private String knowndata;
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
