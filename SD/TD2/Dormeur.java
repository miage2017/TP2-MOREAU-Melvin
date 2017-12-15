package fr.unice.miage.sd.tp2;

import java.util.*;

public class Dormeur implements Runnable {
	String nom = null;
	Thread antecedent = null;

	public Dormeur(String nom, List<Thread> list_antecedents) {
		this.nom = nom;
		for (Thread t : list_antecedents) {
			this.antecedent = t;
		}
	}

	public void run() {
		System.out.format("%s, started !\n", nom);
		if (antecedent != null)
			try {
				antecedent.join();
				System.out.format("%s: %s a fini !!\n", nom, antecedent.getName());
			} catch (InterruptedException e) {
				System.out.format("Issue with %s inte while waiting\n", nom);
			}
		System.out.format("%s dit: Super je peux enfin demarrer! !\n", nom);
		zzzz();
		System.out.format("%s dit: Super j'ai FINI!!! !\n", nom);
	}

	public static void main(String[] args) throws Exception {
		List<Thread> mon_antecedent = new ArrayList();

		Dormeur OC = new Dormeur("Dormeur C", mon_antecedent);
		Thread TC = new Thread(OC, "Mr C");

		mon_antecedent = Arrays.asList(TC);
		Dormeur OB = new Dormeur("Dormeur B", mon_antecedent);
		Thread TB = new Thread(OB, "Mr B");

		mon_antecedent = Arrays.asList(TB);
		Dormeur OA = new Dormeur("Dormeur A", mon_antecedent);
		Thread TA = new Thread(OA, "Mr A");

		TA.start();
		TB.start();
		TC.start();

		System.out.format("Main :Fini ici  !\n");
	}

	private void zzzz() {

		try {
			Thread.sleep((int) (1e3 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}