package bgs99c.client;

import bgs99c.lab2.Record;
import bgs99c.shared.*;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.jcraft.jsch.*;

public class Client implements Closeable {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket socket = null;
	private Lock lock = new ReentrantLock();
	public String name;

	@Override
	public void close() throws IOException {
		if(oos != null && ois != null ) {
			oos.close();
			ois.close();
		}
		if(socket != null)
			socket.close();
	}

	public boolean init(String name, String pass) throws IOException {
		InputStream input;
		OutputStream output;
		Config config = new Config();

		socket = new Socket(config.getHost(), config.getPort());
		input = socket.getInputStream();
		output = socket.getOutputStream();

		oos = new ObjectOutputStream(output);
		oos.flush();
		ois = new ObjectInputStream(input);


		return true;
	}

	public boolean checkConnection() {
		if (lock.tryLock()) {
		    try {
		    	useProtocol(Protocol.STATUS);
				oos.flush();
				return ois.read() == 0;
			} catch (IOException e) {
				return false;
			}
		    finally {
		    	lock.unlock();
		    }
		}
		else 
			return false;
	}

	public synchronized boolean login(String name, String pass) throws IOException {
		oos.writeUTF(name);
		oos.flush();
		int succ = ois.read();
		if(succ != 0) return false;
		byte salt = (byte)ois.read();
		byte sessionSalt = (byte)ois.read();
		String hashedPass = Security.saltUTFString(pass, salt);
		
		System.out.print("1");
		
		String saltedPass = Security.saltHashString(hashedPass, sessionSalt);

		System.out.print("2");
		oos.writeUTF(saltedPass);
		oos.flush();
		succ = ois.read();
		if(succ == 0) {
			System.out.println("Login successful");
			this.name = name;
		}
		return succ == 0;
	}

	public UserStats[] getStats() throws IOException, ClassNotFoundException {
		lock.lock();
		try {
			useProtocol(Protocol.STATS);
			int len = ois.read();
			UserStats[] res = new UserStats[len];
			for(int i = 0; i < len; i++) {
				res[i] = (UserStats) ois.readObject();
			}
			return res;
		} finally {
			lock.unlock();
		}
	}

    @SuppressWarnings("unchecked")
	public BattleInfo declareTournament() throws IOException, ClassNotFoundException {
		lock.lock();
		try {
			useProtocol(Protocol.TOURNAMENT);
			oos.flush();

			// TODO: Not sure how exactly it should work, maybe change this
            List<String> players = (List<String>)ois.readObject();
            List<String> ranks = (List<String>)ois.readObject();
            List<Record> records = (List<Record>) ois.readObject();
            return new BattleInfo(players, ranks, records);
		} finally {
			lock.unlock();
		}
	}

    @SuppressWarnings("unchecked")
	public BattleInfo declareBattle(String selectedPlayer) throws IOException, ClassNotFoundException {
		lock.lock();
		try {
			useProtocol(Protocol.BATTLE);
			oos.writeUTF(selectedPlayer);
			oos.flush();

			List<String> players = (List<String>)ois.readObject();
			List<String> ranks = (List<String>)ois.readObject();
			List<Record> records = (List<Record>) ois.readObject();
			return new BattleInfo(players, ranks, records);
		} finally {
			lock.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getPlayers() throws IOException, ClassNotFoundException {
		lock.lock();
		try {
			return (List<String>)ois.readObject();
		} finally {
			lock.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getRanks() throws IOException, ClassNotFoundException {
		lock.lock();
		try {
			return (List<String>)ois.readObject();
		} finally {
			lock.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Record> getResults() throws IOException, ClassNotFoundException {
		lock.lock();
		try {
			return (List<Record>) ois.readObject();
		} finally {
			lock.unlock();
		}
	}

	public void sendFile(File file) throws IOException {
        lock.lock();
		try {
			useProtocol(Protocol.SEND_FILE);
			byte[] contents = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
			dis.readFully(contents, 0, contents.length);
			dis.close();
			oos.writeLong(contents.length);
			oos.write(contents, 0, contents.length);
			oos.flush();
		} finally {
			lock.unlock();
		} 
	}

	private void useProtocol(Protocol protocol) throws IOException {
		oos.write(protocol.ordinal());
		oos.flush();
	}
}
