package client.model;

public class Opponent {
/**
 * Représente les joueurs adverses
 * Objet utilisé pour stocker leur nom ainsi que le nombre de carte
 * qui leur reste en main.
 */
	private String name;
	private int numberOfCard;
	
	public Opponent(String name) {
		this.name = name;
		numberOfCard = 7;
	}

	/**
	 * add 'i' card to the player
	 * @param i number of card added
	 */
	public void addCards(int i) {
		if(numberOfCard + i >= 0) {
			numberOfCard += i;
		}else {
			System.out.println("ERREUR nombre de carte d'un adversaire négatif");
		}
	}

	/**
	 * @param numberOfCard the numberOfCard to set
	 */
	public void setNumberOfCard(int numberOfCard) {
		this.numberOfCard = numberOfCard;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the numberOfCard
	 */
	public int getNumberOfCard() {
		return numberOfCard;
	}
}

