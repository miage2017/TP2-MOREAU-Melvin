package fr.unice.miage.sd.tp3;

public class Consommateur extends Thread{
	Producteur produ;
	 
	Consommateur(Producteur p) {
        produ = p;
    }
 
    public void run() {
        try {
            while (true) {
                String message = produ.getJobs();
                System.out.println("Recupere le travail: " + message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}