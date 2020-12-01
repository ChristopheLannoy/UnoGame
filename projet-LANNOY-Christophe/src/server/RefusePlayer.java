package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Thread that will send a error message to all the new players
 * @author 32474
 *
 */
public class RefusePlayer implements Runnable{
	ServerSocket serverSocket;
	public RefusePlayer(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		Socket clientSocket;
		try {
			clientSocket = serverSocket.accept();
			Player player = new Player(clientSocket);
			player.send("Erreur : plus de place");
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
