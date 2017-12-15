package fr.unice.miage.sd.tp2;

import java.util.*;

public class DormeurAvancee implements Runnable {
	String nom = null;
	Queue<Thread> antecedents = new LinkedList<Thread>();

	public DormeurAvancee(String nom) {
		this.nom = nom;

	}

	public void SetAntecedent(Queue<Thread> antecedents) {
		this.antecedents = new LinkedList<Thread>(antecedents);
	}

	public void run() {
		System.out.format("%s, started !\n", nom);
		for (Thread t : antecedents) {
			try {
				t.join();
				System.out.format("%s: %s a fini !!\n", nom, t.getName());
			} catch (InterruptedException e) {
				System.out.format("Issue with %s inte while waiting\n", nom);
			}
		}
		System.out.format("%s dit: Super je peux enfin demarrer! !\n", nom);
		zzzz();
		System.out.format("%s dit: Super j'ai FINI!!! !\n", nom);
	}

	public static void main(String[] args) throws Exception {
		DormeurAvancee OC = new DormeurAvancee("Glandeur C");
		Thread TC = new Thread(OC, "Mister C");

		DormeurAvancee OB = new DormeurAvancee("Glandeur B");
		Thread TB = new Thread(OB, "Mister B");

		DormeurAvancee OA = new DormeurAvancee("Glandeur A");
		Thread TA = new Thread(OA, "Mister A");
		Queue<Thread> file = new LinkedList();

		file.add(TB);
		file.add(TC);
		OA.SetAntecedent(file);
		file = new LinkedList();
		file.add(TC);

		OB.SetAntecedent(file);

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
