package fr.unice.miage.sd.tp3;

public final class ObjetExecutable implements Runnable {
	private volatile boolean done = false;

	public void seterminer() {
		done = true;
	}

	public synchronized void run() {
		int i=0;
		System.out.println("Je compte en partant de 0");
		while (!done) {
			i++;
			System.out.println((i-1)+ " + 1 = "+i);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ObjetExecutable container = new ObjetExecutable();
		Thread myThread = new Thread(container);
		myThread.start();
		Thread.sleep(5000);
		container.seterminer();
	}

}