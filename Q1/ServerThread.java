package Q1;

import java.io.IOException;
import java.net.*;

/**
 * ServerThread: This class represents a server thread that listens for client connections.
 * 
 * The server thread accepts client connections and starts a service thread for each client.
 * It uses a ServerSocket to listen on a specified port (9998) and accepts incoming connections.
 * For each connection, it creates a new ServiceThread to handle the client communication.
 * 
 * This class is designed to be used with the Q1_Server class.
 * 
 * @author Suhyeon Jo
 * @since 2023.06.21
 * @version v0.2
 *
 */

public class ServerThread extends Thread {

	/**
	 * Private data member
	 * server: A reference to the Q1_Server instance to access its data members.
	 */
	private Q1_Server server;

	/**
	 * Constructor of ServerThread
	 * @param server: The Q1_Server instance that this thread belongs to.
	 */
	public ServerThread(Q1_Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		ServerSocket listener = null;
		Socket socket = null;

		try {
			listener = new ServerSocket(9998);
			while (true) {
				socket = listener.accept();
				server.log.append("Connected client \n");
				new ServiceThread(socket, server).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (listener != null)
				listener.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
