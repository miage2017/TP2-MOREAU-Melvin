package fr.unice.miage.sd.td4;

public class PhilosopheMalin implements Runnable {
	private String nom;
	private Baguette droite, gauche;
	private boolean bagD, bagG;
	private int nbMange;
	private Philosophe[] voisins;
	private boolean mangeRecent;

	public PhilosopheMalin(String name, Baguette droite, Baguette gauche) {
		this.nom = name;
		this.droite = droite;
		this.gauche = gauche;
		this.bagD = false;
		this.bagG = false;
		this.nbMange = 0;
		this.voisins = new Philosophe[2];
		this.mangeRecent = false;
	}

	public int getNbMange() {
		return this.nbMange;
	}

	public int NbBaguetteDetenu() {
		int nbBag = 0;
		if (this.bagD) {
			nbBag++;
		}
		if (this.bagG) {
			nbBag++;
		}
		return nbBag;
	}

	public String getName() {
		return this.nom;
	}

	public boolean aBagD() {
		return this.bagD;
	}

	public boolean aBagG() {
		return this.bagG;
	}

	private void manger() {
		if (bagD && bagG) {
			nbMange++;
			this.droite.poser();
			this.bagD = false;
			this.gauche.poser();
			this.bagG = false;
			this.mangeRecent = true;
		} else if (this.mangeRecent) {
			if (bagD) {
				this.droite.poser();
				this.mangeRecent = false;
			} else if (bagG) {
				this.gauche.poser();
				this.mangeRecent = false;
			}
		}
	}

	private void prendreBaguettes() {
		int a = 1 * (int) (Math.random() * 2);
		if (a == 0) {
			if (!this.bagD) {
				this.bagD = this.droite.prendre(this);
			}
		} else {
			if (!this.bagG) {
				this.bagG = this.gauche.prendre(this);
			}
		}
	}

	@Override
	public void run() {
		Thread.currentThread();
		while (!Thread.interrupted()) {
			this.prendreBaguettes();
			this.manger();
		}
	}

}
