package fr.unice.miage.sd.tp2;

public class ObjetEntier {
	private int ma_valeur;

	public ObjetEntier() {
		ma_valeur = 0;
		System.out.format("Valeur partagee initialisee a %d\n", ma_valeur);
	}

	public String toString() {
		return Integer.toString(ma_valeur);
	}

	public int val() {
		return ma_valeur;
	}

	public synchronized void add(int i) {
		ma_valeur += i;
	}

}