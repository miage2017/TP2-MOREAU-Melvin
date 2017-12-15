package fr.unice.miage.sd.td4;

import java.util.ArrayList;

public class LancerExo1 {
	public static int nbPhilosophe = 10;

	public static void main(String[] args) {
		ArrayList<Philosophe> philosophes = new ArrayList<Philosophe>();
		ArrayList<Baguette> baguettes = new ArrayList<Baguette>();
		ArrayList<Thread> threads = new ArrayList<Thread>();
		int j = 0;

		for (int i = 0; i < LancerExo1.nbPhilosophe; i++) {
			baguettes.add(new Baguette());
		}
		for (int i = 0; i < LancerExo1.nbPhilosophe; i++) {
			if (j + 1 < baguettes.toArray().length) {
				philosophes.add(new Philosophe("philosophe" + i, (Baguette) baguettes.toArray()[j],
						(Baguette) baguettes.toArray()[j + 1]));
				if (j + 2 != baguettes.toArray().length) {
					j += 2;
				} else {
					j++;
				}
			} else {
				philosophes.add(new Philosophe("philosophe" + i, (Baguette) baguettes.toArray()[j],
						(Baguette) baguettes.toArray()[0]));
			}
		}
		Thread th;
		for (int i = 0; i < philosophes.toArray().length; i++) {
			th = new Thread((Philosophe) philosophes.toArray()[i]);
			threads.add(th);
			th.start();
		}

		th = new Thread(new Runnable() {
			@Override
			public void run() {
				int a = 10 * (int) (Math.random() * 100);
				System.out.println("je vais attendre :" + a + "ms");
				try {
					Thread.currentThread();
					Thread.sleep(a);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < threads.size(); i++) {
			((Thread) threads.toArray()[i]).interrupt();
		}
		for (int i = 0; i < philosophes.toArray().length; i++) {
			Philosophe p = (Philosophe) philosophes.toArray()[i];
			System.out.println("" + p.getNom() + " : j'ai mangé " + p.getNbMange() + " fois et j'ai "
					+ p.NbBaguetteDetenu() + " baguettes");
		}

	}

}
