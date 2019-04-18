import bgs99c.lab2.Battle;

public class Main {

	public static void main(String[] args) {
		try {
			Loader.loadAll();
			Server server = new Server();
			server.init();
			server.run();
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
