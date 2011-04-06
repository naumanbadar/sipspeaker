import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SipHeaderParser {

	public static void main(String[] args) {

		System.out.println(loadTraceString());
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("/home/v10/naumanb/workspace/SIP Listener/sip.properties"));
			System.out.println(prop.getProperty("tracefilepath"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String loadTraceString() {
		String traceString = null;
		try {
			File file;
			FileReader fr = new FileReader(file = new File("/home/v10/naumanb/trace"));
			char charbuffer[] = new char[(int) file.length()];
			fr.read(charbuffer);
			traceString = new String(charbuffer);
			traceString=traceString.trim();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return traceString;
	}
}
