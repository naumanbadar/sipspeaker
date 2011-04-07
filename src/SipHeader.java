import helpers.RegexExtractor;

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
/**
 * 
 */
public SipHeader() {
contact = new Contact();
}
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
		head = RegexExtractor.extract(receivedString, ".", "\r\n",true);
		via = RegexExtractor.extract(receivedString, "Via", "\r\n",true);
		contentLenght = RegexExtractor.extract(receivedString, "Content-Length: ", "\r\n",false);
		contact.ipAddress=RegexExtractor.extract(receivedString, "Contact: <sip:", ":", false);
		contact.port=RegexExtractor.extract(receivedString, "Contact: <sip:\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:", ">", false);
		callID = RegexExtractor.extract(receivedString, "Call-ID:", "\r\n",true);
		contentType = RegexExtractor.extract(receivedString, "Content-Type:", "\r\n",true);
		cSeq = RegexExtractor.extract(receivedString, "CSeq:", "\r\n",true);
		from = RegexExtractor.extract(receivedString, "From:", "\r\n",true);
		to = RegexExtractor.extract(receivedString, "To:", "\r\n",true);
		
	}
	
	public String produceSipInvite(){
		StringBuilder sip = new StringBuilder();
		sip.append(head+"\r\n");
		sip.append(via+"\r\n");
		sip.append("Content-Length: "+contentLenght+"\r\n");
		sip.append(contact+"\r\n");
		sip.append(callID+"\r\n");
		sip.append(contentType+"\r\n");
		sip.append(cSeq+"\r\n");
		sip.append(from+"\r\n");
		sip.append("Max-Forwards: 70"+"\r\n");
		sip.append(to+"\r\n");
		
		
		return sip.toString();
	}

	public String produceSipTrying(){
		StringBuilder sip = new StringBuilder();
		return sip.toString();
	}
}

class Contact {
	String ipAddress;
	String port;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Contact:<sip:"+ipAddress+":"+port+">";
	}
}

