package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	
	private ServerSocket serverSocket;
	private ArrayList<Player> playerList;
	
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Server ready on port: " + serverSocket.getLocalPort());
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public void acceptPlayers() throws IOException {
		playerList = new ArrayList<Player>();
		int numberOfPlayer = askNumberOfPlayer();
		for (int i = 0; i < numberOfPlayer; i++) {
			Socket clientSocket = serverSocket.accept();
			playerList.add(0,new Player(clientSocket));
			playerList.get(0).send("bienvenue");
			System.out.println("New player with client port: " + clientSocket.getPort());
		}
	}
	
	/**
	 * Ask to the user (server) how many players will play
	 * @return int the number of player
	 */
	public int askNumberOfPlayer() {
		Scanner sc = new Scanner(System.in);
		int numberPlayer = 0;
		while (numberPlayer == 0) {
			try {
				System.out.printf("Nombre de joueur ?: ");
				numberPlayer = Integer.parseInt(sc.nextLine());
				if(numberPlayer < 2) {
					numberPlayer = 0;
				}
			} catch (NumberFormatException e) {} 
		}
		sc.close();
		System.out.println("En attente des joueurs...");
		return numberPlayer;
	}
	public void startGame() throws IOException {
		Game game = new Game(playerList);
		game.start();
	}
	
	public void close() throws IOException {
		serverSocket.close();
	}

}
