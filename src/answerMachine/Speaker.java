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
 * @created Apr 12, 2011
 * 
 */
public class Speaker implements Runnable {
	private String ipAddress;
	private String port;
	private String pathToAudio;

	/**
	 * @param ipAddress
	 * @param port
	 * @param pathToAudio
	 */
	public Speaker(String ipAddress, String port, String pathToAudio) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
		this.pathToAudio = pathToAudio;
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
