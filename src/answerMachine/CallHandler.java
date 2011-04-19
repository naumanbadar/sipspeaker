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
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import configuration.Configuration;

import sipParser.SipHeader;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 8, 2011
 * 
 */
public class CallHandler {
	private final static Logger log = Logger.getLogger(CallHandler.class.getName());

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
}
