package fr.unice.miage.sd.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serv_multi {

	private ServerSocket sSocket ;
	private boolean isRunning = true;

	public Serv_multi() {
		try {
			this.sSocket = new ServerSocket(12000);
			System.out.println("Serveur en cours d'execution");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void open() {
		Thread t = new Thread(new Runnable(){
			public void run(){
				while(isRunning == true){

					try {
						Socket client = sSocket.accept();

						System.out.println("Connexion cliente reçue.");                  
						Thread t1 = new Thread(new Runnable(){

							@Override
							public void run() {
								Service_Client(client);
							}

						});
						t1.start();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}


			}
		});

		t.start();

	}

	public void Service_Client(Socket client) {
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(client.getInputStream(), "UTF-8");
			BufferedReader flux_entrant = new BufferedReader(isr) ;
			PrintStream out = new PrintStream(client.getOutputStream()); 
			int i=1;
			String read = flux_entrant.readLine();
			String nameClient = read.split("Le Client dit Bonjour")[1];
			out.println("Bonjour "+nameClient+"!");
			while(read!=null) {
				System.out.println("le client numéro "+nameClient+" écrit : "+read);
				flux_entrant = new BufferedReader(isr) ;
				if(read.equals("FINISH")) {
					break;
				}
				read = flux_entrant.readLine();
				i++;

			}
			System.out.println("le client s'est déconnecter");
			client.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	
	public static void main(String[] args) {
		Serv_multi serv = new Serv_multi();
		serv.open();
	}

}
