package complete_java_course_from_udemy.project.ChatApplication;
import java.io.*;
import java.net.*;

// Once this thread starts executing means a new client has connected to the server.

public class ClientHandler implements Runnable {
	
	Socket clientSocket;
	BufferedReader bReader;
	PrintWriter pWriter;
	Server server;
	
	public ClientHandler(Socket clientSocket, Server server) {
		this.clientSocket = clientSocket;
		this.server = server;
		try {
			this.bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.pWriter = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		String name = "";
		
//		Sending the message to the user to enter his/her name:
		while(true) {
			pWriter.println("SUBMITNAME");
			try {
				name = bReader.readLine();
				if(name == null) return;
				if(!name.isEmpty()) break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		Name is accepted:
		pWriter.println("NAMEACCEPTED");
		server.broadcast(name + " has connected!");
		
//		-----------------------------------------------------
		while(true) {
			try {
				String userMessage = bReader.readLine();
				if(userMessage == null) return;
				server.broadcast("MESSAGE" + name + " : " + userMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
