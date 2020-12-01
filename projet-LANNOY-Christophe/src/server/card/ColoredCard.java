package server.card;
/**
 * Represent all the cards that have a color(=all exept +4/Joker)
 * @author 32474
 *
 */
public abstract class ColoredCard extends Card {
	
	private String color;

	public ColoredCard(int scoreValue, String color) {
		super(scoreValue);
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColoredCard other = (ColoredCard) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
	
	

	

}
