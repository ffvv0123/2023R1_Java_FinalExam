package Q1;

import java.net.*;
import java.io.*;

/**
 * ServiceThread: This class represents a service thread that handles communication with a client.
 * 
 * The service thread receives sentences from the client, processes them using the chatbot, and sends back the results.
 * It reads input from the client's socket input stream and writes output to the socket output stream.
 * The chatbot checks if the sentence is contained in its database and returns the corresponding response.
 * The communication is logged in the server's log area.
 * 
 * This class is designed to be used with the Q1_Server class.
 * 
 * @author Suhyeon Jo
 * @since 2023.06.21
 * @version v0.2
 *
 */

public class ServiceThread extends Thread {
	
	/**
	 * Private data member
	 * socket: The client's socket for communication
	 * in: BufferedReader for reading input from the client
	 * out: BufferedWriter for writing output to the client
	 * server: A reference to the Q1_Server instance for logging and accessing the chatbot
	 */
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	private Q1_Server server;
	

	/**
	 * Constructor of ServiceThread
	 * 
	 * @param socket: The client's socket for communication.
	 * @param server: The Q1_Server instance that this thread belongs to.
	 */
	public ServiceThread(Socket socket, Q1_Server server) {

		this.server = server;

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while (true) {
			try {
				String sentence = in.readLine();
				int res = server.chatbot.check(sentence);

				if(res != -1) {
					out.write(server.av.elementAt(res) + "\n");
					server.log.append(sentence + " = CONTAINED\n");
				}
				else {
					out.write("I cannot understand...!\n");
					server.log.append(sentence + " = NOT CONTAINED\n");
				}

				out.flush();
				
				
			} catch (IOException e) {
				server.log.append("Connection terminated... \n");
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			}

		}
	}

}
