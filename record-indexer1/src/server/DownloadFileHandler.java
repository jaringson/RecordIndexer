package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.facade.ServerFacade;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetSampleImg_Params;
import shared.communication.GetSampleImg_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownloadFileHandler implements HttpHandler{
	
	private XStream xmlStream = new XStream(new DomDriver());	
	
		@Override
	public void handle(HttpExchange exchange) throws IOException {
			
//			System.out.println("Here in DownloadFile");
//			String params = exchange.get;
//			System.out.println(params);
//			File inputFile = new File(params);
//			InputStream input = null;
//			try {
//				System.out.println("Here in DownloadFile1");
//				input = new BufferedInputStream(
//							new FileInputStream(inputFile));
//				
//				byte[] chunk = new byte[512];
//				
//				int bytesRead;
//				System.out.println("Here in DownloadFile2");
//				while ((bytesRead = input.read(chunk)) > 0) {
//					exchange.getResponseBody().write(chunk, 0, bytesRead);
//				}				
//			}
//			finally {
//				System.out.println("Here in DownloadFile3");
//				if (input != null) {
//					input.close();
//				}
//		
//			}
//			System.out.println("Here in DownloadFile4");
//			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//			//xmlStream.toXML(result, exchange.getResponseBody());
//			exchange.getResponseBody().close();
			String s = "." + exchange.getRequestURI().toString();
			Path p = Paths.get(s);
			byte[] b = Files.readAllBytes(p);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, b.length);
			exchange.getResponseBody().write(b);
			exchange.getResponseBody().close();

	}
}
