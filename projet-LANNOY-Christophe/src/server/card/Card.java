package server.card;

import java.util.ArrayList;

import server.Round;

public abstract class Card {
	
	private int scoreValue; //value used to compute the score of the players
	/**
	 * Return all the possible conditions (color, number, role) that the next card must fullfill to be legaly played on this.card.
	 * If only one condition in the ArrayList<String> "conditions" is fullfilled by the "caracteristic" of the next card,
	 *  the card may be played 
	 * @return ArrayList<String> of "conditions"
	 */
	public abstract ArrayList<String> conditionsOnNextCard();
	/**
	 * Return all the characteristics that this.card have (color,number, role)
	 * If one characteristic belong to "conditionsOnNextCard",
	 *  the card may be played legally.
	 * @return ArrayList<String> of "characteristics"
	 */
	public abstract ArrayList<String> caracteristics();
	/**
	 * Apply the effect of the card (+2/+4/Passer/...) on the current round
	 * @param round
	 */
	public abstract void makeEffect(Round round);
	public abstract String toString();
	
	public Card(int scoreValue) {
		this.scoreValue = scoreValue;
	}
	/**
	 * Create any subObject of card based on the input String
	 * @param stringOfCard
	 * @return the Card corresponding to stringOfCard
	 */
	public static Card newCard(String stringOfCard) {
		String[] stringsOfCard = stringOfCard.split("-");
		if (stringsOfCard.length == 2) {
			switch (stringsOfCard[0]) {
			case "+4":
				return new Plus4Card(stringsOfCard[1]); //couleur choisie
			case "Joker":
				return new Joker(stringsOfCard[1]);     //couleur choisie
			case "Passer":
				return new PassCard(stringsOfCard[1]);	
			case "Inversion":
				return new InvertCard(stringsOfCard[1]);
			case "+2":
				return new Plus2Card(stringsOfCard[1]);	
			default:
				int i = 0;
				try {
					i = Integer.parseInt(stringsOfCard[0]);
					if(i<0 || i>9) {
						throw new NumberFormatException("Numéro de carte doit être compris entre 0 et 9");
					}
				}catch(Exception e){
					e.printStackTrace();
					System.exit(1);
				}
				return new ClassicCard(i, stringsOfCard[1]);
			}
		}else {
			System.out.println("string of card: " + stringOfCard + " not valid"); //joker and +4 must specify next chosen color (=>length == 2)
		}
		return null;
	}
	
	/**
	 * Vérifie que la carte2 peut être jouée sur la carte1 et renvois le boolean coorespondant
	 * @param card1
	 * @param card2
	 * @return boolean
	 */
	public static boolean isValid(Card card1, Card card2) { //card2 played on top of card1
		for(String condition: card1.conditionsOnNextCard()) {
			if(card2.caracteristics().contains(condition)) { //remplir une seule condition suffit
				return true;
			}
		}
		return false;
	}

	
	public int getScoreValue() {
		return scoreValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (scoreValue != other.scoreValue)
			return false;
		return true;
	}	
}
