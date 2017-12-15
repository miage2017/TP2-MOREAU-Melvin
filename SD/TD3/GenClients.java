package fr.unice.miage.sd.tp3;

public class GenClients {

	private int nbCLi;
	private Thread[] Threads;

	public GenClients(int nbClient) {
		this.nbCLi = nbClient;
	}
	
	private String createName(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String val = "";
		for (int x = 0; x < length; x++) {
			int i = (int) Math.floor(Math.random() * 62);
			val += chars.charAt(i);
		}
		System.out.println(val);
		return val;
	}

	private void createClient() {
		Threads = new Thread[nbCLi];
		for (int i = 0; i < nbCLi; i++) {
			Threads[i] = new Thread(new Client(createName(5), true));
		}
	}

	private void launchClient() {
		for (int i = 0; i < Threads.length; i++) {
			Threads[i].start();
		}
	}

	public void gotToConnectClients() {
		createClient();
		launchClient();
	}


	public static void main(String[] args) {
		GenClients cli = new GenClients(5);
		cli.gotToConnectClients();
	}

}
