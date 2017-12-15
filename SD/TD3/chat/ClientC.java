package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientC {
	private Socket socket;
	private String name;
	private BufferedReader in;
	private PrintStream out;
	private Scanner scan;
	private boolean isConnected = true;

	public ClientC(String name) {
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
		return scan.nextLine();
	}

	private String getMessageServer() {
		String read = "";
		try {
			read = this.in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}

	public void run() {
		System.out.println(SendName());

		Thread read = new Thread(new Runnable() {

			@Override
			public void run() {
				String read1 = "";
				do {
					read1 = getMessageServer();
					if (!read1.equals("FINISH") && !read1.equals("EXIT")) {
						System.out.println(read1);
					} else {
						System.out.println("vous avez été déconnecté du serveur");
					}
				} while (!read1.equals(" ") && !read1.equals("FINISH") && !read1.equals(null) && !read1.equals("EXIT"));
				if (read1.equals("EXIT")) {
					out.println("exit");
				}
				isConnected = false;
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		read.start();

		Thread write = new Thread(new Runnable() {

			@Override
			public void run() {
				String str = "";
				System.out.println("Veuillez saisir une phrase :");
				do {
					str = getClientString();
					out.println(str);
				} while (!(str.equals("FINISH") || str.equals("") || str.equals(null)) && isConnected);
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		write.start();
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Il me faut 1 argument: identifiant");
			System.exit(1);
		}
		ClientC t = new ClientC(args[0]);
		t.run();
	}

}
