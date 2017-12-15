package fr.unice.miage.sd.tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

	private Socket socket;
	private String name;
	private boolean send = false;
	private BufferedReader in;
	private PrintStream out;
	private int nbMsg = 5;
	private Scanner scan;

	public Client(String name) {
		this.name = name;
		this.socket = connexionServer();
		scan = new Scanner(System.in);
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Client(String name, boolean send) {
		this.name = name;
		this.send = true;
		this.socket = connexionServer();
		scan = new Scanner(System.in);
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Socket connexionServer() {
		Socket retour = null;
		try {
			InetAddress serveur = InetAddress.getByName("127.0.0.1");
			retour = new Socket(serveur, 12000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retour;
	}

	private String SendName() {
		out.println(name);
		String retour = null;
		try {
			retour = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retour;
	}

	private String getClientString() {
		System.out.println("Veuillez saisir une phrase :");
		return scan.nextLine();
	}

	public void sendClientString() {
		do {
			try {
				int a = 5 * (int) (Math.random() * 1000);
				Thread.sleep(a);
				out.println("J'envoie un message");
				this.nbMsg--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (this.nbMsg > 0 && !Thread.interrupted());
		if (!Thread.interrupted()) {
			out.println("FINISH");
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		System.out.println(SendName());
		if (!this.send) {
			String str = "";
			do {
				str = getClientString();
				out.println(str);
			} while (!(str.equals("FINISH") || str.equals("") || str.equals(null)) && !!Thread.interrupted());
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sendClientString();
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Il me faut 1 argument: identifiant");
			System.exit(1);
		}

		Thread t = new Thread(new Client(args[0]));
		t.start();
	}

}