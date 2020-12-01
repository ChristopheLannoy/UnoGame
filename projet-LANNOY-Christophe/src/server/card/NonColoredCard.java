package server.card;

import java.util.ArrayList;

public abstract class NonColoredCard extends Card{
	String nextColorChosen;

	public NonColoredCard() {
		super(50);
		nextColorChosen = null;
	}
	public NonColoredCard(String color) {
		super(50);
		nextColorChosen = color;
	}
	
	public void setNextColorChosen(String color) {
		nextColorChosen = color;
	}
	
	public abstract String toString();

	@Override
	public ArrayList<String> conditionsOnNextCard() {
		ArrayList<String> conditions = new ArrayList<String>();
		conditions.add(nextColorChosen);
		return conditions;
	}

	@Override
	public ArrayList<String> caracteristics() {
		ArrayList<String> caracteristics = new ArrayList<String>();
		caracteristics.add("rouge");
		caracteristics.add("vert");
		caracteristics.add("bleu");
		caracteristics.add("jaune");
		return caracteristics;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		/* NonColoredCard other = (NonColoredCard) obj;               // On ne vérifie pas cet attribut car on considère 
		if (nextColorChosen == null) {                                // que c'est le même objet indépendamment de la
			if (other.nextColorChosen != null)                        // couleur imposée à la carte suivante (+4/Joker)
				return false;
		} else if (!nextColorChosen.equals(other.nextColorChosen))
			return false; */       
		return true;
	}
	
	
	
}
