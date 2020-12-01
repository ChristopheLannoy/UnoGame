package server;

import java.io.IOException;

public class ServerMain {
	
	public static final double MAX_SCORE = 500;
	public static final int PORT = 6789;

	public static void main(String[] args) throws IOException {
		Server server = new Server(PORT);
		server.acceptPlayers();
		Thread refusePlayer = new Thread(new RefusePlayer(server.getServerSocket()));
		refusePlayer.start();  //Refuse other player
		server.startGame();
		refusePlayer.stop(); //bof ?
	}
}
