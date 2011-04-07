import helpers.RegexExtractor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author Nauman Badar
 * @created 6 Apr 2011
 */
public class Starter {

	private final static Logger log = Logger.getLogger(Starter.class.getName());

	public static void main(String[] args) {

//		String sipHeaders = TraceLoader.loadTraceString();

		SipHeader sipHeader = new SipHeader();
//		sipHeader.load(sipHeaders);
		// log.info(sipHeader.produceSIP());
		try {
			DatagramSocket datagramSocket = new DatagramSocket(5060);
			byte byteBuffer[] = new byte[2000];
			DatagramPacket datagramPacket = new DatagramPacket(byteBuffer, byteBuffer.length);
			String receivedData = new String();
			datagramSocket.receive(datagramPacket);
			receivedData = new String(datagramPacket.getData());
			
			sipHeader.load(receivedData);
			 System.out.println(sipHeader.produceSipInvite());
			 TraceLoader.writeReceivedString(receivedData);
			
//			log.info(receivedData);

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
