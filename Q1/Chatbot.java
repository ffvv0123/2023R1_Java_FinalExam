package Q1;

import java.io.*;
import java.util.*;

/**
 * Chatbot: This class represents a simple chatbot.
 * 
 * The chatbot reads a database file containing question-answer pairs.
 * It stores the questions and answers in separate vectors.
 * The chatbot can check if a given sentence is a question in its database and return the corresponding answer.
 * 
 * This class is designed to be used with the Q1_Server class. 
 * 
 * @author Suhyeon Jo
 * @since 2023.06.21
 * @version v0.2
 *
 */

public class Chatbot {

	/**
	 * Private data member
	 * - fileOn: Indicates whether the file was successfully read
	 * - server: A reference to the Q1_Server instance 
	 */
	private boolean fileOn = false;
	private Q1_Server server;

	/**
	 * Constructor of Chatbot
	 * 
	 * @param fileName: The name of the file containing the question-answer pairs.
	 * @param server: The Q1_Server instance that this chatbot belongs to.
	 */
	public Chatbot(String fileName, Q1_Server server) {
		
		this.server = server;
		
		try {
			Scanner reader = new Scanner(new FileReader(fileName));
			while (reader.hasNext()) {
				String sentence = reader.nextLine() + "\t";

				String question, answer = "";
				StringTokenizer st = new StringTokenizer(sentence, "\t");
				
				question = st.nextToken();
				answer = st.nextToken();

				server.qv.add(question);
				server.av.add(answer);
			}
			reader.close();
			fileOn = true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fileOn = false;
		}
	}

	/**
	 * member method isFileRead(): Checks if the file was successfully read.
	 * 
	 * @return true if the file was read successfully, false otherwise.
	 */
	public boolean isFileRead() {
		return fileOn;
	}

	/**
	 * member method check(): Checks if a sentence is a question in the chatbot's database.
	 * 
	 * @param sentence The sentence to check.
	 * @return The index of the matching question in the database, or -1 if no match is found.
	 */
	public int check(String sentence) {
		for(int i=0; i < server.qv.size(); i++) {
			if (server.qv.elementAt(i).equals(sentence)) {
				return i;
			}
		}
		return -1;
	}
}
