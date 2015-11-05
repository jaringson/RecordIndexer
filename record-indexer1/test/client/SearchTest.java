package client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.model.User;

public class SearchTest {

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
		Search_Params params = new Search_Params();
		Search_Result result  = new Search_Result();
		
		ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
		fieldIDs.add(10);
		
		String[] searchValues = new String[]{
				"FOX"
		};
		
		params.setUsername("sheila");
		params.setPassword("parker");
		params.setFieldIDs(fieldIDs);
		params.setSearchValues(searchValues);
		
		result = communicator.search(params);
		
		String expected = "41\n"
				+ "http://localhost:38440/Records/images/draft_image0.png\n"
				+ "1\n"
				+ "10\n";
		
		assertEquals(expected, result.toString());
	}

}
