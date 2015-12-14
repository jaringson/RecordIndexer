package shared.communication;

import java.util.ArrayList;

import client.ClientCommunicator;

public class Search_Result {
	ArrayList<ArrayList<String>> tuples = new ArrayList<ArrayList<String>>();

	public Search_Result() {
		tuples = null;
	}

	public ArrayList<ArrayList<String>> getTuples() {
		return tuples;
	}

	public void setTuples(ArrayList<ArrayList<String>> tuples) {
		this.tuples = tuples;
	}
	@Override
	public String toString(){
		StringBuilder ss = new StringBuilder();
		if(tuples.size() ==0){
			return "FAILED";
		}
		for(int i =0;i<tuples.size();i++){
			ss.append(tuples.get(i).get(0) + "\n");
			ss.append(ClientCommunicator.getURLPrefix()+"/Records/"+ tuples.get(i).get(1) + "\n");
			ss.append(tuples.get(i).get(2) + "\n");
			ss.append(tuples.get(i).get(3) + "\n");
		}
		return ss.toString();
	}
}
