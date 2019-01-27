import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bgs99c.shared.Protocol;
import bgs99c.shared.Security;
import bgs99c.shared.UserStats;
public final class Session implements Closeable, Runnable{
	private Socket connectionSocket;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private DBManager lm;
	
	private String name = null;
	
	private Game game = null;
	
	public Session(DBManager loginManager, Socket sock ) {
		lm = loginManager;
		connectionSocket = sock;
	}
	
	public boolean login() throws IOException, SQLException {
		System.out.println("Login attempt");
		String name = ois.readUTF();
		System.out.println("Name provided: " + name);
		byte salt = 0;
		/*try {
			salt = lm.getSalt(name);
		} catch (Exception e) {
			oos.write(1);
			oos.flush();
			return false;
		}*/
		oos.write(0);
		oos.write(salt);
		byte sessionSalt = Security.createSalt();
		oos.write(sessionSalt);
		oos.flush();
		String pass = ois.readUTF();
		boolean success = true;//TODO fix it back//lm.checkPassword(name, pass, sessionSalt);
		oos.write(success? 0 : 1);
		oos.flush();
		this.name = name;
		return success;
	}
	
	public void readFile(String fileName) throws IOException {
        OutputStream output = new FileOutputStream(Loader.PD + fileName);   
        long size = ois.readLong();   
        byte[] buffer = new byte[1024];   
        int bytesRead;
		while (size > 0 && (bytesRead = ois.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)   
        {   
            output.write(buffer, 0, bytesRead);   
            size -= bytesRead;   
        }
        output.close();
        Loader.load(fileName);
        Game.updatePlayer(fileName.substring(0, fileName.indexOf('.')));
	}
	
	public void init() throws IOException {
		InputStream input = connectionSocket.getInputStream();
		OutputStream output = connectionSocket.getOutputStream();
		oos = new ObjectOutputStream(output);
		oos.flush();
		ois = new ObjectInputStream(input);
	}
	
	public void acceptFeedback() throws IOException {
		System.out.println("*** " + ois.readUTF());
		System.out.println("*** " + name);
	}
	
	@Override
	public void close() throws IOException {
		oos.close();
		connectionSocket.close();
	}
	
	public synchronized void makeTournament() throws IOException, SQLException {
		List<String> ranks = game.Tournament();
		oos.writeObject(Game.getPlayers());
		oos.writeObject(ranks);
		oos.writeObject(Game.getLogs());
		oos.flush();
		Game.clearLogs();
	}
	public synchronized void makeBattle(String opponent) throws IOException, SQLException {
		String winner = game.Battle(this.name, opponent);
		List<String> ps = new ArrayList<String>();
		ps.add(name);
		ps.add(opponent);
		oos.writeObject(ps);
		List<String> rs = new ArrayList<String>();
		rs.add(name.equals(winner)? opponent : winner);
		rs.add(winner);
		oos.writeObject(rs);
		oos.writeObject(Game.getLogs());
		oos.flush();
		Game.clearLogs();
	}
	@Override
	public void run() {
		try {
			while(!login());
				System.out.println("User " + name + " logged in");
			UserStats[] users = lm.getUsers();
			game = new Game(users);
			
			while(true) {
				Protocol action = Protocol.fromInt(ois.read());
				switch(action) {
					case STATS:
						users = lm.getUsers();
						sendStats(users);
						break;
					case NONE:
						break;
					case SEND_FILE:
						readFile(name+".jar");
						break;
					case FEEDBACK:
						acceptFeedback();
						break;
					case BATTLE:
						String opponent = ois.readUTF();
						makeBattle(opponent);
						break;
					case TOURNAMENT:
						makeTournament();
						break;
					case STATUS:
						oos.write(0);
						oos.flush();
					default:
						break;
				}
			}
		} catch (IOException | SQLException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void sendStats(UserStats[] users) throws IOException {
		System.out.println("Sending stats");
		oos.write(users.length);
		oos.flush();
		for(UserStats user : users) {
			oos.writeObject(user);
		}
		System.out.println("Stats are sent");
	}
}
