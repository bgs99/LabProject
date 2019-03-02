package JavaTournament;

import bgs99c.client.Client;

public class ManyClientTest {
    private static volatile boolean enableConnection = false;
    private static volatile int clientId = 0;
    private static volatile int clientsFailed = 0;
    private static volatile int clientsSucceeded = 0;

    public static void main(String[] args) {
        int CLIENT_COUNT = 100;

        for (int i = 0; i < CLIENT_COUNT; i++) {
            Thread t = new Thread(ManyClientTest::processClient);
            t.start();
        }
        enableConnection = true;

        while (clientsFailed + clientsSucceeded < CLIENT_COUNT)
            Thread.yield();

        System.out.println("Total " + clientsSucceeded + " finished successfully.");
        System.out.println("Total " + clientsFailed + " failed.");
    }

    private static void processClient() {
        int curClient = clientId;
        clientId += 1;

        while (!enableConnection) {
            System.out.println("Client " + curClient + " is waiting...");
            try {
                Thread.sleep(0);
            }
            catch (Exception e) {}
        }

        System.out.println("Client " + curClient + " started connecting.");
        Client client = new Client();
        try {
            System.out.println("Client " + curClient + " started init.");
            client.init("s242322", "waw657");
            System.out.println("Client " + curClient + " started login.");
            client.login("s242322", "waw657");
            System.out.println("Client " + curClient + " declared battle.");
            client.declareBattle("Player4");
            clientsSucceeded += 1;
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Client " + curClient + " FAILED TO CONNECT!");
            StackTraceElement frame = e.getStackTrace()[e.getStackTrace().length - 2];
            System.out.println(frame.getFileName() + ":" + frame.getLineNumber());
            clientsFailed += 1;
        }

        System.out.println("Client " + curClient + " finished.");
    }

}
