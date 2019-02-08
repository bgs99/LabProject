import bgs99c.shared.Config;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server implements Closeable{
	private DBManager loginManager;
	private ThreadPoolExecutor executor = 
			  (ThreadPoolExecutor) Executors.newCachedThreadPool();
	private ServerSocket serverSocket;

	public Server() {
		loginManager = new DBManager();
	}

	public void init() {
		int port = Config.getPort();
		try {
			serverSocket = new ServerSocket(port);
		} catch(Exception e) {
			System.err.println(String.format("Can't start server at port %d:", port));
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println(String.format("Server is listening on port %d", port));
	}

	public void run() throws IOException {
		while (true) {
			Socket sock = serverSocket.accept();

			Session sess = new Session(loginManager, sock);
            sess.init();
			executor.submit(sess);
		}
	}

	@Override
	public void close() throws IOException {
		serverSocket.close();
	}
}
