import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
			prop.load(new FileInputStream("properties/sip.properties"));
			String traceFilePath = prop.getProperty("tracefilepath");
			File file;
			FileReader fr = new FileReader(file = new File(traceFilePath));
			char charbuffer[] = new char[(int) file.length()];
			fr.read(charbuffer);
			traceString = new String(charbuffer);
			traceString = traceString.trim();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return traceString;
	}

	static void writeReceivedString(String receivedData) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("properties/sip.properties"));
			String receivedDataFilePath = prop.getProperty("receivedDataFilePath");
			File file = new File(receivedDataFilePath);
			PrintWriter pw = new PrintWriter(file, "UTF-8");
			pw.write(receivedData);
			pw.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
