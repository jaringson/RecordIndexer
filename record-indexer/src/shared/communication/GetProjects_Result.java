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
}
