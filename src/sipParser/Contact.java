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
}