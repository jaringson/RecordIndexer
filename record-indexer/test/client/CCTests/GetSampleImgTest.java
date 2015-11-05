package client.CCTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import client.ClientCommunicator;
import client.ClientException;
import client.ClientUnitTests;
import server.database.Database;
import shared.communication.GetSampleImg_Params;
import shared.communication.GetSampleImg_Result;
import shared.model.Project;
import shared.model.User;

public class GetSampleImgTest {
	private static Database database = new Database();
 	private static ClientCommunicator communicator =  ClientUnitTests.getCC();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		database.startTransaction();
//		database.recreateTables("database.sql");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		database.endTransaction(false);
	}
	@Test
	public void test() throws ClientException {
		GetSampleImg_Params params = new GetSampleImg_Params();
		GetSampleImg_Result result  = new GetSampleImg_Result();
		
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		
		params.setUsername("sheila");
		params.setPassword("parker");
		params.setProjectID(1);
		
		result = communicator.getSampleImage(params);

		assertEquals("http://localhost:38440/Records/images/1890_image0.png\n",result.toString());

	}

}
