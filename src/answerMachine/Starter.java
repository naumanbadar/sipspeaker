package answerMachine;

import helpers.TraceLoader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.log4j.Logger;

import sipParser.SipHeader;

/**
 * @author Nauman Badar
 * @created 6 Apr 2011
 */
public class Starter {

	private final static Logger log = Logger.getLogger(Starter.class.getName());

	public static void main(String[] args) {

		// String sipHeaders = TraceLoader.loadTraceString();

		// sipHeader.load(sipHeaders);
		// log.info(sipHeader.produceSIP());
		try {
			DatagramSocket datagramSocket = new DatagramSocket(5061);
			byte byteBuffer[] = new byte[2000];
			DatagramPacket datagramPacket = new DatagramPacket(byteBuffer, byteBuffer.length);
			String receivedData = new String();
			datagramSocket.receive(datagramPacket);
			receivedData = new String(datagramPacket.getData());
			log.info("*********************************************Received Data\n" + receivedData.trim());
			SipHeader sipHeader = new SipHeader("5061", "IBN BAD'R", "IK2213_SIP_SPEAKER", receivedData);

			TraceLoader.writeReceivedString(receivedData);
			log.info("*********************************************Sent OK\n" + sipHeader.produceSipOK());
			
			
			byteBuffer = sipHeader.produceSipTrying().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

			byteBuffer = sipHeader.produceSipRinging().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

			byteBuffer = sipHeader.produceSipOK().getBytes();
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);

			datagramSocket.close();

			// log.info(receivedData);

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
