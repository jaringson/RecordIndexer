package servertester.controllers;

import java.util.*;

import client.ClientCommunicator;
import client.ClientException;
import servertester.views.*;
import shared.communication.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		String host = _view.getHost();
		String port = _view.getPort();
		String[] values = _view.getParameterValues();
		String username = values[0];
		String password = values[1];
		ClientCommunicator cc = new ClientCommunicator(host,Integer.parseInt(port));
		ValidateUser_Params params = new ValidateUser_Params();
		params.setPassword(password);
		params.setUsername(username);
		ValidateUser_Result result;
		try {
			result = cc.validateUser(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED");
			e.printStackTrace();
			return;
		}
		_view.setResponse(result.toString());
	}
	
	private void getProjects() {
		String host = _view.getHost();
		String port = _view.getPort();
		String[] values = _view.getParameterValues();
		String username = values[0];
		String password = values[1];
		ClientCommunicator cc = new ClientCommunicator(host,Integer.parseInt(port));
		GetProjects_Params params = new GetProjects_Params();
		params.setPassword(password);
		params.setUsername(username);
		GetProjects_Result result;
		try {
			result = cc.getProjects(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED");
			e.printStackTrace();
			return;
		}
		_view.setResponse(result.toString());
	}
	
	private void getSampleImage() {
		String host = _view.getHost();
		String port = _view.getPort();
		String[] values = _view.getParameterValues();
		String username = values[0];
		String password = values[1];
		String strprojectID = values[2];
		ClientCommunicator cc = new ClientCommunicator(host,Integer.parseInt(port));
		GetSampleImg_Params params = new GetSampleImg_Params();
		params.setPassword(password);
		params.setUsername(username);
		params.setProjectID(Integer.parseInt(strprojectID));
		GetSampleImg_Result result;
		try {
			result = cc.getSampleImage(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED");
			e.printStackTrace();
			return;
		}
		_view.setResponse(result.toString());
	}
	
	private void downloadBatch() {
		String host = _view.getHost();
		String port = _view.getPort();
		String[] values = _view.getParameterValues();
		String username = values[0];
		String password = values[1];
		String strprojectID = values[2];
		ClientCommunicator cc = new ClientCommunicator(host,Integer.parseInt(port));
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setPassword(password);
		params.setUsername(username);
		params.setProjectID(Integer.parseInt(strprojectID));
		DownloadBatch_Result result;
		try {
			result = cc.downloadImage(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED");
			e.printStackTrace();
			return;
		}
		_view.setResponse(result.toString());
	}
	
	private void submitBatch() {
		String host = _view.getHost();
		String port = _view.getPort();
		String[] values = _view.getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(host,Integer.parseInt(port));
		SubmitBatch_Params params = new SubmitBatch_Params();
		
		String username = values[0];
		String password = values[1];
		String batchID = values[2];
		String record = values[3];

		params.setPassword(password);
		params.setUsername(username);
		params.setBatchID(Integer.parseInt(batchID));
		
		ArrayList<ArrayList<String>> records_final = new ArrayList<ArrayList<String>>();
		String[] recordsSplit = record.split(";");

		for(String split:recordsSplit){
			
			ArrayList<String> records_group = new ArrayList<String>();
			String[] splits_two = split.split(",");
			
			for(String s:splits_two){
				records_group.add(s);
			}

			records_final.add(records_group);
		}
		params.setValues(records_final);

		SubmitBatch_Result result;
		try {
			result = cc.submitBatch(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED");
			e.printStackTrace();
			return;
		}
		_view.setResponse(result.toString());
	}
	
	private void getFields() {
		String host = _view.getHost();
		String port = _view.getPort();
		String[] values = _view.getParameterValues();
		String username = values[0];
		String password = values[1];
		String strProjectID = values[2];
	
		GetFields_Params params = new GetFields_Params();
		ClientCommunicator cc = new ClientCommunicator(host,Integer.parseInt(port));
		
		params.setPassword(password);
		params.setUsername(username);
		if(!values[2].equals("")){
			params.setProjectID(Integer.parseInt(strProjectID));
		}
		
		GetFields_Result result;
		try {
			result = cc.getFeilds(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED");
			e.printStackTrace();
			return;
		}
		_view.setResponse(result.toString());
	}
	
	private void search() {
		String host = _view.getHost();
		String port = _view.getPort();
		String[] values = _view.getParameterValues();
		String username = values[0];
		String password = values[1];
		String strFields = values[2];
		String strValues = values[3];
		ClientCommunicator cc = new ClientCommunicator(host,Integer.parseInt(port));
		Search_Params params = new Search_Params();
		
		String[] splitfeilds = strFields.split(",");
		ArrayList<Integer> feilds = new ArrayList<Integer>();
		for(int i =0;i<splitfeilds.length;i++){
			feilds.add(Integer.parseInt(splitfeilds[i]));
		}
		String[] searchValues = strValues.split(",");
		
		params.setPassword(password);
		params.setUsername(username);
		params.setFieldIDs(feilds);
		params.setSearchValues(searchValues);
		
		Search_Result result;
		try {
			result = cc.search(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED");
			e.printStackTrace();
			return;
		}
		_view.setResponse(result.toString());
	}
}

