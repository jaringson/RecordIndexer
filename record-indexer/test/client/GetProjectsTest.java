package client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Server;
import server.database.Database;
import server.importer.DataImporter;
import shared.communication.*;
import shared.model.*;

public class GetProjectsTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Load data
		DataImporter importer = new DataImporter();
		String[] str = null;
		str[0] = "Records.xml";
		importer.main(str);
		String[] str2 = null;
		str2[0] = "38431";
		Server.main(str2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	
	@Test
	public void test() throws ClientException {

		
	ClientCommunicator communicator = new ClientCommunicator();
	GetProjects_Params params = new GetProjects_Params();
	GetProjects_Result result  = new GetProjects_Result();
	
	ArrayList<Project> actual = new ArrayList<Project>();
	actual.add(new Project(1,"1890 Census",8,199,60));
	actual.add(new Project(2, "1900 Census",10,204,62));
	actual.add(new Project(3,"Draft Records",7,195,65));
	
	
	params.setUsername("sheila");
	params.setPassword("parker");
	
	result = communicator.getProjects(params);
	
	List<Project> projects = result.getProjects();
	for(int i =0; i < projects.size();i++){
		//compareProjects(p, actual,get(i));
	}

	//compareProjects(expected, actual, false);
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
