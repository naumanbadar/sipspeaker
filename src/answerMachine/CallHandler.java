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

			Contact key1 = new Contact(datagramPacket.getAddress().getHostAddress(), Integer.toString(datagramPacket.getPort()));
			if (runningCalls.containsKey(key1)) {
				return;
			}
			log.info("Call accepted from " + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort());
			SipHeader sipHeader = new SipHeader(Configuration.INSTANCE.getSipPort(), Configuration.INSTANCE.getSipUser(), "IK2213_SIP_SPEAKER", receivedData);


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

			Thread speakerThread = new Thread(new Speaker(sipHeader, datagramSocket, Configuration.INSTANCE.getPlayMessage(), runningCalls));
			Contact key2 = new Contact(datagramPacket.getAddress().getHostAddress(), Integer.toString(datagramPacket.getPort()));
			runningCalls.put(key2, speakerThread);
			speakerThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
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


			byte[] byteBuffer = sipHeader.produceSipNotFound().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
			if (!runningCalls.containsKey(key)) {
				return;
			}

			Thread runningcall = runningCalls.get(key);
			if (runningcall.isAlive()) {
				runningcall.stop();
			}

			byte[] byteBuffer = sipHeader.produceByeOK().getBytes();
			datagramPacket.setAddress(datagramPacket.getAddress());
			datagramPacket.setPort(datagramPacket.getPort());
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);
			runningCalls.remove(key);
			log.info("Client hang up from " + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param receivedData
	 * @param datagramPacket
	 * @param datagramSocket
	 */
	public static void handleCancel(String receivedData, DatagramPacket datagramPacket, DatagramSocket datagramSocket) {
		try {
			SipHeader sipHeader = new SipHeader(Configuration.INSTANCE.getSipPort(), Configuration.INSTANCE.getSipUser(), "IK2213_SIP_SPEAKER", receivedData);

			Contact key = new Contact(datagramPacket.getAddress().getHostAddress(), Integer.toString(datagramPacket.getPort()));
			if (!runningCalls.containsKey(key)) {
				return;
			}

			Thread runningcall = runningCalls.get(key);
			if (runningcall.isAlive()) {
				runningcall.stop();
			}

			byte[] byteBuffer = sipHeader.produceCancelOK().getBytes();
			datagramPacket.setAddress(datagramPacket.getAddress());
			datagramPacket.setPort(datagramPacket.getPort());
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);
			
			byteBuffer = sipHeader.requestTerminated().getBytes();
			datagramPacket.setAddress(datagramPacket.getAddress());
			datagramPacket.setPort(datagramPacket.getPort());
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);
			
			
			runningCalls.remove(key);
			log.info("Client cancelled from " + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
