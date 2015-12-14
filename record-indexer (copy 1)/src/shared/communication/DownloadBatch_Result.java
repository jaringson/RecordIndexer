package shared.communication;

import java.util.List;

import client.ClientCommunicator;
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
	@Override
	public String toString() {
		String url = ClientCommunicator.getURLPrefix();
		StringBuilder ss = new StringBuilder();
		ss.append(batch.getId()+"\n");
		ss.append(batch.getProjectid() +"\n");
		ss.append(url+"/Records/"+batch.getFile() + "\n");
		ss.append(project.getFirstycoordinate()+"\n");
		ss.append(project.getRecordheight()+"\n");
		ss.append(project.getRecordsperimage()+"\n");
		ss.append(fields.size() + "\n");
		for(Field field:fields){
			ss.append(field.getId()+"\n");
			ss.append(field.getColumnnumber()+"\n");
			ss.append(field.getTitle()+"\n");
			ss.append(url+"/Records/"+ field.getHelphtml()+"\n");
			ss.append(field.getXcoord()+"\n");
			ss.append(field.getWidth()+"\n");
			if(!field.getKnowndata().equals("")){
				ss.append(url+"/Records/"+ field.getKnowndata()+"\n");
			}
			
		}
		return ss.toString();
	}
	
}
