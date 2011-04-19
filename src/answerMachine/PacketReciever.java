/**
 * 
 */
package answerMachine;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.util.regex.Pattern;

import javax.media.CannotRealizeException;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.NotRealizedError;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;

import org.apache.log4j.Logger;

import configuration.Configuration;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 8, 2011
 * 
 */
public class PacketReciever {
	private final static Logger log = Logger.getLogger(PacketReciever.class.getName());

	public static void receive(DatagramSocket datagramSocket) {

		try {
			byte byteBuffer[] = new byte[2000];
			DatagramPacket datagramPacket = new DatagramPacket(byteBuffer, byteBuffer.length);
			String receivedData = new String();
			datagramSocket.receive(datagramPacket);
			receivedData = new String(datagramPacket.getData(), "UTF-8");
			receivedData = receivedData.trim();

			// if (receivedData.indexOf("INVITE sip:nauman")==0) {
			if (receivedData.indexOf("INVITE sip:"+Configuration.INSTANCE.getSipUser()+"@") == 0) {
				log.info("Call accepted from " + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort());
				CallHandler.handleInvite(receivedData, datagramPacket, datagramSocket);
			}else if (receivedData.indexOf("INVITE sip")==0) {
				log.info("Call rejected for unknown calee from " + datagramPacket.getAddress().getHostAddress() + ":" + datagramPacket.getPort());
				CallHandler.handleWrongInvite(receivedData, datagramPacket, datagramSocket);
				
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
