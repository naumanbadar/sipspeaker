/**
 * 
 */
package WebServer;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 20, 2011
 *
 */
public class WebServerStarter implements Runnable {
private int httpPort;

/**
 * 
 */
public WebServerStarter(String httpPort) {
this.httpPort=Integer.parseInt(httpPort.trim());
}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		Listener listener = new Listener(httpPort);
		while (true) {
			listener.start();
		}

	}

}
