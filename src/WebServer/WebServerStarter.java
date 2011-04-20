/**
 * 
 */
package WebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import answerMachine.CallHandler;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 20, 2011
 * 
 */
public class WebServerStarter implements Runnable {
	private final static Logger log = Logger.getLogger(WebServerStarter.class);
		private int httpPort;

	/**
 * 
 */
	public WebServerStarter(String httpPort) {
		this.httpPort = Integer.parseInt(httpPort.trim());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		try {
			ServerSocket serverSocket = new ServerSocket(httpPort);
			while (true) {

				Socket clientSocket = serverSocket.accept();
				Thread requestHandler = new Thread(new Listener(clientSocket));
				requestHandler.start();
				log.info("thread started for: "+clientSocket.getInetAddress().getHostName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
