import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server implements Closeable{
	DBManager loginManager;
	private ThreadPoolExecutor executor = 
			  (ThreadPoolExecutor) Executors.newCachedThreadPool();
	private ServerSocket serverSocket;
	public Server() throws SQLException {
		loginManager = new DBManager();
	}
	public void init() {
		try {
			serverSocket = new ServerSocket(18888);
		} catch(Exception e) {
			System.out.println("Can't start server at port 18888:");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Server is listening on port 18888");
	}
	public void run() throws IOException {
		while(true) {
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
