package client.model;
/**
 * Represent all the 108 possible card with the attributes "role" and "color"
 * @author 32474
 *
 */
public class CardClient implements Comparable<CardClient> {
	
	public static final String[] COLORS = {"rouge", "bleu", "jaune", "vert"};
	
	private String role; //0 à 9, +2, +4, Joker, Passer, Inversion
	private String color;
	
	public CardClient(String s) {
		String[] card = s.split("-");
		if(card.length == 1) {
			role = card[0];
		}else if(card.length == 2) {
			role = card[0];
			color = card[1];
		}else {
			System.out.println("ERROR:");
		}
		
	}
	
	public void setColor(String color) {    //pour les +4/Joker
		this.color = color;
	}
	
	public String getColor() {
		return color; 
	}
	
	public String toString() {
		if(color == null) {
			return role;
		}
		return role + "-" + color; 
	}
	
	/**
	 * Return a short version of the string that describe the card
	 * @return
	 */
	public String toShortString() {
		if(color == null) {
			return role;
		}
		return (role) + ("" + color.charAt(0)).toUpperCase();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardClient other = (CardClient) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	
	/**
	 * Compare la CardClient appelante et celle passée en argument pour leur donner un ordre
	 * Odre: +4, Joker, (0à9,+2,Inversion,Passer)-bleu, ("")-jaune, ("")-rouge, ("")-vert
	 * this > o ==> return 1
	 * this = o ==> return 0 (consistant avew equals())
	 * this < o ==> return -1
	 */
	@Override
	public int compareTo(CardClient o) {
		if(this.color == null) {                          //this une carte non colorée (+4/Joker)
			if(o.color != null) {return -1;}              //o est coloré => this = nonColoré < o = coloré
			else {return this.role.compareTo(o.role);}    //o est également non coloré ==> ordre lexicographique
		}
		else if(o.color == null) {                        //this une carte colorée et o est une carte non colorée (+4/Joker)
			return 1;                                     //this = coloré > o = nonColoré
		}  
		//Les deux cartes sont colré à partir de cette ligne
		else if(this.color.equals(o.color)) {             //Deux cartes de même couleur
			return this.role.compareTo(o.role);           //ordre Lexicographique (0=>9, +2, Inversion, Passer)
		}else {                                           //Deux cartes collorés de couleurs différentes 
			return this.color.compareTo(o.color);         //ordre lexico our les couleurs
		}
	}
	


}
