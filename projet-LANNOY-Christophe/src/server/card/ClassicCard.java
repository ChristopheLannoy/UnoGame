package server.card;

import java.util.ArrayList;

import server.Round;
/**
 * Represente les cartes colorées de 0 à 9
 * @author 32474
 *
 */
public class ClassicCard extends ColoredCard {
	
	// no need for a "value" attribute since value = scoreValue defined in super class Card
	
	public ClassicCard(int value, String color) {
		super(value, color); //set scoreValue (= value for classic card)
	}
	
	/*
	public ClassicCard(String s) {
		this(Integer.parseInt(s.split("-")[0]), s.split("-")[1]);
	}*/
	

	@Override
	public String toString() {
		return this.getValue() + "-" + this.getColor();
	}
	
	public int getValue() {
		return this.getScoreValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public ArrayList<String> conditionsOnNextCard() {
		ArrayList<String> conditions = new ArrayList<String>();
		conditions.add(getColor());
		conditions.add(""+ getValue()); //to parse to String
		return conditions;
	}

	@Override
	public ArrayList<String> caracteristics() {
		return conditionsOnNextCard();
	}

	@Override
	public void makeEffect(Round round) {  //no effect for classic cards ==> do nothing
	}	

}
