package shared.communication;

public class SubmitBatch_Result {
	private Boolean success;
	

	public SubmitBatch_Result() {
		super();
		success = false;
	}
	

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	@Override
	public String toString(){
		return "True";
	}
}
