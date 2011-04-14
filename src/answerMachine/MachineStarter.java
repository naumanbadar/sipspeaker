/**
 * 
 */
package answerMachine;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 12, 2011
 * 
 */
public class MachineStarter {
	public static void main(String[] args) {
		try {
			DatagramSocket datagramSocket;
			datagramSocket = new DatagramSocket(5061);
			while (true) {

				PacketReciever.receive(datagramSocket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
