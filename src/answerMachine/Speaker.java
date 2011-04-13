/**
 * 
 */
package answerMachine;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;

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

import sipParser.SipHeader;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 12, 2011
 * 
 */
public class Speaker implements Runnable {
	private final static Logger log = Logger.getLogger(Speaker.class.getName());
	private String ipAddress;
	private String port;
	private String pathToAudio;
	private SipHeader sipHeader;
	private DatagramSocket datagramSocket;

	/**
	 * @param sipHeader
	 * @param datagramSocket
	 * @param pathToAudio
	 */
	public Speaker(SipHeader sipHeader, DatagramSocket datagramSocket, String pathToAudio) {
		super();
		this.sipHeader = sipHeader;
		this.datagramSocket = datagramSocket;
		this.pathToAudio = pathToAudio;
		this.ipAddress = sipHeader.getContact().getIpAddress();
		this.port = sipHeader.getSipPort();
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the pathToAudio
	 */
	public String getPathToAudio() {
		return pathToAudio;
	}

	/**
	 * @param pathToAudio
	 *            the pathToAudio to set
	 */
	public void setPathToAudio(String pathToAudio) {
		this.pathToAudio = pathToAudio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		answer();
	}

	private void answer() {
		try {
			
//			Thread.sleep(8000);
			
			byte[] byteBuffer = sipHeader.produceSipOK().getBytes();
			DatagramPacket datagramPacket = new DatagramPacket(byteBuffer, byteBuffer.length);
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);
			
			
			
			
			
			final Format[] formats = new Format[] { new AudioFormat(AudioFormat.GSM_RTP) };
			final ContentDescriptor contentDescriptor = new ContentDescriptor(ContentDescriptor.RAW_RTP);
			File mediaFile = new File(pathToAudio);
			// File mediaFile = new File("properties/surahBayyinah.mp3");
			DataSource dataSource = Manager.createDataSource(new MediaLocator(mediaFile.toURL()));

			Processor processor = Manager.createRealizedProcessor(new ProcessorModel(dataSource, formats, contentDescriptor));

			DataSink dataSink = Manager.createDataSink(processor.getDataOutput(), new MediaLocator("rtp://"+ipAddress+":"+port+"/audio"));

			processor.start();
			dataSink.open();
			dataSink.start();

			// System.out.println(dataSource.getDuration().getNanoseconds());
			// System.out.println((long) dataSource.getDuration().getSeconds());
			// System.out.println((long) processor.getDuration().getSeconds());

			Thread.sleep((long) (processor.getDuration().getSeconds() * 1000));

			dataSink.stop();
			dataSink.close();
			processor.stop();
			processor.close();
			
			byteBuffer = sipHeader.produceBye(sipHeader.getContact().getIpAddress(), sipHeader.getContact().getPort()).getBytes();
			datagramPacket = new DatagramPacket(byteBuffer, byteBuffer.length);
			datagramPacket.setAddress(InetAddress.getByName(sipHeader.getContact().getIpAddress()));
			datagramPacket.setPort(Integer.parseInt(sipHeader.getContact().getPort()));
			datagramPacket.setData(byteBuffer);
			datagramSocket.send(datagramPacket);
			log.info("BYE SENT");

		} catch (NoDataSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoProcessorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoDataSinkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotRealizedError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}