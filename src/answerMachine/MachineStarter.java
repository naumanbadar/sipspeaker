/**
 * 
 */
package answerMachine;

import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import WebServer.WebServerStarter;

import configuration.Configuration;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 12, 2011
 * 
 */
public class MachineStarter {
	public static void main(String[] args) {
		final Logger log = Logger.getLogger(MachineStarter.class);
		try {

			Configuration.INSTANCE.insert(args);
			DatagramSocket datagramSocket;
			// log.info("current message set to :"+Configuration.INSTANCE.getCurrentMessage());

			Thread webServerThread = new Thread(new WebServerStarter(Configuration.INSTANCE.getHttpPort()));
			webServerThread.start();
			log.info("Web Server started on port " + Configuration.INSTANCE.getHttpPort());

			datagramSocket = new DatagramSocket(Integer.parseInt(Configuration.INSTANCE.getSipPort()));
			log.info("SipSpeaker started on port " + Configuration.INSTANCE.getSipPort() + " for user " + Configuration.INSTANCE.getSipUser());
			while (true) {

				PacketReciever.receive(datagramSocket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
