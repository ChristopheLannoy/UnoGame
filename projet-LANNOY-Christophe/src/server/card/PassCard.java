package server.card;

import java.util.ArrayList;

import server.Round;

public class PassCard extends ColoredCard {
	
	public PassCard(String color) {
		super(20, color);
	}

	@Override
	public String toString() {
		return "Passer-" + getColor();
	}

	@Override
	public ArrayList<String> conditionsOnNextCard() {
		ArrayList<String> conditions = new ArrayList<String>();
		conditions.add(getColor());
		conditions.add("Passer");
		return conditions;
	}

	@Override
	public ArrayList<String> caracteristics() {
		return conditionsOnNextCard();          //same for this card
	}

	@Override
	public void makeEffect(Round round) {
		round.passNextPlayer();
	}

}
