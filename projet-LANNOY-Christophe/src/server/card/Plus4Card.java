package server.card;

import server.Round;

public class Plus4Card extends NonColoredCard{

	public Plus4Card() {
	}
	public Plus4Card(String color) {
		super(color);
	}
	@Override
	public String toString() {
		if(nextColorChosen != null) {
			return "+4-" + nextColorChosen;
		}
		return "+4";
	}
	
	 @Override
		public void makeEffect(Round round) {
			round.plus4ForNextPlayer();
		}

}
