package shared.communication;

import java.util.ArrayList;

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
}
