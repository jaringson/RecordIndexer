package shared.communication;

import java.util.List;

import shared.model.Field;

public class GetFields_Result {
	private List<Field> fields;

	public GetFields_Result() {
		fields = null;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	@Override
	public String toString(){
		StringBuilder ss = new StringBuilder();
		for(Field field: fields){
			ss.append(field.getProject_id()+"\n");
			ss.append(field.getId()+"\n");
			ss.append(field.getTitle() +"\n");
		}
		return ss.toString();
	}
}
