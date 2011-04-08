import helpers.RegexExtractor;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Nauman Badar
 * @created 6 Apr 2011
 */
public class SipHeader {

	private String receivedData;
	private String head;
	private String via;
	private String contentLenght;
	private Contact contact;
	private String callID;
	private String contentType;
	private String cSeq;
	private String from;
	private String to;

	private String sdpData;
	private String localPort;
	private String localIP;
	private String tag;
	private String appendedVia;
	private String appendedTo;
	

	/**
 * 
 */
	public SipHeader(String localPort,String tag, String receivedData) {
		contact = new Contact();
		this.localPort = localPort;
		this.receivedData = receivedData;
		this.tag=tag;
		try {
			this.localIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load();
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

	private void load() {
		head = RegexExtractor.extract(receivedData, ".", "\r\n", true);
		via = RegexExtractor.extract(receivedData, "Via", "\r\n", true);
		contentLenght = RegexExtractor.extract(receivedData, "Content-Length: ", "\r\n", false);
		contact.ipAddress = RegexExtractor.extract(receivedData, "Contact: <sip:", ":", false);
		contact.port = RegexExtractor.extract(receivedData, "Contact: <sip:\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:", ">", false);
		callID = RegexExtractor.extract(receivedData, "Call-ID:", "\r\n", true);
		contentType = RegexExtractor.extract(receivedData, "Content-Type:", "\r\n", true);
		cSeq = RegexExtractor.extract(receivedData, "CSeq:", "\r\n", true);
		from = RegexExtractor.extract(receivedData, "From:", "\r\n", true);
		to = RegexExtractor.extract(receivedData, "To:", "\r\n", true);

		StringBuilder viaBuilder = new StringBuilder(via);
		int indexOfrport = via.indexOf("rport");
		viaBuilder.insert(indexOfrport + 5, "=" + contact.port + ";" + "received=" + contact.ipAddress);
//		viaBuilder.insert(indexOfrport + 5, "=" + localPort + ";" + "received=" + contact.ipAddress);
		appendedVia = viaBuilder.toString();
		sdpData=receivedData.substring(receivedData.indexOf("\r\n\r\n"));
		sdpData=sdpData.trim();
		
		appendedTo=to.concat(";tag="+tag);
	}

	public String produceSipInvite() {
		StringBuilder sip = new StringBuilder();
		sip.append(head + "\r\n");
		sip.append(via + "\r\n");
		sip.append("Content-Length: " + contentLenght + "\r\n");
		sip.append(contact + "\r\n");
		sip.append(callID + "\r\n");
		sip.append(contentType + "\r\n");
		sip.append(cSeq + "\r\n");
		sip.append(from + "\r\n");
		sip.append("Max-Forwards: 70" + "\r\n");
		sip.append(to + "\r\n");

		return sip.toString();
	}

	public String produceSipTrying() {
		StringBuilder sip = new StringBuilder();
		sip.append("SIP/2.0 100 Trying" + "\r\n");
		sip.append(appendedVia + "\r\n");
		sip.append("Content-Length: 0" + "\r\n");
		sip.append(callID + "\r\n");
		sip.append("CSeq: 1 INVITE" + "\r\n");
		sip.append(from + "\r\n");
		sip.append(appendedTo + "\r\n");
		sip.append("\r\n");

		return sip.toString();
	}

	public String produceSipRinging() {

		StringBuilder sip = new StringBuilder();
		sip.append("SIP/2.0 180 Ringing" + "\r\n");
		sip.append(appendedVia + "\r\n");
		sip.append("Content-Length: 0" + "\r\n");
		sip.append("Contact: <sip:" + localIP + ":" + localPort + ">" + "\r\n");
		sip.append(callID + "\r\n");
		sip.append("CSeq: 1 INVITE" + "\r\n");
		sip.append(from + "\r\n");
		sip.append(appendedTo + "\r\n");
		sip.append("\r\n");

		return sip.toString();
	}
	public String produceSipOK() {
		
		StringBuilder sip = new StringBuilder();
		sip.append("SIP/2.0 200 OK" + "\r\n");
		sip.append(appendedVia + "\r\n");
		sip.append("Content-Length: "+sdpData.length() + "\r\n");
		sip.append("Contact: <sip:" + localIP + ":" + localPort + ">" + "\r\n");
		sip.append(callID + "\r\n");
		sip.append("Content-Type: application/sdp"+ "\r\n");
		sip.append("CSeq: 1 INVITE" + "\r\n");
		sip.append(from + "\r\n");
		sip.append(appendedTo + "\r\n");
		sip.append("\r\n");
		sip.append(sdpData);
		
		return sip.toString();
	}

	/**
	 * @return the localPort
	 */
	public String getLocalPort() {
		return localPort;
	}

	/**
	 * @param localPort
	 *            the localPort to set
	 */
	public void setLocalPort(String localPort) {
		this.localPort = localPort;
	}

	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}

	public String getLocalIP() {
		return localIP;
	}

	public void setAppendedVia(String appendedVia) {
		this.appendedVia = appendedVia;
	}

	public String getAppendedVia() {
		return appendedVia;
	}

	public void setSdpData(String sdpData) {
		this.sdpData = sdpData;
	}

	public String getSdpData() {
		return sdpData;
	}
}

class Contact {
	String ipAddress;
	String port;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Contact:<sip:" + ipAddress + ":" + port + ">";
	}
}
