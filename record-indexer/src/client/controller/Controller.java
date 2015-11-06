package client.controller;

import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import client.ClientCommunicator;
import client.ClientException;

public class Controller {


	public static ValidateUser_Result validateUser(String username, String password) {

		ClientCommunicator cc = new ClientCommunicator();
		ValidateUser_Params params = new ValidateUser_Params();
		params.setPassword(password);
		params.setUsername(username);
		ValidateUser_Result result;
		try {
			result = cc.validateUser(params);
			return result;
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
