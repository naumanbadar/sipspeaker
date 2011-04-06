import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nauman Badar
 * @created 6 Apr 2011
 */
public class SipHeader {

	private String head;
	private String via;
	private String contentLenght;
	private Contact contact;
	private String callID;
	private String contentType;
	private String cSeq;
	private String from;
	private String to;

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getContentLenght() {
		return contentLenght;
	}

	public void setContentLenght(String contentLenght) {
		this.contentLenght = contentLenght;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getCallID() {
		return callID;
	}

	public void setCallID(String callID) {
		this.callID = callID;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getcSeq() {
		return cSeq;
	}

	public void setcSeq(String cSeq) {
		this.cSeq = cSeq;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	
	public void load(String receivedString) {
		Pattern pattern = Pattern.compile("Via");
		Matcher matcher = pattern.matcher(receivedString);
		
	}

}

class Contact {
	String ipAddress;
	String port;
}

