package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import server.facade.ServerFacade;
import shared.communication.GetSampleImg_Params;
import shared.communication.GetSampleImg_Result;
import shared.model.Batch;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetSampleImageHandler implements HttpHandler{

	private XStream xmlStream = new XStream(new DomDriver());	
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GetSampleImg_Params params = (GetSampleImg_Params)xmlStream.fromXML(exchange.getRequestBody());
		User user = params.getUser();
		int projectID = params.getProjectID();
		String file = null;
		try {
			file = ServerFacade.getSampleImage(user, projectID);
		}
		catch (ServerException e) {
	       // logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		
		GetSampleImg_Result result = new GetSampleImg_Result();
		result.setFile(file);
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();

	}

}
