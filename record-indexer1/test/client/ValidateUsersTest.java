package client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import shared.communication.*;
import shared.model.*;
import client.ClientCommunicator;

public class ValidateUsersTest {
	private static Database database = new Database();
 	private static ClientCommunicator communicator =  ClientUnitTests.getCC();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		database.startTransaction();
		database.recreateTables("database.sql");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		database.endTransaction(false);
	}
	
	@Test
	public void test() throws ClientException {

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
		
	}

}
