package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.view.Window;

public class Keyboard implements KeyListener{
	private Object lock;
	private Window window;
	private String userInput = "";
	
	public Keyboard(Window window){ //constructeur
		this.window = window;
		lock= new Object();
		synchronized (lock) {
		}
	}
	
	/**
	 * get an input string from the user through GUI
	 * @return String
	 */
	public String askUser() {
		userInput = "";
		window.getMap().setUserInput(userInput);
		synchronized (lock) {
			try {
				lock.wait();       //attends que l'utilisateur appuie sur "enter" ==> lock.notify()
			} catch (InterruptedException e) {}
		}
		window.getMap().setUserInput(""); //Une fois l'entrée envoyée on l'efface de l'écran
		return userInput;
	}
	
	
	public void keyPressed(KeyEvent event) {
		synchronized (lock) {
			int key = event.getKeyCode(); //Returns the integer keyCode associated with the key in this event.
			switch (key) {

			case KeyEvent.VK_Y:
				userInput += "y";
				break;
			case KeyEvent.VK_N:
				userInput += "n";
				break;
			case KeyEvent.VK_R:
				userInput += "r";
				break;
			case KeyEvent.VK_V:
				userInput += "v";
				break;
			case KeyEvent.VK_B:
				userInput += "b";
				break;
			case KeyEvent.VK_J:
				userInput += "j";
				break;
			case KeyEvent.VK_0:
				userInput += "0";
				break;
			case KeyEvent.VK_1:
				userInput += "1";
				break;
			case KeyEvent.VK_2:
				userInput += "2";
				break;
			case KeyEvent.VK_3:
				userInput += "3";
				break;
			case KeyEvent.VK_4:
				userInput += "4";
				break;
			case KeyEvent.VK_5:
				userInput += "5";
				break;
			case KeyEvent.VK_6:
				userInput += "6";
				break;
			case KeyEvent.VK_7:
				userInput += "7";
				break;
			case KeyEvent.VK_8:
				userInput += "8";
				break;
			case KeyEvent.VK_9:
				userInput += "9";
				break;
				
			case KeyEvent.VK_BACK_SPACE:
				if(userInput.length() > 0) {
					userInput = userInput.substring(0, userInput.length()-1);
				}
				break;
			
			case KeyEvent.VK_ENTER:
				lock.notify();  //libère la méthode askUser()
				break;
			}
			window.getMap().setUserInput(userInput);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
