package complete_java_course_from_udemy.project.ChatApplication;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	/*Whenever a client connects to the server, 
	 * a socket will be created for this connection, 
	 * so we have to store the socket related to all the users	
	 */
	
	ArrayList<Socket> sockets = new ArrayList<>();
	
	
	// Broadcast method: That broadcasts the message to all the users, and it will be called by the client handler not the server.
	public void broadcast(String message) {
		for(Socket socket : sockets) {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				writer.println(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	// Main method for the Server class:
	public void run() {
		// Creating server socket:
		try {
			ServerSocket serverSocket = new ServerSocket(4444);
			System.out.println("Server started on PORT: 4444");
			
			// loop which will keep listening from the clients trying to connect to the server:
			while(true) {
				Socket clientSocket = serverSocket.accept(); // this means the client has connected to the server.
				sockets.add(clientSocket);
				System.out.println("Client Connected!");
				
				// A client handler thread:
				ClientHandler clientHandler = new ClientHandler(clientSocket, this);
				new Thread(clientHandler).start();
			}		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server().run();
	}

}
