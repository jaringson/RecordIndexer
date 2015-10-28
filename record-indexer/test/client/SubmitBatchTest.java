package client;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.communication.*;
import shared.model.User;

public class SubmitBatchTest {

	@Test
	public void test() throws ClientException {

	ClientCommunicator communicator = new ClientCommunicator();
	SubmitBatch_Params params = new SubmitBatch_Params();
	SubmitBatch_Result result  = new SubmitBatch_Result();
	
	User expected = new User(3,"sheila","parker","Sheila","Parker","sheila.parker@gmail.com",0,0);
	
	params.setUsername("sheila");
	params.setPassword("parker");
	
	/*result = communicator.validateUser(params);
	
	User actual = result.getUser();*/

	//compareUsers(expected, actual, false);
}

	private void compareUsers(User expected, User actual, boolean compareIDs) {
	
		if (compareIDs) {
			assertEquals(expected.getId(), actual.getId());
		}
		assertEquals(expected.getUsername(), actual.getUsername());
		assertEquals(expected.getPassword(), actual.getPassword());
		assertEquals(expected.getFirstname(), actual.getFirstname());
		assertEquals(expected.getLastname(), actual.getLastname());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getIndexrecords(), actual.getIndexrecords());
		assertEquals(expected.getCurBatch(), actual.getCurBatch());
	}

}
