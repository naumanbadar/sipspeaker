package WebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import configuration.Configuration;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Mar 31, 2011
 *
 */
public class PostUpdateHandler {

	private final static Logger log = Logger.getLogger(PostUpdateHandler.class.getName());

	public static void handle(BufferedReader inputBufferReader, PrintWriter outputPrintWriter, StringBuilder completeHeader) {
		int indexContentLenght = completeHeader.toString().indexOf("Content-Length: ");
		String contentLengthString = completeHeader.toString().substring(indexContentLenght + 16, indexContentLenght + 19);
		int contentLenght = Integer.parseInt(contentLengthString.trim());
		
		char characterBuffer[] = new char[contentLenght];
		try {
			inputBufferReader.read(characterBuffer,0,contentLenght);
			String postBody = new String(characterBuffer);
			
			String fields[] = postBody.split("\\&?\\w*=");
			for (String string : fields) {
				log.info(string);
			}
//
			String newMessage = URLDecoder.decode(fields[1],"UTF-8");
			
			Configuration.INSTANCE.updateFromWebserver(newMessage.trim());
			log.info(newMessage);
			
			outputPrintWriter.println("HTTP/1.1 200 OK\r\n");
			outputPrintWriter.println("<HTML><HEAD><TITLE>IK2213 Web Mail</TITLE></HEAD><BODY bgcolor=\"#ff9900\"><H1>Your message has been submitted to SIP Speaker.</H1></BODY></HTML>");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

}
