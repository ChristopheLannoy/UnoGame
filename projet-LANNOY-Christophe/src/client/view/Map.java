package client.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.model.CardClient;
import client.model.Opponent;

public class Map extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<CardClient> hand;
	private ArrayList<Opponent> opponentList;
	private CardClient talon;
	private String information = "";
	private String userInput = "";

	public Map(){
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public void paint(Graphics g) { 
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Dessine la main du joueur
		int size = 0;
		if(hand != null) {
			size = hand.size();
		}
		int cardWidth = (int) Math.round((getWidth()*0.8)/(Math.max(size, 7)));
		int cardHight = (int) Math.round(cardWidth*1.5);
		Image image_map = null;
		for(int x = 0; x < size; x++) {
			int xCard = 20+(x*(cardWidth+(int) Math.round((getWidth()*0.1)/(Math.max(size, 7)))));
			int yCard = (int) Math.round(getHeight()*0.55);
			try {
				image_map = ImageIO.read(new File("card_data/" + hand.get(x).toShortString() + ".png") );
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image_map, xCard, yCard, cardWidth, cardHight, null );
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(x),xCard+(cardWidth/2),yCard + cardHight + 10);
		}
		
		
		//Dessine le talon
		if (talon!=null) {
			try {
				if(talon.toString().split("-")[0].equals("+4") || talon.toString().split("-")[0].equals("Joker")) {
					image_map = ImageIO.read(new File("card_data/" + talon.toString().split("-")[0] + ".png"));
					switch(talon.toString().split("-")[1]) {
					case "rouge":
						g.setColor(Color.RED);
					break;
					case "bleu":
						g.setColor(Color.BLUE);
					break;
					case "vert":
						g.setColor(Color.green);
					break;
					case "jaune":
						g.setColor(Color.YELLOW);
					break;
					}
					g.drawRect((int) Math.round(getWidth() * 0.83)-2,
							   (int) Math.round(getHeight() * 0.2)-2, 64, 94);
					
				}else {
					image_map = ImageIO.read(new File("card_data/" + talon.toShortString() + ".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image_map, (int) Math.round(getWidth() * 0.83), (int) Math.round(getHeight() * 0.2),
					60, 90, null);
			}
		
		//Dessine les adversaires
		int y = (int) Math.round(getHeight()*0.2);
		int noo = 0;  //Number Of Opponent
		if(opponentList != null) {
			noo = opponentList.size();
		}
		int oWidth1 = (int) Math.round((getWidth()*0.8)/(noo+2));
		int oWidth2 = (int) Math.round((getWidth())/(noo+1));

		cardWidth = 40;
		cardHight = 60;
		try {
			image_map = ImageIO.read(new File("card_data/" + "dos" + ".png") );
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(int k = 0; k < noo; k++) {
			int x = 20 + (k*oWidth2);
			g.setColor(Color.BLACK);
			g.drawRect(x, y-7, oWidth2, 100);
			g.drawString(opponentList.get(k).getName(),x + (int) Math.round((oWidth2-oWidth1)/2),y-25);  //draw name of opponent
			int numberOfCard = opponentList.get(k).getNumberOfCard();
			g.drawString(Integer.toString(numberOfCard) +
					" carte(s) en main",x + (int) Math.round((oWidth2-oWidth1)/2),y-15);  //draw name of opponent

			for(int l = 0; l<numberOfCard; l++) {
				int cardSpace = (int) Math.round(oWidth1/numberOfCard);
				g.drawImage(image_map, x+((oWidth2-oWidth1)/2)+(l*cardSpace), y, cardWidth, cardHight, null );
			}
		}
		
		//information/question destiné au joueur
		g.setColor(Color.GREEN);
		g.drawString(information, 20, 20);
		g.drawString(userInput, 20, 30);
	}
	
	public Map getMap(){
		return this;
	}
	
	public void setHand(ArrayList<CardClient> hand){
		this.hand = hand;
	}
	public void setTalon(CardClient talon) {
		this.talon = talon;
	}
	public void setOpponentList(ArrayList<Opponent> opponentList) {
		this.opponentList = opponentList;
	}
	/**
	 * Print the text information on the GUI
	 * @param information
	 */
	public void informUser(String information) {
		this.information = information;
		this.repaint();
	}
	/**
	 * Print the text userInput on the GUI
	 * @param information
	 */
	public void setUserInput(String userInput) {
		this.userInput = userInput;
		this.repaint();
	}
	
	public void redraw(){
		this.repaint();
	}	
}