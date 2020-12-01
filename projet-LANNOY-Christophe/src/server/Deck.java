package server;

import java.util.ArrayList;
import java.util.Collections;

import server.card.Card;
import server.card.ClassicCard;
import server.card.InvertCard;
import server.card.Joker;
import server.card.PassCard;
import server.card.Plus2Card;
import server.card.Plus4Card;

public class Deck {
	public static final String[] COLORS = {"rouge", "bleu", "jaune", "vert"};
	private ArrayList<Card> deck;  //index 0 = top
	private Talon talon;
	/**
	 * Create a deck with all the 108 cards
	 * @param talon
	 */
	public Deck(Talon talon) {
		this.talon = talon;
		deck = new ArrayList<Card>();
		for (String color : COLORS) {
			for (int i = 1; i <= 9; i++) {
				deck.add(new ClassicCard(i, color));
				deck.add(new ClassicCard(i, color));
			}
			deck.add(new ClassicCard(0, color));
			for(int i = 0; i<2; i++) {
				deck.add(new Plus2Card(color));
				deck.add(new InvertCard(color));
				deck.add(new PassCard(color));
			}
		}
		for(int i = 0; i<4; i++) {
			deck.add(new Plus4Card());
			deck.add(new Joker());
		}
		Collections.shuffle(deck);
	}
		
	/**
	 * Pioche une carte du deck. Si celui-ci est vide, le deck est 
	 * réapprovisioné en reprenant les cartes du talon (en les mélangeant)
	 * tout en laissant la carte supérieur de ce dernier. 
	 * @return 'Card' au sommet du deck
	 */
	public Card remove() {
		if(deck.size()==0) {
			deck = talon.getAllCard();
			talon.getAllCard().clear();
			talon.add(deck.remove(0));
			Collections.shuffle(deck);
		}
		return deck.remove(0);
	}
	
	
	/**
	 * Ajoute une carte au deck puis mélange ce dernier
	 * @param card Carte à ajouter au deck
	 */
	public void add(Card card) {
		deck.add(card);
		Collections.shuffle(deck);
	}

}
