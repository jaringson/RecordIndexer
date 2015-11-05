package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.facade.ServerFacade;
import shared.communication.Search_Params;
import shared.communication.Search_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SearchHandler implements HttpHandler{

private XStream xmlStream = new XStream(new DomDriver());	
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Search_Params params = (Search_Params)xmlStream.fromXML(exchange.getRequestBody());
		Search_Result result = new Search_Result();
		try {
			result = ServerFacade.search(params);
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
