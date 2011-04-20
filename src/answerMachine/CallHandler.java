/**
 * 
 */
package answerMachine;

import helpers.TraceLoader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import configuration.Configuration;

import sipParser.Contact;
import sipParser.SipHeader;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 8, 2011
 * 
 */
public class CallHandler {
	private final static Logger log = Logger.getLogger(CallHandler.class.getName());
	private static Hashtable<Contact, Thread> runningCalls = new Hashtable<Contact, Thread>();

	public static void handleInvite(String receivedData, DatagramPacket datagramPacket, DatagramSocket datagramSocket) {
		try {

			// log.info("*********************************************Received Data\n"
			// + receivedData.trim());
			// SipHeader sipHeader = new SipHeader("5061", "IBN BAD'R",
			// "IK2213_SIP_SPEAKER", receivedData);
			SipHeader sipHeader = new SipHeader(Configuration.INSTANCE.getSipPort(), Configuration.INSTANCE.getSipUser(), "IK2213_SIP_SPEAKER", receivedData);

			// log.info("*********************************************Sent OK\n"
			// + sipHeader.produceSipOK());

			byte[] byteBuffer = sipHeader.produceSipTrying().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

			byteBuffer = sipHeader.produceSipRinging().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

			// byteBuffer = sipHeader.produceSipOK().getBytes();
			// datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			// datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			// datagramPacket.setData(byteBuffer);
			// datagramSocket.send(datagramPacket);

			// Thread speakerThread = new Thread(new
			// Speaker(sipHeader.getContact().getIpAddress(),
			// sipHeader.getSipPort(),
			// "/home/naumanbadar/Downloads/chaotic.wav"));
			// Thread speakerThread = new Thread(new Speaker(sipHeader,
			// datagramSocket, "/home/naumanbadar/Downloads/chaotic.wav"));
			Thread speakerThread = new Thread(new Speaker(sipHeader, datagramSocket, Configuration.INSTANCE.getPlayMessage()));
			runningCalls.put(new Contact(datagramPacket.getAddress().getHostAddress(), Integer.toString(datagramPacket.getPort())), speakerThread);
			speakerThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param receivedData
	 * @param datagramPacket
	 * @param datagramSocket
	 */
	public static void handleWrongInvite(String receivedData, DatagramPacket datagramPacket, DatagramSocket datagramSocket) {
		try {
			SipHeader sipHeader = new SipHeader(Configuration.INSTANCE.getSipPort(), Configuration.INSTANCE.getSipUser(), "IK2213_SIP_SPEAKER", receivedData);

			// log.info("*********************************************Sent OK\n"
			// + sipHeader.produceSipOK());

			byte[] byteBuffer = sipHeader.produceSipNotFound().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param receivedData
	 * @param datagramPacket
	 * @param datagramSocket
	 */
	public static void handleBye(String receivedData, DatagramPacket datagramPacket, DatagramSocket datagramSocket) {
		try {

			SipHeader sipHeader = new SipHeader(Configuration.INSTANCE.getSipPort(), Configuration.INSTANCE.getSipUser(), "IK2213_SIP_SPEAKER", receivedData);

			Contact key = new Contact(datagramPacket.getAddress().getHostAddress(), Integer.toString(datagramPacket.getPort()));
//			log.info("key constructed: " + key);
			if (!runningCalls.containsKey(key)) {
				return;
			}
			Thread runningcall = runningCalls.get(key);
			runningcall.stop();
//			log.info("registered in running calls register");

//			log.info("Bye received");
			// log.info(sipHeader.produceByeOK());
//			log.info(receivedData);

			byte[] byteBuffer = sipHeader.produceByeOK().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
