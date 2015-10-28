package client;

import java.io.*;
import java.net.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import client.*;
/**
 * This Class communicates with the Server by sending information through objects
 * in the Shared Communicator Classes.
 * 
 * @author jaronce
 *
 */

public class ClientCommunicator {
	
	
	
	private static final String SERVER_HOST = "localhost";
	private static final int SERVER_PORT = 38431;
	private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";

	private XStream xmlStream;

	public ClientCommunicator() {
		xmlStream = new XStream(new DomDriver());
	}

	
	private Object doGet(String urlPath) throws ClientException {
		try {
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else {
				throw new ClientException(String.format("doGet failed: %s (http code %d)",
											urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
		}
	}
	
	private Object doPost(String urlPath, Object postData) throws ClientException {
		try {
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else {
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
											urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}
	
	
	
	/**Returns Shared Communicator Class: ValidateUser_Result
	 * @return ValidateUser_Result
	 * @param ValidateUser_Params
	 * @throws ClientException 
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ClientException {
		return (ValidateUser_Result)doPost("/ValidateUser", params);
	}
	
	/** Returns Shared Communicator Class: GetProjects_Result 
	 * @return GetProjects_Result 
	 * @param GetProjects_Params 
	 * @throws ClientException 
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) throws ClientException{
		return (GetProjects_Result)doPost("/GetProjects", params);
	}
	
	/** Returns Shared Communicator Class: GetSampleImg_Result
	 * @return GetSampleImg_Result
	 * @param GetSampleImg_Params
	 * @throws ClientException 
	 */
	public GetSampleImg_Result getSampleImage(GetSampleImg_Params params) throws ClientException{
		return (GetSampleImg_Result)doPost("/GetSampleImage", params);
		
	}
	
	/** Returns Shared Communicator Class: DownloadImage_Result
	 * @return DownloadImage_Result
	 * @param DownloadBatch_Params
	 * @throws ClientException 
	 */
	public DownloadBatch_Result downloadImage(DownloadBatch_Params params) throws ClientException{
		return (DownloadBatch_Result)doPost("/DownloadBatch", params);
		
	}
	/** Returns Shared Communicator Class: SubmitImage_Result
	 * @return SubmitImage_Result
	 * @param SubmitBatch_Params
	 * @throws ClientException 
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ClientException{
		return (SubmitBatch_Result)doPost("/SubmitBatch", params);
	}
	/** Returns Shared Communicator Class: GetFields_Result
	 * @return GetFields_Result
	 * @param GetFields_Params
	 * @throws ClientException 
	 */
	public GetFields_Result getFeilds(GetFields_Params params) throws ClientException{
		return (GetFields_Result)doPost("/GetFields", params);
		
	}
	/** Returns Shared Communicator Class:Search_Result 
	 * @return Search_Result 
	 * @param Search_Params
	 * @throws ClientException 
	 */
	public Search_Result search(Search_Params params) throws ClientException{
		return (Search_Result)doPost("/ValidateUser", params);
		
	}

	
	public void downloadFile(){
		
	}
}
