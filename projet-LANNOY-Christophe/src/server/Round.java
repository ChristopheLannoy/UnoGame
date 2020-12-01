package server;

import java.io.IOException;
import java.util.ArrayList;

import server.card.Card;
import server.card.ClassicCard;


public class Round {

	private ArrayList<Player> playerList;
	Player currentPlayer = null;
	int cpi;                   //Current Player Index
	private Deck deck;
	private Talon talon;
	private boolean endRound;
	
	/**
	 * Create a new Roundand set the attributes playerList 
	 * Create a new Deck and a new Talon
	 * @param playerList
	 */
	public Round(ArrayList<Player> playerList) {
		endRound = false;
		this.playerList = playerList;
		for(Player player: playerList) {
			player.setRound(this);
		}
		talon = new Talon();
		deck = new Deck(talon);
	}
	
	
	/**
	 * Retourne la première carte du deck pour avoir la première carte du talon
	 * Si la première carte n'est pas une carte classique (de 0 à 9) celle-ci est remise
	 * dans le deck, le deck est mélangé, et finalement on tire une autre carte
	 * (règle choisie arbitrairement)
	 */
	public void drawFirstCardForTalon() {
		Card card = deck.remove();
		while(card.getClass() != ClassicCard.class) {
			deck.add(card);
			card = deck.remove();
		}
		talon.add(card);
	}
	
	/**
	 * Inform all players of 'debut de manche'
	 * Distribute the card to the player and set the 'talon'
	 * Then ask consecutively all the player to play until the round is finished
	 * Finally update the score of the winning player
	 * @throws IOException
	 */
	public void startRound() throws IOException {
		for (Player player: playerList) {
			player.send("debut-de-manche");
		}
		//Collections.shuffle(playerList); // first player chosen randomly
		distributionCard();
		drawFirstCardForTalon(); //retourne la première carte
		sendToAllPlayers("nouveau-talon " + talon.getTopCard());
		cpi = -1; //star with player 0 in playerList
		while(!endRound) {
			cpi = (cpi+1)%playerList.size(); //next player index
			currentPlayer = playerList.get(cpi);
			currentPlayer.play(deck, talon);
			sendToAllPlayers("nouveau-talon " + talon.getTopCard());
			endRound = (currentPlayer.getHand().size() == 0);
		}
		for (Player player: playerList) {
			currentPlayer.increaseScore(player.getPointsInHand()); //winningPlayer.getPoints() = 0
		}
		endRound();
	}
	
	/**
	 * Compute the score of this round (and add the score of this round with 
	 * the previous rounds if any) and send it to all the players
	 */
	public void endRound() {
		String roundScore = "fin-de-manche";
		for (Player player: playerList) {
			roundScore += " " +player.getName();
			roundScore += " " +player.getScore();			
		}
		for (Player player: playerList) {
			player.send(roundScore);
			player.getHand().clear();
		}
	}
	
	/**
	 * Send the String s to all the players and print it in the console
	 * 
	 * @param s String to be send
	 */
	public void sendToAllPlayers(String s) {
		for(Player player: playerList) {
			player.send(s);
		}
	}
	/**
	 * Inverse l'ordre cyclique des joueurs de 'playerList' 
	 */
	public void invertPlayerOrder() {
		ArrayList<Player> newPlayerList = new ArrayList<>();
		for(int j = 0; j < playerList.size(); j++) {
			int index = (cpi+j)%playerList.size();    // 'cpi' = Current Player Index
			newPlayerList.add(0, playerList.get(index));
		}
		playerList = newPlayerList;
		cpi = playerList.size() - 1;        //le joueur actuel donné par l'indice 'cpi' se retrouve en fin de liste
	}
	
	/**
	 * the next player wont be able to play
	 */
	public void passNextPlayer() {
		cpi = (cpi+1)%playerList.size(); //next player index
	}
	
	/**
	 * next player will draw 2 card then pass is turn
	 * all the players will be informed of this
	 */
	public void plus2ForNextPlayer() {
		cpi = (cpi+1)%playerList.size(); //next player index
		playerList.get(cpi).sendCard(deck.remove());
		playerList.get(cpi).sendCard(deck.remove());
		sendToAllPlayers("joueur " + playerList.get(cpi).getName() + " pioche 2");
	}
	/**
	 * next player will draw 4 card then pass is turn
	 * all the players will be informed of this
	 */
	public void plus4ForNextPlayer() {
		cpi = (cpi+1)%playerList.size(); //next player index
		for(int k=0; k<4; k++) {
			playerList.get(cpi).sendCard(deck.remove());
		}
		sendToAllPlayers("joueur " + playerList.get(cpi).getName() + " pioche 4");
	}

	/**
	 * Send to all the players 7 card of the deck
	 */
	public void distributionCard() {
		for(Player player: playerList) {
			for (int i = 0; i < 7; i++) {
				player.sendCard(deck.remove());
			}
		}
	}
	

}
