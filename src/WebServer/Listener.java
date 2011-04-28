package WebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Mar 31, 2011
 * 
 */
public class Listener implements Runnable{

	private final static Logger log = Logger.getLogger(Listener.class.getName());

	private Socket clientSocket;

	/**
	 * 
	 */
	public Listener(Socket clientSocket) {
		this.clientSocket=clientSocket;
	}

	public String httpHandler() {
		try {

			BufferedReader inputBufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter outputPrintWriter = new PrintWriter(clientSocket.getOutputStream());
			String requestHeader;
			requestHeader = inputBufferReader.readLine();

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

			outputPrintWriter.close();
			inputBufferReader.close();
			clientSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		httpHandler();
		
	}

}
