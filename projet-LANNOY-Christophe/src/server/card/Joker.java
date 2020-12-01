package server.card;

import server.Round;

public class Joker extends NonColoredCard{

	public Joker() {
		
	}
	public Joker(String color) {
		super(color);
	}
	@Override
	public String toString() {
		if(nextColorChosen != null) {
			return "Joker-" + nextColorChosen;
		}
		return "Joker";
	}
	@Override
	public void makeEffect(Round round) { // no effect
	}

}
