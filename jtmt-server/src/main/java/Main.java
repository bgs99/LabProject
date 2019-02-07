public class Main {

	public static void main(String[] args) {
		try {
			System.out.println("Init");
			System.out.println("Init");
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
