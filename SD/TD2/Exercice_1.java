package fr.unice.miage.sd.tp2;

public class Exercice_1 implements Runnable{
	
	public String name;
	public int debut;
	public int fin;

	public Exercice_1(String name, int debut, int fin) {
		this.name = name;
		this.debut = debut;
		this.fin = fin;
	}

	@Override
	public void run() {
		if(debut > fin) {			
			for(int i=1000;i>=1;i--) {
				System.out.println(name+" count : "+i);
			}
		}else {
			for(int i=debut;i<fin;i++) {
				System.out.println(name+" count : "+i);
			}
		}
	}

	public static void main(String[] args) {
		Exercice_1   A  = new Exercice_1("TA", 1, 1000);
		Thread TA = new Thread(A);
		System.out.format("Creating thread %s\n", A.name);
		Exercice_1   B  = new Exercice_1("TB", 1000, 1);
		Thread TB = new Thread(B);
		System.out.format("Creating thread %s\n", B.name);
		TA.start();
		TB.start();
		
	}
	
	
	
}
