package Q1;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Q1_Client: This program is client of Q1. This client sends the user's input
 * to the server and outputs the computed result from the server.
 * 
 * The client connects to a server using a socket and communicates by sending
 * and receiving messages. The user can type a sentence in the text field and
 * press Enter to send it to the server. The server processes the sentence and
 * sends back the computed results, which is displayed in a label.
 * 
 * The client uses a GUI created with Swing. It consists of a window with a
 * label, a text field, and a result label. The user can enter a sentence in the
 * text field and press Enter to send it to the server. The result label
 * displays the computed result received from the server.
 * 
 * The client establishes a connection with the server by creating a socket and
 * setting up input to receive messages from the server, and the output stream
 * is used to send messages to the server.
 * 
 * This program assumes that the server is running on the same
 * machine(localhost) and listening on port 9998.
 * 
 * 
 * @author Suhyeon Jo
 * @since 2023.06.21
 * @version v0.2
 *
 */

public class Q1_Client extends JFrame {
	
	/**
	 * Private data member
	 * 
	 * - sentenceTF: Text field where the user types a sentence
	 * - resLabel: Labels to represent the results
	 * - socket: Socket object used for establishing the connection with the server 
	 * - in: BufferedReader object used for reading input from the server
	 * - out: BufferedWriter object used for writing output to the server 
	 */
	private JTextField sentenceTf = new JTextField(30);
	private JLabel resLabel = new JLabel("Check result ");
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;

	/**
	 * Constructor of Q1_Client
	 * 
	 * - Create a GUI window for the client
	 * - Establish a connection to the server
	 * - Display the results received from the server
	 */
	public Q1_Client() {
		super("Client of Chatbot");
		setSize(500, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JLabel("Try chatting with a chatbot!"));
		c.add(sentenceTf);
		c.add(resLabel);

		setVisible(true);
		setupConnection();
		

		sentenceTf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					String sentence = tf.getText().trim();
					if(sentence.length() == 0)
						return;
					
					out.write(sentence + "\n");
					out.flush();
					
					String result = in.readLine();
					resLabel.setText(result);
				} catch(IOException e1) {
					System.out.println("Client: terminated connection from server");
					return;
				}
				
			}
			
		});
	}

	/**
	 * member method: setupConnection(): Sets up the connection with the server
	 * 
	 * This method creates a socket and initializes the input and output streams. It
	 * establishes a connection with the server running on the same
	 * machine(localhost) and listening on port 9998. If any exception occurs during
	 * the setup process, the stack trace is printed for debugging purposes.
	 */
	public void setupConnection() {
		try {
			socket = new Socket("localhost", 9998);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new Q1_Client();
	}

}
