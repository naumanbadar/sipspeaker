/**
 * 
 */
package WebServer;

import java.io.BufferedReader;
import java.io.PrintWriter;

import configuration.Configuration;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Mar 31, 2011
 *
 */
public class GetViewMessageHandler {

	/**
	 * @param inputBufferReader
	 * @param outputPrintWriter
	 * @param completeHeader
	 */
	public static void handle(BufferedReader inputBufferReader, PrintWriter outputPrintWriter, StringBuilder completeHeader) {
//		StateManager stateManager  = StateManager.INSTANCE;
//		String statusString = EmailStatusHTML.spitHTML(stateManager.getEmailList());
		
		outputPrintWriter.println("HTTP/1.1 200 OK\r\n");
//		outputPrintWriter.println("<HTML><HEAD><TITLE>Hello from MANEN</TITLE></HEAD><BODY><H1>Pay your bill.</H1></BODY></HTML>");
//		outputPrintWriter.print(statusString);
		
		
		StringBuilder htmlData;
		htmlData = new StringBuilder();
		htmlData.append("<HTML>");
		htmlData.append("<HEAD><Title>Webmail</Title><META HTTP-EQUIV=\"REFRESH\" CONTENT=\"5\"><META http-equiv=Content-Type content=\"text/html; charset=utf-8\"></HEAD>");
		htmlData.append("<BODY bgcolor=\"#ff9900\">");
		htmlData.append("<H1>");
		htmlData.append(Configuration.INSTANCE.getCurrentText());
		htmlData.append("</H1>");
		htmlData.append("</BODY>");
		htmlData.append("</HTML>");
		
		outputPrintWriter.print(htmlData);
		
	}

}
