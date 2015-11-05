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
import shared.communication.*;
import shared.model.User;

public class SubmitBatchTest {

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

	SubmitBatch_Params params = new SubmitBatch_Params();
	SubmitBatch_Result result  = new SubmitBatch_Result();
	
	ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
	ArrayList<String> value = new ArrayList<String>();
	value.add("values");
	value.add("ya");
	value.add("jaron");
	value.add("orange");
	values.add(value );
	params.setUsername("sheila");
	params.setPassword("parker");
	params.setBatchID(1);
	params.setValues(values);
	
	result = communicator.submitBatch(params);
	
	String expected = "FAILED";
	
	assertEquals(expected,result.toString());
	
	}

	

}
