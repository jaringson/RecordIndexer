package client;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;

public class GetFeildsTest {
	
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
		GetFields_Params params = new GetFields_Params();
		GetFields_Result result  = new GetFields_Result();
		
		params.setUsername("sheila");
		params.setPassword("parker");
		params.setProjectID(1);
		
		result = communicator.getFeilds(params);
		String expected = "1\n"
				+ "1\n"
				+ "Last Name\n"
				+ "1\n"
				+ "2\n"
				+ "First Name\n"
				+ "1\n"
				+ "3\n"
				+ "Gender\n"
				+ "1\n"
				+ "4\n"
				+ "Age\n";
		
		assertEquals(expected, result.toString());
	}

}
