package fr.unice.miage.sd.td4;

import java.util.concurrent.locks.ReentrantLock;

public class Baguette extends ReentrantLock {
	private Philosophe philo;
	private PhilosopheMalin philomalin;

	public synchronized boolean prendre(Philosophe philo) {
		if (this.philo != null || this.philomalin != null) {
			return false;
		} else {
			this.philo = philo;
			return true;

		}
	}

	public synchronized boolean prendre(PhilosopheMalin philo) {
		if (this.philo != null || this.philomalin != null) {
			return false;
		} else {
			this.philomalin = philo;
			return true;

		}
	}

	public synchronized boolean poser() {
		if (this.philo != null || this.philomalin != null) {
			this.philo = null;
			this.philomalin = null;
			return true;
		} else {
			return false;
		}
	}

	public synchronized Philosophe getPhilosophe() {
		return this.philo;
	}

	public synchronized PhilosopheMalin getPhilosopheMalin() {
		return this.philomalin;
	}
}
