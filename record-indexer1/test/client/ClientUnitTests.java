package client;

import org.junit.*;

import server.Server;
import server.database.Database;
import static org.junit.Assert.*;

public class ClientUnitTests {
	

 	private static ClientCommunicator communicator = new ClientCommunicator("localhost", 38440);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
		String[] str1 = new String[]{"38440"};
		Server.main(str1);
		
		
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

		String[] testClasses = new String[] {
				"client.ClientUnitTests",
				"client.GetProjectsTest",
				"client.ValidateUsersTest",
				"client.GetSampleImgTest",
				"client.DownloadBatchTest",
				"client.GetFeildsTest",
				"client.SearchTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

