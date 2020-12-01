package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import server.card.Card;

public class Player {
	
	private PrintWriter txtOut;
	private BufferedReader txtIn;
	private String name;
	private int score;
	private ArrayList<Card> hand;
	private Round round;
	
	public Player(Socket clientSocket) throws IOException {
		txtOut = new PrintWriter(clientSocket.getOutputStream(), true);
		txtIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		hand = new ArrayList<Card>();
		name = listen().split(" ")[1];
	}
	
	public int getScore() {
		return score;
	}
	public String getName() {
		return name;
	}
	
	public void setRound(Round round) {
		this.round = round;
	}
	
	public void increaseScore(int points) {
		score += points;
	}
	/**
	 * Compute the value of all the card the player has in his hand
	 * Usefull to compute the score at the end of a round
	 * @return int the sum of the value of the card still in hand
	 */
	public int getPointsInHand() {
		int points = 0;
		for(Card card: hand) {
			points += card.getScoreValue();
		}
		return points;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	/**
	 * send a card to the client and add it to hand in the server
	 * @param card
	 */
	public void sendCard(Card card) {
		hand.add(card);
		send("prends " + card);
	}
	
	/**
	 * Play a card (cartePosee) on the "talon"
	 * if the move is illegal send 2 card to this player
	 * Inform all the other players of the situation
	 * @param deck
	 * @param talon
	 * @param cartePosee
	 */
	public void playCard(Deck deck, Talon talon, Card cartePosee) {
		if(hand.remove(cartePosee)) {                              // si le joueur possède effectivement la carte qu'il souhaite jouer
			if(talon.add(cartePosee)) {                            //si la carte est jouable sur le talon actuel
				send("OK");
				round.sendToAllPlayers("joueur " + this.name + " pose " + cartePosee.toString());
				cartePosee.makeEffect(this.round);                 //applique les +2/passer/inversion/+4/jocker (ne fait rien dans le cas d'une carte classique)
			}else {                                                //carte pas jouable sur le talon
				System.out.println("Carte non jouable ==> pioche Deux");
				sendCard(cartePosee);
				sendCard(deck.remove());
				sendCard(deck.remove());
				round.sendToAllPlayers("joueur " + this.name + " pioche 2");
			}
		}else {
			System.out.println("ERREUR: carte posée pas dans la main du joueur");
			System.out.println("Je joue pas avec des tricheurs, chao!");
			System.exit(1);
		}
	}
	
	/**
	 * Ask to the client to play and get his response
	 * 
	 * @param deck
	 * @param talon
	 * @throws IOException
	 */
	public void play(Deck deck, Talon talon) throws IOException {
		send("joue");
		String request = listen();
		switch (request.split(" ")[0]) {
		case "je-pose":
			Card cartePosee = Card.newCard(request.split(" ")[1]);
			playCard(deck, talon, cartePosee);
			break;
			
		case "je-pioche":
			Card cartePiochee = deck.remove(); 
			sendCard(cartePiochee);
			round.sendToAllPlayers("joueur " + this.name + " pioche 1");
			send("joue");
			String request2 = listen();
			switch (request2.split(" ")[0]) {
			case "je-pose":
				cartePosee = Card.newCard(request2.split(" ")[1]);
				playCard(deck, talon, cartePosee);
				break;
				
			case "je-passe":
				round.sendToAllPlayers("joueur " + this.name + " passe");
				break;
			
			default:
				System.out.println("Protocol non respecté !2" + request2.split(" ")[0]);
			}
			break;
		
		default:
			System.out.println("Protocol non respecté !1" + request.split(" ")[0]);
		}
	}

	/**
	 * Send the String s to the player that call this method
	 * and print on the 'console' the message, the sender(the server) and the target player
	 * @param s String to be send to the player
	 */
	public void send(String s) {
		System.out.println("S -> " + name + " : " + s);
		txtOut.println(s);
	}
	
	/**
	 * Listen to the client through txtIn.readLine() and print the String on the console
	 * @return String response of the client
	 * @throws IOException
	 */
	public String listen() throws IOException {
		String s = txtIn.readLine();
		System.out.println("S <- " + name + " : " + s);
		return s;
	}
}
