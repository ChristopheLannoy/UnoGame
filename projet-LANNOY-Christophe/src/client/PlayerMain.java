package client;

import java.io.IOException;
import java.util.Scanner;

import client.controller.Keyboard;
import client.model.PlayerClient;
import client.view.Window;

public class PlayerMain {
	public static final String HOST = "127.0.0.1";
	public static final int PORT = 6789;

	public static void main(String[] args) throws IOException {
		
		//Ask player name and creat the ClientPlayer
		Scanner sc = new Scanner(System.in);
		System.out.printf("Quel est votre nom ?: ");
		String name = sc.nextLine();
		PlayerClient player = new PlayerClient(HOST, PORT, name, sc);
		
		//Mode Graphique
		String userInput = "";
		while( !(userInput.equals("y") || userInput.equals("n")) ) {
			System.out.printf("Voulez vous utiliser l'interface graphque (y/n + enter) ?: ");
			userInput = sc.nextLine();
		}
		if(userInput.equals("y")) { 
			player.setGraphicMode(true);              //false by default
			Window window = new Window(name);
			player.setWindow(window);
			Keyboard keyboard = new Keyboard(window);
			window.setKeyListener(keyboard);          //lance implicitement un thread
		}
		
		while(true) {
			player.listen();    // pas top ?
		}
	}
}
