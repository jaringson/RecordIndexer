package shared.communication;

import client.ClientCommunicator;

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
		String url = ClientCommunicator.getURLPrefix();
		ss.append(url+"/Records/"+file+"\n");
		return ss.toString();
	}
}
