/**
 * 
 */
package answerMachine;

import java.io.File;
import java.io.IOException;
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
/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 8, 2011
 * 
 */
public class PacketReciever {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// try {
		//
		// DatagramSocket datagramSocket = new DatagramSocket(5061);
		// CallHandler callHandler = new CallHandler();
		//
		// while (true) {
		// byte byteBuffer[] = new byte[2000];
		// DatagramPacket datagramPacket = new DatagramPacket(byteBuffer,
		// byteBuffer.length);
		// datagramSocket.receive(datagramPacket);
		// callHandler.handle(datagramPacket);
		//
		//
		// }
		//
		//
		//
		// } catch (SocketException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			final Format[] formats = new Format[] { new AudioFormat(AudioFormat.GSM_RTP) };
			final ContentDescriptor contentDescriptor = new ContentDescriptor(ContentDescriptor.RAW_RTP);
			File mediaFile = new File("/home/naumanbadar/Downloads/chaotic.wav");
//			File mediaFile = new File("properties/surahBayyinah.mp3");
			DataSource dataSource = Manager.createDataSource(new MediaLocator(mediaFile.toURL()));

		
		    Processor processor = Manager.createRealizedProcessor(new ProcessorModel(dataSource,formats, contentDescriptor));
			 
			DataSink dataSink = Manager.createDataSink(processor.getDataOutput(), new MediaLocator("rtp://127.0.0.1:49152/audio")); 
		
			processor.start();
			dataSink.open();
			dataSink.start();
			
//			System.out.println(dataSource.getDuration().getNanoseconds());
//			System.out.println((long) dataSource.getDuration().getSeconds());
//			System.out.println((long) processor.getDuration().getSeconds());
			
			Thread.sleep((long) (processor.getDuration().getSeconds()*1000));
			
			
			dataSink.stop();
			dataSink.close();
			processor.stop();
			processor.close();
			
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
