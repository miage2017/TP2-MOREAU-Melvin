package fr.unice.miage.sd.tp3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Repartiteur {

	private ServerSocket servSocket;
	private int lengthCli;
	private boolean isRunning = true;
	private Thread[] allCli;
	
	private static int maxCliAccept = 5;

	public Repartiteur() {
		try {
			this.allCli = new Thread[Repartiteur.maxCliAccept];
			this.servSocket = new ServerSocket(12000);
			this.lengthCli = 0;
			System.out.println("Serveur en cours d'execution...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {
		while (isRunning == true && this.lengthCli < Repartiteur.maxCliAccept) {

			try {
				System.out.println("Attente d'un client");
				Socket client = servSocket.accept();

				System.out.println("Client connecté.");
				Thread t1 = new Thread(new ServiceClient(client));
				this.allCli[this.lengthCli] = t1;
				this.lengthCli++;
				System.out.println("Nombre de client connecté : " + this.lengthCli);
				t1.start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (isRunning == true) {
			try {
				Thread.sleep(10000);
				System.out.println("Arret de tous les clients");
				for (int i = 0; i < this.allCli.length; i++) {
					allCli[i].interrupt();
				}
				System.out.println("Arret du serveur");
				isRunning = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Repartiteur serv = new Repartiteur();
		serv.open();
	}

}
