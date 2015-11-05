package client.CCTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import client.ClientCommunicator;
import client.ClientException;
import client.ClientUnitTests;
import server.Server;
import server.database.Database;
import server.importer.DataImporter;
import shared.communication.*;
import shared.model.*;

public class GetProjectsTest {
	
	
	
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
		compareProjects(result.getProjects().get(i), actual.get(i), false);
	}

}

	private void compareProjects(Project expected, Project actual, boolean compareIDs) {
	
		if (compareIDs) {
			assertEquals(expected.getId(), actual.getId());
		}
		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getRecordsperimage(), actual.getRecordsperimage());
		assertEquals(expected.getFirstycoordinate(), actual.getFirstycoordinate());
		assertEquals(expected.getRecordheight(), actual.getRecordheight());
		
	}
}
