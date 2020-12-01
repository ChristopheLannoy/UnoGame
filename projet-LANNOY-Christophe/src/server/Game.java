package server;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
	
	private ArrayList<Player> playerList;
	
	public Game(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}
	
	public void start() throws IOException {
		while(maxScore(playerList) < ServerMain.MAX_SCORE) {
			System.out.println("NEW ROUND");
			Round round = new Round(playerList);
			round.startRound();
			displayScores();
		}
		endGame();
	}
	
	/**
	 * Send the final sore of the game to all the players
	 */
	public void endGame() {
		String gameScore = "fin-de-partie";
		for (Player player: playerList) {
			gameScore += " " + player.getName();
			gameScore += " " + player.getScore();			
		}
		for(Player player: playerList) {
			player.send(gameScore);
		}
	}
	/**
	 * Print on the console the score of every player
	 */
	public void displayScores() {
		for(Player player: playerList) {
			System.out.println("Player " + player.getName() + " have a score of: " + player.getScore());
		}
		
	}
	/**
	 * Return the maximum of the score of all the player in playerList
	 * (usfull to check the end of the game)
	 * @param playerList
	 * @return int maximum score
	 */
	public int maxScore(ArrayList<Player> playerList) {
		int maxScore = 0;
		for (Player player: playerList) {
			if(player.getScore() > maxScore) {
				maxScore = player.getScore();
			}
		}
		return maxScore;
	}

}
