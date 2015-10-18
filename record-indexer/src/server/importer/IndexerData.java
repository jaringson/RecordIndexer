package server.importer;

import java.util.ArrayList;

import org.w3c.dom.Element;

import shared.model.Project;
import shared.model.User;

//An Example of how to use “DataImporter.getChildElements(Element)
public class IndexerData {
	IndexerData(){
		
	}
	
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Project> projects = new ArrayList<Project>();
	public IndexerData(Element root) {
	ArrayList<Element> rootElements = DataImporter.getChildElements(root);
	ArrayList<Element> userElements =
	DataImporter.getChildElements(rootElements.get(0));
	for(Element userElement : userElements) {
	users.add(new User(userElement));
	}
	ArrayList<Element> projectElements =
	DataImporter.getChildElements(rootElements.get(1));
		for(Element projectElement : projectElements) {
			projects.add(new Project(projectElement));
		}
	}
}
