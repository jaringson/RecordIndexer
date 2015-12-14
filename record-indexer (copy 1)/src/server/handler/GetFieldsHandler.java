package server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.ServerException;
import server.facade.ServerFacade;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetFieldsHandler implements HttpHandler{

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetFields_Params params = (GetFields_Params)xmlStream.fromXML(exchange.getRequestBody());
		GetFields_Result result = new GetFields_Result();
		try {
			result = ServerFacade.getFields(params);
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
