package helpers;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Nauman Badar
 * @created 6 Apr 2011
 */
public class TraceLoader {
	private final static Logger log = Logger.getLogger(TraceLoader.class.getName());

	static String loadTraceString() {

		String traceString = null;
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("sip.properties"));
			String traceFilePath = prop.getProperty("tracefilepath");
			File file;
			FileReader fr = new FileReader(file = new File(traceFilePath));
			char charbuffer[] = new char[(int) file.length()];
			fr.read(charbuffer);
			traceString = new String(charbuffer);
			traceString = traceString.trim();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return traceString;
	}

	public static void writeReceivedString(String receivedData) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("sip.properties"));
			String receivedDataFilePath = prop.getProperty("receivedDataFilePath");
		
			FileOutputStream fileOutputStream = new FileOutputStream(receivedDataFilePath);
			DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
			dataOutputStream.writeUTF(receivedData);
			dataOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
