package client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import client.view.Window;

public class PlayerClient {
	
	public static final String[] QUESTIONS = {
			"Voulez-vous jouer une carte ? (dans la négative vous piocherez une carte) (y/n + enter): ",
			"Quelle carte voulez-vous jouer ? (entrer l'indice de la carte + enter): ",
			"Voulez-vous jouer une carte ? (dans la négative vous passerez votre tour) (y/n + enter): ",
			"Quelle couleur voulez-vous imposer à la carte suivante ? (r/v/b/j + enter)"};
	
	private Socket socket;
	private PrintWriter txtOut;
	private BufferedReader txtIn;
	Scanner sc;
	
	private String name;
	private ArrayList<CardClient> hand;
	private ArrayList<Opponent> opponentList;
	private CardClient talon; //dernière carte posée sur le talon
	private Window window;
	private boolean graphicMode;
	
	
	public PlayerClient(String host, int port, String name, Scanner sc) throws IOException {
		this.sc = sc;
		socket = new Socket(host, port);
		this.name = name;
		System.out.printf("%s connected to server, local port %d\n", name, socket.getLocalPort());
		txtOut = new PrintWriter(socket.getOutputStream(), true);
		txtIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		hand = new ArrayList<CardClient>();
		opponentList = new ArrayList<Opponent>();
		graphicMode = false;  //by default
		
		askToJoin();
	}
	
	
	/**
	 * ask the server to join the game
	 * @throws IOException
	 */
	public void askToJoin() throws IOException {
		send("je-suis " + name); // client demande à rejoindre la partie
		String response = txtIn.readLine();
		switch (response) {
		case "bienvenue":
			System.out.println(response);
			break;
		case "Erreur : plus de place":
			System.out.println(response);
			socket.close();
			System.exit(0);
		default:
			System.out.println("Réponse du serveur invalide: " + response);
			System.exit(1);
		}
	}
	
	public void setGraphicMode(boolean b) {
		graphicMode = b;
	}
	public void setWindow(Window window){
		this.window = window;
		window.setHand(hand);
		window.setOpponentList(opponentList);
		window.setTalon(talon);
	}
	/**
	 * Listen to the server through txtIn.readLine() and print the server message.
	 * If the server is disconnected, end the programme.
	 * Call the appropriates functions based on the server message 
	 * @throws IOException
	 */
	public void listen() throws IOException {
		String serverMessage = txtIn.readLine();
		System.out.println(name + " <- S: " + serverMessage);
		if(serverMessage == null) {
			System.out.println("Serveur déconnecté");
			System.exit(1);
		}
		switch(serverMessage.split(" ")[0]) {
		case "debut-de-manche":
			for(Opponent opponent: opponentList) {
				opponent.setNumberOfCard(7);
			}
			break;
		
		case "prends":
			hand.add(new CardClient(serverMessage.split(" ")[1]));
			Collections.sort(hand);   //trie la main du joueur pour classer les cartes selon compareTo() de CardClient
			if(graphicMode) {window.update();}
			break;
		
		case "nouveau-talon":
			talon = new CardClient(serverMessage.split(" ")[1]);
			if(graphicMode) {
				window.setTalon(talon);
				window.update();
			}
			break;
		
		case "joue":
			play();
			break;
		
		case "OK":
			break;
		
		case "joueur":
			getInfoOfOtherPlayer(serverMessage);
			break;
		
		case "fin-de-manche":
			hand.clear();
		//	window.update();
			break;
		
		case "fin-de-partie":
			socket.close();
			System.exit(0);
			break;
		
		case "erreur":
			break;
		
		default:
			System.out.println("message serveur invalide: " + serverMessage);
		}
	}
	
	/**
	 * ask the String "question" to the user and get a boolean response (y/n) 
	 * question is asked through GUI if the boolean "graphicMode" is true 
	 * else question is asked through the console based on 
	 * @param question
	 * @return boolean (y/n)
	 */
	public boolean askPlaying(String question) {
		displayHand();
		String userInput = "";
		while( !(userInput.equals("y") || userInput.equals("n")) ) {
			if(graphicMode) {
				userInput = window.askUser(question);
			}else {
				System.out.printf(question);
				userInput = sc.nextLine();
			}
		}
		if(userInput.equals("y")) {                            //le joueur veut jouer
			return true;
		}
		return false;                                    //le joueur ne veut pas jouer (il va donc piocher une carte ou passer solon le cas)
	}
	
	/**
	 * ask the a color to the user and get a String response (r,v,b,j) (abreviation francaise). 
	 * Question is asked through GUI if the boolean "graphicMode" is true. 
	 * Else question is asked through the console based on. 
	 * @return String (r,v,b or j)
	 */
	public String askColor() {
		String userInput = "";
		while( !(userInput.equals("r") || userInput.equals("v") || userInput.equals("b") || userInput.equals("j") ) ) {
			if(graphicMode) {
				userInput = window.askUser(QUESTIONS[3]);
			}else {
				System.out.printf(QUESTIONS[3]);
				userInput = sc.nextLine();
			}
		}
		switch(userInput) {
		case "r":
			return "rouge";
		case "v":
			return "vert";
		case "b":
			return "bleu";
		default:
			return "jaune";
		}
		
	}
	
	
	/**
	 * The player calling may play: 
	 * 1) ask jouer ou piocher
	 * 2) if piocher => ask jouer ou passer
	 * Handel the card accordingly to the user request
	 * @throws IOException
	 */
	public void play() throws IOException {
		if (askPlaying(QUESTIONS[0])) {                   //demande au joueur si il veux jouer (dans la négative il pioche)
			CardClient carteDeposee = hand.remove(askIndex());
			if(carteDeposee.getColor() == null) {
				carteDeposee.setColor(askColor());
			}
			send("je-pose " + carteDeposee);
			if(graphicMode){window.update();}
		} else {                                          // joueur ne veut pas jouer ==> il pioche
			send("je-pioche");
			hand.add(0,new CardClient(txtIn.readLine().split(" ")[1]));
			System.out.println("Carte piochée: " + hand.get(0));
			Collections.sort(hand);
			if(graphicMode){window.update();}
			String serverMessage = "";
			while(!serverMessage.equals("joue") ) {
				serverMessage = txtIn.readLine(); 
				System.out.println(name + " <- S:" + serverMessage);
			}
			
			if (askPlaying(QUESTIONS[2])) {               //Apres avoir piocher, veut-on jouer ou passer ?
				CardClient carteDeposee = hand.remove(askIndex());
				if(carteDeposee.getColor() == null) {
					carteDeposee.setColor(askColor());
				}
				send("je-pose " + carteDeposee);
				if(graphicMode){window.update();}
			}else {                                       //Le joueur veut passer
				send("je-passe");
			}	
		}
		if(graphicMode) {window.informUser("Wait");}
	}
	
	/**
	 * Send the string 's' to the server and print s on the console
	 * @param s
	 */
	public void send(String s) {
		txtOut.println(s);
		System.out.println(name + " -> " + s);
	}
	
	/**
	 * Ask to the user the index of the card he want to play
	 * @return int index of the card in hand
	 */
	public int askIndex() {
		String userInput = "";
		int index = 0;
		boolean validInput = false;
		while(!validInput) {
			if(graphicMode) {
				userInput = window.askUser(QUESTIONS[1]);
			}else {                                        //ask through console
				System.out.print(QUESTIONS[1]);
				userInput = sc.nextLine();
			}
			try {
				index = Integer.parseInt(userInput);
				if(index < 0 || index > hand.size() - 1) {
					throw new NumberFormatException();
				}
				validInput = true;
			}catch(NumberFormatException e){
				System.out.println("Entrez un indice valide !");
			}
		}
		return index;
	}
	
	/**
	 * Update the status of the opponent based on the server message (beginning with "joueur")
	 * Number of card in theirs hands
	 * @param serverMessage
	 */
	public void getInfoOfOtherPlayer(String serverMessage) {
		boolean playerInList = false;
		Opponent currentPlayer = null;
		String name = serverMessage.split(" ")[1];
		if(name.equals(this.name)) {
			return; // pas d'info à prendre
		}
		for(Opponent player: opponentList) {
			if(name.equals(player.getName()) ){
				currentPlayer = player;
				playerInList = true;
			}
		}
		if(!playerInList) {
			currentPlayer = new Opponent(name);
			opponentList.add(currentPlayer);
		}
		switch(serverMessage.split(" ")[2]) {
		case "passe":
			break;
			
		case "pioche":
			if(Integer.parseInt(serverMessage.split(" ")[3])==1) {
				currentPlayer.addCards(1);
			}
			else if(Integer.parseInt(serverMessage.split(" ")[3])==2) {
				currentPlayer.addCards(2);
			}
			else {currentPlayer.addCards(4);}  //+4 card
			break;
		
		case "pose":
			currentPlayer.addCards(-1);
			break;
			
		default:
			System.out.println("Erreur: message non protocolaire: " + serverMessage);
		}
		if(graphicMode) {window.update();}
	}
	
	
	/**
	 * Display the card of the player on the console
	 */
	public void displayHand() {
		System.out.println("Votre main actuelle est: ");
		for(CardClient card: hand) {
			System.out.print(card.toShortString() + " ");
		}
		System.out.println();
	}


}
