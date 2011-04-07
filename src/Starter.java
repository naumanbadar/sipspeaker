import helpers.RegexExtractor;

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

		String sipHeaders = TraceLoader.loadTraceString();

		SipHeader sipHeader = new SipHeader();
		sipHeader.load(sipHeaders);
//		log.info(sipHeader.produceSIP());
		System.out.println(sipHeader.produceSipInvite());
	}
}
