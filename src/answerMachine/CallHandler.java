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

import sipParser.SipHeader;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 8, 2011
 * 
 */
public class CallHandler implements Runnable {
	private final static Logger log = Logger.getLogger(CallHandler.class.getName());

	public void handle(DatagramPacket datagramPacket) {

		String receivedData = new String(datagramPacket.getData());

		if (Pattern.matches("^INVITE sip:.*", receivedData)) {
			log.info("received invite from: " + datagramPacket.getAddress().getHostAddress());
		}
	}

	public static void handleInvite(String receivedData, DatagramPacket datagramPacket, DatagramSocket datagramSocket) {
		try {


//			log.info("*********************************************Received Data\n" + receivedData.trim());
			SipHeader sipHeader = new SipHeader("5061", "IBN BAD'R", "IK2213_SIP_SPEAKER", receivedData);

			TraceLoader.writeReceivedString(receivedData);
//			log.info("*********************************************Sent OK\n" + sipHeader.produceSipOK());

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

//			byteBuffer = sipHeader.produceSipOK().getBytes();
//			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
//			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
//			datagramPacket.setData(byteBuffer);
//			datagramSocket.send(datagramPacket);

			
//			Thread speakerThread = new Thread(new Speaker(sipHeader.getContact().getIpAddress(), sipHeader.getSipPort(), "/home/naumanbadar/Downloads/chaotic.wav"));
			Thread speakerThread = new Thread(new Speaker(sipHeader, datagramSocket, "/home/naumanbadar/Downloads/chaotic.wav"));
			speakerThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
