package fr.unice.miage.sd.tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ServiceClient implements Runnable {

	private Socket client;
	private InputStreamReader inputStreamR;
	private PrintStream out;
	private BufferedReader flux_in;

	public ServiceClient(Socket client) {
		this.client = client;
		try {
			this.inputStreamR = new InputStreamReader(client.getInputStream(), "UTF-8");
			this.out = new PrintStream(client.getOutputStream());
			this.flux_in = new BufferedReader(inputStreamR);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getNameClient() {
		String read = read();
		return read;
	}

	public void ClientClose() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String read() {
		String read = null;
		try {
			read = this.flux_in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}

	@Override
	public void run() {
		String nameCli = getNameClient();
		out.println("Bonjour " + nameCli + "!");
		String read = read();
		while (read != null && !Thread.interrupted()) {
			System.out.println(nameCli + " écrit : " + read);
			flux_in = new BufferedReader(this.inputStreamR);
			if (read.equals("FINISH")) {
				System.out.println("Deconnexion du client");
				ClientClose();
				break;
			}
			read = read();
		}

		
	}

}
