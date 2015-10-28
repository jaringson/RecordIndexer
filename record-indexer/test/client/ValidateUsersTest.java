package client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import shared.communication.*;
import shared.model.*;
import client.ClientCommunicator;

public class ValidateUsersTest {
	@Test
	public void test() throws ClientException {

	ClientCommunicator communicator = new ClientCommunicator();
	ValidateUser_Params params = new ValidateUser_Params();
	ValidateUser_Result result  = new ValidateUser_Result();
	
	User expected = new User(3,"sheila","parker","Sheila","Parker","sheila.parker@gmail.com",0,0);
	
	params.setUsername("sheila");
	params.setPassword("parker");
	
	result = communicator.validateUser(params);
	
	User actual = result.getUser();

	compareUsers(expected, actual, false);
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
