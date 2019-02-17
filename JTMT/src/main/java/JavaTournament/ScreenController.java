package JavaTournament;

import bgs99c.client.*;
import bgs99c.shared.UserStats;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.awt.GridBagConstraints.*;


public class ScreenController {

    private Client client;
    private JFrame frame;
    private Label teamName;
    private Button tour;
    private Button battleBtn;
    private final int LOGINPAGE_HEIGHT = 530;
    private final int LOGINPAGE_WIDTH = 800;
	/*private final ObservableList<PlayerModel> data =
	        FXCollections.observableArrayList();*/
	ScreenController(Client client, JFrame frame) throws URISyntaxException {
		this.client = client;
		this.frame = frame;
        Background panel1 = new Background(client, frame);

        //Костыль, у меня в Ubuntu почему-то Java считает что 2 экрана, потом потесчу на винде
        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] devices = environment.getScreenDevices();
            DisplayMode dmode = devices[0].getDisplayMode();
            int screenWidth = dmode.getWidth();
            int screenHeight = dmode.getHeight();


        frame.setPreferredSize(new Dimension(LOGINPAGE_WIDTH, LOGINPAGE_HEIGHT));
        Dimension screenSize = new Dimension(screenWidth, screenHeight);
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - ( LOGINPAGE_WIDTH / 2),
                middle.y - (LOGINPAGE_HEIGHT / 2 ) );
        frame.setLocation(newLocation);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.pack();
        frame.setVisible(true);

      	}


	
	public void selectFile() throws IOException {
		/*FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Java libraries", "*.jar"));
		File file = fileChooser.showOpenDialog(window);
		if(file==null)
			return;
		client.sendFile(file);*/
	}
	

	
}


class Background extends JPanel {
private JLabel connectionStatus;
    private Label teamName;
    private Button tour;
    AudioStream s = null;
    private Button battleBtn;
    private JFrame frame;

    private Client client;

    private String selectedPlayer = null;

    private PlayerTableModel data = new PlayerTableModel();

    public Background (Client client, JFrame frame) {
        this.frame = frame;
        this.client = client;
        JTable table = new JTable(data);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0) {
                    battleBtn.setEnabled(false);
                    return;
                }
                int i = selectedRows[0];
                selectedPlayer = data.getRow(i).getName();
                battleBtn.setEnabled(!selectedPlayer.equals(client.name));
                teamName.setText("Team " + selectedPlayer);
                //TODO fill detailsList
            }
        });
        JButton battle;
        JButton tournament;
       JLabel connectionStatus;
        JLabel orLabel;
       JButton disconnect = new JButton();
       JLabel youcanstartLabel;
     JList jList1;
      JScrollPane jScrollPane1;


        battle = new javax.swing.JButton();
        tournament = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        connectionStatus = new javax.swing.JLabel();
        orLabel = new javax.swing.JLabel();
        youcanstartLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 500));

        battle.setBackground(new java.awt.Color(128, 0, 0));
        battle.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        battle.setForeground(new java.awt.Color(255, 248, 220));
        battle.setText("Battle");
        disconnect.setBackground(new java.awt.Color(128, 0, 0));
     disconnect.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        disconnect.setForeground(new java.awt.Color(255, 248, 220));
        disconnect.setText("Disconnect");
//        battle.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                battleActionPerformed(evt);
//            }
//        });

        tournament.setBackground(new java.awt.Color(128, 0, 0));
        tournament.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        tournament.setForeground(new java.awt.Color(255, 248, 220));
        tournament.setText("Tournament");
//        tournament.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                tournamentActionPerformed(evt);
//            }
//        });

        jList1.setBackground(new java.awt.Color(128, 0, 0));
        jList1.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        jList1.setForeground(new java.awt.Color(255, 248, 220));
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setToolTipText("");
        jScrollPane1.setViewportView(jList1);

        connectionStatus.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        connectionStatus.setForeground(new java.awt.Color(255, 248, 220));
        connectionStatus.setText("Connected as " + client.name);

        orLabel.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        orLabel.setForeground(new java.awt.Color(255, 248, 220));
        orLabel.setText("or");

//        jLabel3.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
//        jLabel3.setForeground(new java.awt.Color(255, 248, 220));
//        jLabel3.setText("or");

        youcanstartLabel.setFont(new java.awt.Font("Purisa", 0, 11)); // NOI18N
        youcanstartLabel.setForeground(new java.awt.Color(255, 248, 220));
        youcanstartLabel.setText("You can start a");

        disconnect.addActionListener(this::disconnect);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(youcanstartLabel)
                                                        .addComponent(tournament)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addComponent(connectionStatus))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addComponent(orLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(54, 54, 54)
                                                .addComponent(battle)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 511, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(disconnect))
                                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(youcanstartLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(battle)

                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(orLabel)
                                                .addGap(1, 1, 1)
                                                .addComponent(tournament)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 305, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(disconnect)
                                        .addGap(33,33,33)
                                        .addComponent(connectionStatus)))
        );


        ///
        URL resource = ScreenController.class.getResource("/Main_menu_track.wav");


        try {
            s = new AudioStream(new FileInputStream(Paths.get(resource.toURI()).toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        AudioData MD;
        AudioPlayer.player.start(s);
        ///


//
//        add(table, c);
//        teamName = new Label("");
//        add(teamName, c);
//        battleBtn = new Button("Battle");
//        battleBtn.setEnabled(false);
//        battleBtn.addActionListener(this::startBattle);
//      add(battleBtn, c);
//
//        tour = new Button("Tournament");
//        tour.addActionListener(this::startTournament);
//       add(tour, c);
//
//        connectionStatus = new Label("Connected");
//        add(connectionStatus, c);

        UserStats[] stats = new UserStats[0];
        try {
            stats = client.getStats();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(UserStats stat : stats) {
            data.addRow(new PlayerModel(stat.name, stat.lastpos, stat.score, stat.avgpos, stat.fighters));
        }
        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateConnectionStatus, 2, 2, TimeUnit.SECONDS);



    }
    private void startTournament(ActionEvent event){
        try {
            BattleInfo battleInfo = client.declareTournament();
            new BattleController(battleInfo, frame);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startBattle(ActionEvent event) {
        try {
            BattleInfo battleInfo = client.declareBattle(selectedPlayer);
            new BattleController(battleInfo, frame);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void paintComponent(Graphics g) {
        try {
            GraphicsEnvironment environment =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();

            GraphicsDevice[] devices = environment.getScreenDevices();
            DisplayMode dmode = devices[0].getDisplayMode();
            int screenWidth = dmode.getWidth();
            int screenHeight = dmode.getHeight();
            URL resource = ScreenController.class.getResource("/new.png");
          Image image = ImageIO.read(Paths.get(resource.toURI()).toFile());
//            URL resource2 = ScreenController.class.getResource("/bg.png");
//            Image bg = ImageIO.read(Paths.get(resource2.toURI()).toFile());
            g.drawImage(image,0,0,null);
//            g.drawImage(image, 0,0, null);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private void updateConnectionStatus() {
//TODO: Мб сделать только когда меняется значение чекКонекшена? И нужна ли эта строка вообще
         frame.repaint();
       connectionStatus.setText(client.checkConnection() ? "Connected as " + client.name : "Disconnected");
    }

    private void disconnect(ActionEvent event){
        try {
            client.close();
            AudioPlayer.player.stop(s);
            frame.dispose();
            MainController mainController = new MainController(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}