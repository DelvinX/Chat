package pis.hue2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

	ServerSocket server;
	ArrayList<PrintWriter> list_clientwriter;

	final int LEVEL_ERR = 1;
	final int LEVEL_Norm = 0;

	public static void main(String[] args) {

		Server s = new Server();
		if (s.runServer()) {
			s.listenToClients();
		} else {

		}
	}

	// From Runnable implentiert
	public class ClientHandler implements Runnable {

		Socket client;
		// What wl b recvd is by Buffered reader
		BufferedReader reader;

		// Constructor
		public ClientHandler(Socket client) {
			try {
				this.client = client;
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String msg;

			// til a client is online n send a msg
			try {
				while ((msg = reader.readLine()) != null) {
					appendTextToConsole("frm Client: \n" + msg, LEVEL_Norm);
					sendToAllClients(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		// This Method runs the whole time
		private void listenToClients() {

			while (true) {
				try {
					Socket client = server.accept();

					// Printwriter ot prntstream
					PrintWriter writer = new PrintWriter(client.getOutputStream());
					// It holds ol the printwriter from ol Clients.
					list_clientwriter.add(writer);

					// 4 Each @ client a new thread coz they run differently at
					// same
					// time
					Thread clientThread = new Thread(new ClientHandler(client));
					clientThread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		public boolean runServer() {
			try {
				server = new ServerSocket(5555);
				appendTextToConsole("Server starts", LEVEL_ERR);

				list_clientwriter = new ArrayList<PrintWriter>();
				return true;

			} catch (Exception e) {
				appendTextToConsole(" Server didnt start", LEVEL_ERR);
				e.printStackTrace();
				return false;
			}
		}

		public void sendToAllClients(String msg) {
			// Iterator is like a pointer showing to the lists
			Iterator itr = list_clientwriter.iterator();

			while (itr.hasNext()) {
				// Printwtr in arraylist, We pick up the next element in
				// >itr.next
				PrintWriter writer = (PrintWriter) itr.next();
				writer.println(msg);
				writer.flush();
			}

		}



	private void appendTextToConsole(String msg, int level) {
		if (level == LEVEL_ERR) {
			System.err.println(msg + "\n");

		} else {
			System.out.println(msg + "\n");
		}

	}
}
// Strg+ shift + o //Automatic export;