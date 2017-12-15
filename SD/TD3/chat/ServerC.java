package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerC {
	private ServerSocket sSocket;
	private boolean isRunning = true;
	private ArrayList<Thread> allClients;
	private ArrayList<ServCliC> clientsConnected;

	public ServerC() {
		try {
			this.allClients = new ArrayList();
			this.sSocket = new ServerSocket(12000);
			this.clientsConnected = new ArrayList();
			System.out.println("Serveur en cours d'execution");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ServCliC> getClientsConnected() {
		return this.clientsConnected;
	}

	public void disconnectClient(ServCliC client, Thread t) {
		this.clientsConnected.remove(client);
		this.allClients.remove(t);
	}

	public void open() {
		Thread exit = new Thread(new Runnable() {

			@Override
			public void run() {
				Scanner sc = new Scanner(System.in);
				String src = "";
				do {
					src = sc.nextLine();
				} while (!src.equals("exit"));
				if (isRunning == true) {
					try {
						System.out.println("Arrêt de tous les clients");
						do {
							for (int i = 0; i < clientsConnected.toArray().length; i++) {
								((Thread) allClients.toArray()[i]).interrupt();
								((ServCliC) clientsConnected.toArray()[i]).disconnectThisClient();
							}
							Thread.currentThread().sleep(3000);
						} while (clientsConnected.size() > 0);
						System.out.println("Le serveur s'arrête");
						isRunning = false;
						try {
							sSocket.close();
						} catch (IOException e) {
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});
		exit.start();
		while (isRunning == true) {
			try {
				Socket client = sSocket.accept();
				ServCliC serv = new ServCliC(client, this);
				clientsConnected.add(serv);

				Thread t1 = new Thread(serv);
				allClients.add(t1);
				t1.start();

			} catch (IOException e) {
			}
		}
	}

	public static void main(String[] args) {
		ServerC serv = new ServerC();
		serv.open();
	}

}
