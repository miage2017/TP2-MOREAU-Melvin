package fr.unice.miage.sd.tp3;

import java.util.Vector;

public class Producteur extends Thread {
	static final int MAXQUEUE = 5;
	private int numeroJob = 0;
	private Vector Jobs = new Vector();
	private int Identifiant;

	public Producteur(int identifiant) {
		Identifiant = identifiant;
	}

	public synchronized void prod() throws InterruptedException {
		while (Jobs.size() == MAXQUEUE) {
			wait();
		}

		Jobs.addElement("Travail numéro " + numeroJob);
		numeroJob += 1;
		System.out.println("Travail disponible: " + Jobs.toString());
		notify();
	}

	@Override
	public void run() {
		try {
			while (true) {
				prod();
			}
		} catch (InterruptedException e) {
		}
	}

	public synchronized String getJobs() throws InterruptedException {
		notify();
		while (Jobs.size() == 0) {
			wait();
		}
		String message = (String) Jobs.firstElement();
		Jobs.removeElement(message);
		return message;
	}

	public static void main(String[] args) {
		Producteur producer = new Producteur(1);
		producer.start();
		new Consommateur(producer).start();

	}

}
