package server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.ServerException;
import server.facade.ServerFacade;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetProjectsHandler implements HttpHandler{

//private Logger logger = Logger.getLogger("contactmanager"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetProjects_Params params = (GetProjects_Params)xmlStream.fromXML(exchange.getRequestBody());
		GetProjects_Result result = new GetProjects_Result();
		try {
			result = ServerFacade.getProjects(params);
		}
		catch (ServerException e) {
           // logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	
	}
}
