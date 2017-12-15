package fr.unice.miage.sd.td4;

public class Philosophe implements Runnable {

	private String nom;
	private Baguette droite, gauche;
	private boolean bagD, bagG;
	private int nbMange;

	public Philosophe(String nom, Baguette droite, Baguette gauche) {
		this.nom = nom;
		this.droite = droite;
		this.gauche = gauche;
		this.bagD = false;
		this.bagG = false;
		this.nbMange = 0;
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

	public String getNom() {
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
