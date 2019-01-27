import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bgs99c.shared.Security;
import bgs99c.shared.UserStats;

public class DBManager {
	Connection conn;
	public Timestamp lastTournament() throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("Select MAX(date) from tournaments");
		rs.next();
		return rs.getTimestamp(1);
	}
	public UserStats[] getUsers() throws SQLException {
		Timestamp last = lastTournament();
		PreparedStatement st = conn.prepareStatement("SELECT name, AVG(ta.position), tb.position" +
				" FROM login LEFT JOIN tournaments as ta on login.id = player" +
				" left join (select player, position FROM tournaments where date = ?) as tb" +
				" on login.id = tb.player" +
				" group by login.id, name, tb.position");
		st.setTimestamp(1, last);
		ResultSet rs = st.executeQuery();
		List<UserStats> ls = new ArrayList<>();
		while(rs.next()) {
			ls.add(new UserStats(rs.getString(1), rs.getInt(3), rs.getInt(2), 0, Loader.teamInfo(rs.getString(1))));
		}
		
		return ls.toArray(new UserStats[] {});
	}
	public void recordTournament(List<String> players) throws SQLException {
		Timestamp ts = new Timestamp((new java.util.Date()).getTime());
		for(int i = 0; i < players.size(); i++) {
			PreparedStatement st = conn.prepareStatement("INSERT INTO tournaments(date, player, position) VALUES (?, (SELECT id FROM login WHERE name = ?), ?)");
			st.setTimestamp(1, ts);
			st.setString(2, players.get(i));
			st.setInt(3, players.size()- i);
		}
	}
	private String localCon = "jdbc:postgresql:studs";
	private String remoteCon = "jdbc:postgresql://pg/studs";
	public DBManager() throws SQLException{
		PrintStream err = System.err;
		try {
			System.setErr(null);
			conn = DriverManager.getConnection(localCon);
		} catch (SQLException e) {
			System.out.println("Local DB not found, trying to connect to host pg...");
			conn = DriverManager.getConnection(remoteCon, "s242322", "waw657");
		} finally {
			System.setErr(err);
		}

	}
	public boolean checkPassword(String name, String password, byte sessionSalt) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT password FROM login WHERE name = '"+name + "'");
		rs.next();
		String spass = rs.getString(1);
		
		rs.close();
		st.close();
		
		return Security.saltHashString(spass, sessionSalt).equals(password);
	}
	public boolean addUser(String name, String password) throws SQLException {
		byte salt = Security.createSalt();
		String pass = Security.saltUTFString(password, salt);
		Statement st = conn.createStatement();
		try {
			st.executeUpdate("INSERT INTO login VALUES ('"+name+"','"+pass+"',"+salt+")");
		}catch (SQLException e) {
			st.close();
			return false;
		}
		st.close();
		return true;
	}
	public byte getSalt(String name) throws SQLException {
		PreparedStatement st = conn.prepareStatement("SELECT salt FROM login WHERE name = ?");
		st.setString(1, name);
		ResultSet rs = st.executeQuery();
		if(!rs.next()) {
			rs.close();
			st.close();
			throw new SQLException();
		}
		byte res = rs.getByte(1);
		rs.close();
		st.close();
		return res;
	}
}
