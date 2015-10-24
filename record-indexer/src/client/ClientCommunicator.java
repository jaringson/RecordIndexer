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
	
	/**Returns Shared Communicator Class: ValidateUser_Result
	 * @return ValidateUser_Result
	 * @param ValidateUser_Params
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params user) {
		return null;
	}
	
	/** Returns Shared Communicator Class: GetProjects_Result 
	 * @return GetProjects_Result 
	 * @param GetProjects_Params 
	 */
	public GetProjects_Result getProjects(GetProjects_Params proj){
		return null;
	}
	
	/** Returns Shared Communicator Class: GetSampleImg_Result
	 * @return GetSampleImg_Result
	 * @param GetSampleImg_Params
	 */
	public GetSampleImg_Result getSampleImage(GetSampleImg_Params img){
		return null;
		
	}
	
	/** Returns Shared Communicator Class: DownloadImage_Result
	 * @return DownloadImage_Result
	 * @param DownloadBatch_Params
	 */
	public DownloadBatch_Result downloadImage(DownloadBatch_Params down){
		return null;
		
	}
	/** Returns Shared Communicator Class: SubmitImage_Result
	 * @return SubmitImage_Result
	 * @param SubmitImage_Params
	 */
	public SubmitImage_Result submitBatch(SubmitImage_Params sub){
		return null;
	}
	/** Returns Shared Communicator Class: GetFields_Result
	 * @return GetFields_Result
	 * @param GetFields_Params
	 */
	public GetFields_Result getFeilds(GetFields_Params field){
		return null;
		
	}
	/** Returns Shared Communicator Class:Search_Result 
	 * @return Search_Result 
	 * @param Search_Params
	 */
	public Search_Result search(Search_Params search){
		return null;
		
	}

	
	public void downloadFile(){
		
	}
}
