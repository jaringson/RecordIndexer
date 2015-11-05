package client;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.model.User;

public class DownloadBatchTest {

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
		DownloadBatch_Params params = new DownloadBatch_Params();
		DownloadBatch_Result result  = new DownloadBatch_Result();
		
		params.setUsername("sheila");
		params.setPassword("parker");
		params.setProjectID(1);
		
		result = communicator.downloadImage(params);
		
		String expected = "1\n"
				+ "http://localhost:38440/Records/images/1890_image0.png\n"
				+ "199\n"
				+ "60\n"
				+ "8\n"
				+ "4\n"
				+ "1\n"
				+ "Last Name\n"
				+ "http://localhost:38440/Records/fieldhelp/last_name.html\n"
				+ "60\n"
				+ "300\n"
				+ "http://localhost:38440/Records/knowndata/1890_last_names.txt\n"
				+ "2\n"
				+ "First Name\n"
				+ "http://localhost:38440/Records/fieldhelp/first_name.html\n"
				+ "360\n"
				+ "280\n"
				+ "http://localhost:38440/Records/knowndata/1890_first_names.txt\n"
				+ "3\n"
				+ "Gender\n"
				+ "http://localhost:38440/Records/fieldhelp/gender.html\n"
				+ "640\n"
				+ "205\n"
				+ "http://localhost:38440/Records/knowndata/genders.txt\n"
				+ "4\n"
				+ "Age\n"
				+ "http://localhost:38440/Records/fieldhelp/age.html\n"
				+ "845\n"
				+ "120\n";
		assertEquals(expected,result.toString());
	}

}
