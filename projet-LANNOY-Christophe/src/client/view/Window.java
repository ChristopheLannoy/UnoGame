package client.view;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;

import client.controller.Keyboard;
import client.model.CardClient;
import client.model.Opponent;


public class Window {
	private Map map = new Map();
	private Keyboard keyboard;

	public Window(String name){	    
	    JFrame window = new JFrame(name);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setAlwaysOnTop(true); //a verifier
	    window.setBounds(0, 0, 600, 400); //donne la taille
	    window.getContentPane().setBackground(Color.gray);
	    window.getContentPane().add(this.map);
	    window.setVisible(true);
	}	
	
	public void setHand(ArrayList<CardClient> hand){
		this.map.setHand(hand);
		this.map.redraw();
	}
	public void setTalon(CardClient talon){
		this.map.setTalon(talon);
		this.map.redraw();
	}
	public void setOpponentList(ArrayList<Opponent> opponentList) {
		this.map.setOpponentList(opponentList);
		this.map.redraw();
	}
	public void update(){
		this.map.redraw();
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setKeyListener(KeyListener keyboard){
	    this.map.addKeyListener(keyboard);
	    this.keyboard = (Keyboard)keyboard;
	}
	
	public void informUser(String s) {
		map.informUser(s);
	}
	/**
	 * ask the user the String 'question' through the GUI 
	 * and get String response through the keyboard
	 * @param question to be ask to user
	 * @return String response from user
	 */
	public String askUser(String question) {
		map.informUser(question);             //graphically ask
		return keyboard.askUser();               //get input on keyboard
	}
	
	
}
