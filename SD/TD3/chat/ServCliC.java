package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;

public class ServCliC implements Runnable {

	private Socket client;
	private ServerC serveur;
	private String name;

	private PrintStream out;
	private InputStreamReader inStream;
	private BufferedReader flux_entrant;

	public ServCliC(Socket client, ServerC serv) {
		this.client = client;
		this.serveur = serv;
		try {
			this.inStream = new InputStreamReader(client.getInputStream(), "UTF-8");
			this.out = new PrintStream(client.getOutputStream());
			this.flux_entrant = new BufferedReader(inStream);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getName() {
		return this.name;
	}

	public void getNameClient() {
		this.name = read();
	}

	public String read() {
		String read = null;
		try {
			read = this.flux_entrant.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}

	public void ClientClose() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void SendMessageToOtherClient(String message) {
		String messageFinal = name + " : " + message;
		ArrayList<ServCliC> allClients = this.serveur.getClientsConnected();
		for (int i = 0; i < allClients.size(); i++) {
			if (!((ServCliC) allClients.toArray()[i]).getName().equals(this.name)) {
				((ServCliC) allClients.toArray()[i]).sendMessageToClient(messageFinal);
			}
		}
		System.out.println(messageFinal);
	}

	public void disconnectThisClient() {
		out.println("Arret du serveur");
		out.println("EXIT");
		this.serveur.disconnectClient(this, Thread.currentThread());
	}

	public void sendMessageToClient(String message) {
		out.println(message);
	}

	@Override
	public void run() {
		getNameClient();
		SendMessageToOtherClient("Je viens de me connecter");
		out.println("Bonjour " + this.name + "!");
		String read = read();
		Thread.currentThread();
		flux_entrant = new BufferedReader(this.inStream);
		while (read != null && !Thread.interrupted()) {
			this.SendMessageToOtherClient(read);
			if (read.equals("FINISH")) {
				out.println("FINISH");
				break;
			}
			if (read.equals("exit")) {
				break;
			}
			read = read();

		}
		if (Thread.interrupted()) {
			out.println("FINISH");
		} else {
			this.serveur.disconnectClient(this, Thread.currentThread());
		}
		SendMessageToOtherClient("Je me suis deconnecter");
		ClientClose();
	}

}
