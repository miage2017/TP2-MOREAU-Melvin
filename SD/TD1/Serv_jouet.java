package fr.unice.miage.sd.tp1;

import java.net.*;
import java.text.spi.NumberFormatProvider;
import java.io.*;
import java.util.*;

public class Serv_jouet {
	Socket client ;

	public Serv_jouet() {
		try {
			ServerSocket socketserver = new ServerSocket(12000);
			System.out.println("Serveur en cours d'execution...");
			client = socketserver.accept();
			System.out.println("Client accepté.");

			InputStreamReader isr = new InputStreamReader(client.getInputStream(), "UTF-8");
			BufferedReader flux_entrant = new BufferedReader(isr) ;

			int i=1;
			String read = flux_entrant.readLine();
			System.out.println("Client :");
			while(read!=null) {
				System.out.println("ligne "+i+": "+read);
				flux_entrant = new BufferedReader(isr) ;
				if(read.equals("FINISH")) {
					break;
				}
				read = flux_entrant.readLine();
				i++;
			}

			client.close();
			System.out.println("le client s'est déconnecter");
			System.out.println("déconnexion du serveur");
			socketserver.close();

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) {
		Serv_jouet serv = new Serv_jouet();

	}

}

