package shared.communication;

import shared.model.Project;
import java.util.*;

public class GetProjects_Result {
	private List<Project> projects;

	public GetProjects_Result() {
		projects = null;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	@Override
	public String toString(){
		StringBuilder ss = new StringBuilder();
		for(Project project: projects){
			ss.append(project.getId() +"\n");
			ss.append(project.getTitle()+"\n");
		}
		return ss.toString();
	}
}
