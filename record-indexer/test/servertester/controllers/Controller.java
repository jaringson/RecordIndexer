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
		ClientCommunicator cc = new ClientCommunicator();
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
		ClientCommunicator cc = new ClientCommunicator();
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
		ClientCommunicator cc = new ClientCommunicator();
		GetSampleImg_Params params = new GetSampleImg_Params();
		params.setPassword(password);
		params.setUsername(username);
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
	}
	
	private void getFields() {
	}
	
	private void submitBatch() {
	}
	
	private void search() {
	}

}

