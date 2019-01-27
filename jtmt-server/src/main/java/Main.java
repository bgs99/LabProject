public class Main {

	public static void main(String[] args) {
		try {
			Loader.loadAll();
			Loader.teamInfo("s242322");
			Server server = new Server();
			server.init();
			server.run();
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
