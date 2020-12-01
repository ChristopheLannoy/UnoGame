package server.card;

import java.util.ArrayList;

import server.Round;

public class Plus2Card extends ColoredCard {

	public Plus2Card(String color) {
		super(20, color);
	}
	
    @Override
	public void makeEffect(Round round) {
		round.plus2ForNextPlayer();
	}

	@Override
	public String toString() {
		return "+2-" + getColor();
	}

	@Override
	public ArrayList<String> conditionsOnNextCard() {
		ArrayList<String> conditions = new ArrayList<String>();
		conditions.add(getColor());
		conditions.add("+2");
		return conditions;
	}

	@Override
	public ArrayList<String> caracteristics() {
		return conditionsOnNextCard();     //same for this card
	}

}
