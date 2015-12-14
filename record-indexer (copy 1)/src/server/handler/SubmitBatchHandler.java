package server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.ServerException;
import server.facade.ServerFacade;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SubmitBatchHandler implements HttpHandler{

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		SubmitBatch_Params params = (SubmitBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
		SubmitBatch_Result result = new SubmitBatch_Result();
		try {
			result = ServerFacade.submitBatch(params);
		}
		catch (ServerException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	
	}

}
