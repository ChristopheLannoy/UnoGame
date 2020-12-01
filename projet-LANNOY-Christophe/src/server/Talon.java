package server;

import java.util.ArrayList;

import server.card.Card;

public class Talon {
	
	private ArrayList<Card> talon;  //index 0 = top
	
	public Talon() {
		talon = new ArrayList<Card>();
	}
	/**
	 * Add a card to the 'talon' and check if the move is legal
	 * @param card
	 * @return true if the 'card' can legally by played on the talon, false otherwise
	 */
	public boolean add(Card card) {
		if(talon.size() == 0) { // 1st card on the talon ==> no condition
			talon.add(0, card);
			return true;
		}
		if(Card.isValid(this.getTopCard(), card)) {
			talon.add(0, card);
			return true;
		}else {
			System.out.println("carte pas jouable");
		}
		return false;
		
	}
	public ArrayList<Card> getAllCard() {
		return talon;
	}
	public Card getTopCard() {
		return talon.get(0);
	}

}
