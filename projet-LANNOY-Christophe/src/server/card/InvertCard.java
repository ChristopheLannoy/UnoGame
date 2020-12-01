package server.card;

import java.util.ArrayList;

import server.Round;
/**
 * Carte Inversion
 * @author 32474
 *
 */
public class InvertCard extends ColoredCard {

	public InvertCard(String color) {
		super(20, color);
	}

	@Override
	public String toString() {
		return "Inversion-" + getColor();
	}

	@Override
	public ArrayList<String> conditionsOnNextCard() {
		ArrayList<String> conditions = new ArrayList<String>();
		conditions.add(getColor());
		conditions.add("Inversion");
		return conditions;
	}

	@Override
	public ArrayList<String> caracteristics() {
		return conditionsOnNextCard();
	}

	@Override
	public void makeEffect(Round round) {
		round.invertPlayerOrder();
		
	}
}
