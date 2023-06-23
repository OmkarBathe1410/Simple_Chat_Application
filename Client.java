package complete_java_course_from_udemy.project.ChatApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {
	JFrame jFrame = new JFrame("Chat Application");
	
	// Text field used by the user to write and send a message.
	JTextField sendMessageTextField = new JTextField(40);
	
	// Text area to show the messages to the user.
	JTextArea messageTextArea = new JTextArea(8, 40);
	
	// For reading and writing into the socket:
	BufferedReader bufferedReader;
	PrintWriter pWriter;
	
	// Constructor
	public Client() {
		// Making text field and text area, un-editable.
		// Text field will be un-editable until the user inputs something.
		sendMessageTextField.setEditable(false);
		messageTextArea.setEditable(false);
		
		jFrame.getContentPane().add(sendMessageTextField, "North"); // BorderLayout.NORTH
		jFrame.getContentPane().add(messageTextArea, "Center"); // BorderLayout.CENTER
		jFrame.pack(); // Resizes the window, so that it remains of the size sufficient for the content it has.
		
		
		sendMessageTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pWriter.println(sendMessageTextField.getText());
				sendMessageTextField.setText("");
			}
		});
	}
	
	// Connect Method: Will connect the client to the server.
	public void connect() {
		// Fetching the server address from the user.
		String serverAddress = JOptionPane.showInputDialog(jFrame, "Enter the server IP", "Connect to the server", JOptionPane.QUESTION_MESSAGE);
		
		// Creating new socket for that IP:
		try {
			Socket socket = new Socket(serverAddress, 4444);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pWriter = new PrintWriter(socket.getOutputStream(), true);
			
			// loop which keeps listening the message sent on the server:
			while(true) {
				String line = bufferedReader.readLine();
				
				// For submitting the name to the server:
				if(line.startsWith("SUBMITNAME")) {
					String name = JOptionPane.showInputDialog(jFrame, "Enter your name: ", "Name Selection", JOptionPane.PLAIN_MESSAGE);
					pWriter.println(name);
				}else if(line.startsWith("NAMEACCEPTED")) {
					sendMessageTextField.setEditable(true);
				}else if(line.startsWith("MESSAGE")) {
					messageTextArea.append(line.substring(7) + "\n");
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Client client = new Client();
		client.jFrame.setVisible(true);
		client.connect();
	}

}
