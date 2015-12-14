package client;

import org.junit.*;

import server.Server;
import server.database.Database;
import server.importer.DataImporter;
import static org.junit.Assert.*;

public class ClientUnitTests {
	

 	private static ClientCommunicator communicator = new ClientCommunicator("localhost", 38440);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		String[] str1 = new String[]{"38440"};
		Server.main(str1);
		String[] args = new String[]{
			"/users/guest/j/jaronce/Desktop/Records/Records.xml"
		};
		DataImporter.main(args);
	
	}
	public static ClientCommunicator getCC(){
		return communicator;
	}
	
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {

		//These are for testing ClientCommunicator seperate from Server Unit Tests
		
//		String[] testClasses = new String[] {
//				"client.ClientUnitTests",
//				"client.CCTests.GetProjectsTest",
//				"client.CCTests.ValidateUsersTest",
//				"client.CCTests.GetSampleImgTest",
//				"client.CCTests.DownloadBatchTest",
//				"client.CCTests.GetFeildsTest",
//				"client.CCTests.SearchTest"
//		};
//
//		org.junit.runner.JUnitCore.main(testClasses);
	}
}

