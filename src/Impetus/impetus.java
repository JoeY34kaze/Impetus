package Impetus;

import java.net.*;
import java.io.*;

import java.net.*;
import java.io.*;

public class impetus extends Thread {
	private ServerSocket serverSocket;

	public impetus(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(100000000);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();

				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());

				System.out.println(in.readUTF());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
				server.close();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		if (Integer.parseInt(args[1])==0) {//server
			int port = Integer.parseInt(args[0]);
			try {
				Thread t = new impetus(port);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {//client
			String serverName = "localhost";
		      int port = Integer.parseInt(args[0]);
		      try {
		         System.out.println("Connecting to " + serverName + " on port " + port);
		         Socket client = new Socket(serverName, port);
		         
		         System.out.println("Just connected to " + client.getRemoteSocketAddress());
		         OutputStream outToServer = client.getOutputStream();
		         DataOutputStream out = new DataOutputStream(outToServer);
		         
		         out.writeUTF("Hello from " + client.getLocalSocketAddress());
		         InputStream inFromServer = client.getInputStream();
		         DataInputStream in = new DataInputStream(inFromServer);
		         
		         System.out.println("Server says " + in.readUTF());
		         client.close();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		}
	}
}
