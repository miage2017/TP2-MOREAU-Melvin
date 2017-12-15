package fr.unice.miage.sd.td4;

import java.util.ArrayList;

public class LancerExo2 {
	public static int nbPhilosophe = 5;

	public static void main(String[] args) {
		ArrayList<PhilosopheMalin> philosophes = new ArrayList<PhilosopheMalin>();
		ArrayList<Baguette> baguettes = new ArrayList<Baguette>();
		ArrayList<Thread> threads = new ArrayList<Thread>();
		int j = 0;

		for (int i = 0; i < LancerExo2.nbPhilosophe; i++) {
			baguettes.add(new Baguette());
		}
		for (int i = 0; i < LancerExo2.nbPhilosophe; i++) {
			if (j + 1 < baguettes.toArray().length) {
				philosophes.add(new PhilosopheMalin("philosophe" + i, (Baguette) baguettes.toArray()[j],
						(Baguette) baguettes.toArray()[j + 1]));
				if (j + 2 != baguettes.toArray().length) {
					j += 2;
				} else {
					j++;
				}
			} else {
				philosophes.add(new PhilosopheMalin("philosophe" + i, (Baguette) baguettes.toArray()[j],
						(Baguette) baguettes.toArray()[0]));
			}
		}
		Thread th;
		for (int i = 0; i < philosophes.toArray().length; i++) {
			th = new Thread((PhilosopheMalin) philosophes.toArray()[i]);
			threads.add(th);
			th.start();
		}

		th = new Thread(new Runnable() {
			@Override
			public void run() {
				int a = 10 * (int) (Math.random() * 100);
				System.out.println("je vais attendre : " + a + "ms");
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
			PhilosopheMalin p = (PhilosopheMalin) philosophes.toArray()[i];
			System.out.println("" + p.getName() + " : j'ai mangé " + p.getNbMange() + " fois et j'ai "
					+ p.NbBaguetteDetenu() + " baguettes");
		}

	}

}
