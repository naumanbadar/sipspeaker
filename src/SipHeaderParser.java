import org.apache.log4j.Logger;

/**
 * @author Nauman Badar
 * @created 6 Apr 2011
 */
public class SipHeaderParser {

	private final static Logger log = Logger.getLogger(SipHeaderParser.class.getName());

	public static void main(String[] args) {
		log.info(TraceLoader.loadTraceString());
		System.out.print(TraceLoader.loadTraceString());
	}

}
