package shared.communication;

import java.util.List;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;

/**
 * Contains an objects for Returning Results of DownloadImage
 * @author jaronce
 *
 */
public class DownloadBatch_Result {
	private Project project;
	private Batch batch;
	private List<Field> fields;
	
	public DownloadBatch_Result() {
		project = null;
		batch = null;
		fields = null;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
}
