package shared.communication;

public class GetSampleImg_Result {
	private String file;

	public GetSampleImg_Result() {
		file = null;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	@Override
	public String toString(){
		StringBuilder ss = new StringBuilder();
		ss.append(file+"\n");
		return ss.toString();
	}
}
