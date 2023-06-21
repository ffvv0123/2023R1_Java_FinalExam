package Q1;

import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * Q1_Server: This program is server of Q1.
 * 
 * The server handles communication with the client and processes the user's
 * input. It uses a chatbot to generate responses based on the input.
 * 
 * The server displays a graphical user interface (GUI) window with a log area
 * to show the conversation between the chatbot and the client. The server reads
 * the chatbot's database file, initializes the chatbot, and starts a server
 * thread to listen for client connections.
 * 
 * This program assumes that the chatbot's database file "Chatbot.txt" is
 * present in the same directory.
 * 
 * 
 * @author Suhyeon Jo
 * @since 2023.06.21
 * @version v0.2
 *
 */

public class Q1_Server extends JFrame {

	
	/**
	 * default data member of Q1_Server
	 * 
	 * - chatbot: The instance of the Chatbot class used for generating responses.
	 * - log: The JTextArea component used for displaying the conversation log.
	 * - qv: A vector to store the questions.
	 * - av: A vector to store the corresponding answers.
	 */
	Chatbot chatbot = null;
	JTextArea log = new JTextArea();
	Vector<String> qv = new Vector<String>();
	Vector<String> av = new Vector<String>();

	/**
	 * Constructor of Q1_Server
	 * - Creates a GUI window for the server
	 * - Loads the chatbot's database file
	 * - Starts a server thread
	 */
	public Q1_Server() {
		super("Chatbot");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.add(new JLabel("This is chatbot!"));
		c.add(new JScrollPane(log), BorderLayout.CENTER);
		setVisible(true);

		chatbot = new Chatbot("Chatbot.txt", this);

		if (chatbot.isFileRead()) {
			log.setText("Database file [Chatbot.txt] is succesfully loaded! \n");
			new ServerThread(this).start();
		}
	}

	public static void main(String[] args) {

		new Q1_Server();
	}

}
