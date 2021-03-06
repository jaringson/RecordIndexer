package server;

import java.io.IOException;
import java.net.HttpURLConnection;
//import java.util.ArrayList;
//import java.util.List;

import server.facade.ServerFacade;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownloadBatchHandler implements HttpHandler{

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		DownloadBatch_Params params = (DownloadBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
		DownloadBatch_Result result = new DownloadBatch_Result();
		try {
			 result = ServerFacade.downloadBatch(params);
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
