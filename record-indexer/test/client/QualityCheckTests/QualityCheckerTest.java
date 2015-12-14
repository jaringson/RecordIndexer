package client.QualityCheckTests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.BatchState;
import client.ClientCommunicator;
import client.qualitycheck.QualityCheck;

public class QualityCheckerTest {

	@Test
	public void firstnameTest() {
		QualityCheck test = new QualityCheck(new BatchState());
		test.url = "http://localhost:44443/Records/knowndata/1890_first_names.txt";
		test.inputWord = "Dania";
		
		String result = test.runQualityCheck();
		
		assertTrue(result.equals("Dalia"));
	}
	
	@Test
	public void lastnameTest() {
		QualityCheck test = new QualityCheck(new BatchState());
		test.url = "http://localhost:44443/Records/knowndata/1890_last_names.txt";
		test.inputWord = "Duar";
		
		String result = test.runQualityCheck();
		
		assertTrue(result.equals("Duke"));
	}
	
	@Test
	public void ethnicityTest() {
		QualityCheck test = new QualityCheck(new BatchState());
		test.url = "http://localhost:44443/Records/knowndata/ethnicities.txt";
		test.inputWord = "wit";

		String result = test.runQualityCheck();
		
		assertTrue(result.equals("white"));
	}
	
	@Test
	public void nullTest() {
		QualityCheck test = new QualityCheck(new BatchState());
		test.url = "http://localhost:44443/Records/knowndata/ethnicities.txt";
		test.inputWord = "wi";

		String result = test.runQualityCheck();
		
		assertTrue(result == null);
	}
	
	

}
