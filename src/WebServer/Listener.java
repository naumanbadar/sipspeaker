package WebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Mar 31, 2011
 * 
 */
public class Listener {

	private final static Logger log = Logger.getLogger(Listener.class.getName());

	private ServerSocket serverSocket;
	private int httpPort;

	/**
	 * 
	 */
	public Listener(int port) {
		httpPort = port;
	}

	public String start() {
		try {

			// char charb[]= new char[2000];
			serverSocket = new ServerSocket(httpPort);
			// serverSocket = new ServerSocket(8888);

			Socket clientSocket = serverSocket.accept();
			BufferedReader inputBufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter outputPrintWriter = new PrintWriter(clientSocket.getOutputStream());
			// while
			// (inputStringBuilder.append(iputBufferReader.readLine()).length()<2000)
			// {
			String requestHeader;
			// while ((inputString=iputBufferReader.readLine())!=null) {
			// inputStringBuilder.append(inputString);
			// // Pattern.matches("", input)
			// }
			requestHeader = inputBufferReader.readLine();

			// this is just to get all the lines after header untill an empty
			// line is not received.
			StringBuilder completeHeader = new StringBuilder(requestHeader + "\r\n");
			String readline;
			while ((readline = inputBufferReader.readLine()) != "") { // to
																		// flush
																		// all
																		// the
																		// lines
				if (readline.isEmpty()) // when empty line is encountered.
					break;
				completeHeader.append(readline + "\r\n");
			}

			if (Pattern.matches("^GET / .*", requestHeader) || Pattern.matches("^GET /index.*", requestHeader)) {
				// log.info(completeHeader);
				GetIndexHandler.handle(inputBufferReader, outputPrintWriter, completeHeader);
			}

			else if (Pattern.matches("^POST /update.*", requestHeader)) {
				PostUpdateHandler.handle(inputBufferReader, outputPrintWriter, completeHeader);
				// log.info(completeHeader);
			} else if (Pattern.matches("^GET /viewMessage.*", requestHeader)) {
				 GetViewMessageHandler.handle(inputBufferReader, outputPrintWriter,completeHeader);
				 log.info("view STATUS");
			}

			// ipbuff.read(charb);
			outputPrintWriter.close();
			inputBufferReader.close();
			serverSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
