/**
 * 
 */
package sipParser;

public class Contact {
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

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		return result;
	}

	/**
	 * @param ipAddress
	 * @param port
	 */
	public Contact(String ipAddress, String port) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
	}

	/**
	 * 
	 */
	public Contact() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		String objIp = ((Contact)obj).getIpAddress();
		String objPort = ((Contact)obj).getPort();
		if (ipAddress.compareTo(objIp)==0&&port.compareTo(objPort)==0) {
			return true;
		}
		return false;
	}
}