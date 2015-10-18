package shared.model;

import org.w3c.dom.Element;

import server.importer.DataImporter;

/**
 * Holds the information for the Field class.
 * Contains all getters and setters.
 * 
 * @author jaronce
 *
 */

public class Field {
	private int id;
	private String title;
	private int xcoord;
	private int width;
	private String helphtml;
	private String knowndata;
	
	public Field(Element item) {
		title = DataImporter.getValue((Element)item.getElementsByTagName("title").item(0));
		xcoord = Integer.parseInt(DataImporter.getValue((Element)item.getElementsByTagName("xcoord").item(0)));
		width = Integer.parseInt(DataImporter.getValue((Element)item.getElementsByTagName("width").item(0)));
		helphtml = DataImporter.getValue((Element)item.getElementsByTagName("helphtml").item(0));
		knowndata = DataImporter.getValue((Element)item.getElementsByTagName("knowndata").item(0));
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
