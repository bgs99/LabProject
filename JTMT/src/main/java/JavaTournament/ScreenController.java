package JavaTournament;

import bgs99c.client.*;
import bgs99c.shared.UserStats;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


 class ScreenController {

    ScreenController(Client client, JFrame frame) {

        Background panel1 = new Background(client, frame);
        frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.MAIN_PAGE_SIZE));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }






}


class   Background extends JPanel {


    private JFrame frame;
    private Client client;

    private JLabel connectionStatusLabel ;
     Background (Client client, JFrame frame) {
        this.frame = frame;
        this.client = client;


         JLabel orLabel = new JLabel();
         JButton battleButton = new JButton();
         JButton tournamentLabel = new JButton();
        connectionStatusLabel = new JLabel();
         JButton disconnectButton = new JButton();
         JLabel yourStatsLabel = new JLabel();
         JLabel stat1Label = new JLabel();
         JLabel stat2Label = new JLabel();
         JLabel stat3Label = new JLabel();
         JLabel youcanstartLabel = new JLabel();
        Font CustomFont;
        Font customFont18 = null;
        try {

            URL resource  = ScreenController.class.getResource("/beermoney.ttf");
            String TTFpath = Paths.get(resource.toURI()).toString();
            CustomFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(TTFpath));
            customFont18 = CustomFont.deriveFont(19.0F); //11 шрифт

        }
        catch (FontFormatException | IOException | URISyntaxException e2) 	 {e2.printStackTrace();}

         orLabel.setFont(customFont18);
        battleButton.setFont(customFont18);
        tournamentLabel.setFont(customFont18);
        connectionStatusLabel.setFont(customFont18);
        disconnectButton.setFont(customFont18);
        youcanstartLabel.setFont(customFont18);
        yourStatsLabel.setFont(customFont18);
        stat1Label.setFont(customFont18);
        stat2Label.setFont(customFont18);
        stat3Label.setFont(customFont18);
        stat1Label.setForeground(new Color(209,209,202));
        stat2Label.setForeground(new Color(209,209,202));
        stat3Label.setForeground(new Color(209,209,202));




        setPreferredSize(new Dimension(800, 560));

        orLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orLabel.setText("or");
        orLabel.setForeground(new Color(209,209,202));


        battleButton.setText("Battle");
        battleButton.setBackground(new Color(128, 12, 12));
        battleButton.setForeground(new Color(209,209,202));

        battleButton.addActionListener(actionEvent -> new SelectOpponentController(frame,client));

        tournamentLabel.setText("Tournament");
        tournamentLabel.setBackground(new Color(128, 12, 12));
        tournamentLabel.setForeground(new Color(209,209,202));

        connectionStatusLabel.setText("Connected as " + client.name);
        connectionStatusLabel.setFont(customFont18);
        connectionStatusLabel.setForeground(new Color(209,209,202));


        disconnectButton.setText("Disconnect");
        disconnectButton.setBackground(new Color(128, 12, 12));
        disconnectButton.setForeground(new Color(209,209,202));
        disconnectButton.addActionListener(this::disconnect);

        yourStatsLabel.setText("Your stats:");
        yourStatsLabel.setForeground(new Color(209,209,202));

        youcanstartLabel.setText("You can start a");
        youcanstartLabel.setForeground(new Color(209,209,202));
        youcanstartLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        try {
            stat1Label.setText("rank: " + client.getStats()[0].lastpos);
            stat2Label.setText("score: " + client.getStats()[0].score);
            stat3Label.setText("avgrank: " + client.getStats()[0].avgpos);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


         GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(orLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(youcanstartLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(battleButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(tournamentLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(connectionStatusLabel)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 520, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(stat1Label)
                                                        .addGap(100, 100, 100))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(stat2Label)
                                                                .addComponent(stat3Label))
                                                        .addContainerGap()))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(yourStatsLabel)
                                                .addGap(30, 30, 30))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(disconnectButton)
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(youcanstartLabel)
                                        .addComponent(yourStatsLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(battleButton)
                                        .addComponent(stat1Label))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(orLabel)
                                        .addComponent(stat2Label))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tournamentLabel)
                                        .addComponent(stat3Label))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(connectionStatusLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(disconnectButton)))
        );
        AudioController.startAudio("Main_menu_track.wav");


        setPreferredSize(ScreenUtil.MAIN_PAGE_SIZE);


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


    public void paintComponent(Graphics g) {
        try {

            URL resource = ScreenController.class.getResource("/new.png");
            Image image = ImageIO.read(Paths.get(resource.toURI()).toFile());

            g.drawImage(image,0,0,null);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private void updateConnectionStatus() {
        frame.repaint();
        connectionStatusLabel.setText(client.checkConnection() ? "Connected as " + client.name : "Disconnected");
    }

    private void disconnect(ActionEvent event){
        try {
            client.close();
            AudioController.stopAudio();
            frame.dispose();
            MainController mainController = new MainController(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

class SelectOpponentController extends JPanel{
   private  JFrame frame;
   private Client client;
   private JPanel oldPanel;
    private String selectedPlayer = null;
    private PlayerTableModel data = new PlayerTableModel();
    SelectOpponentController(JFrame frame, Client client){
        this.frame = frame;

        AudioController.stopAudio();
        oldPanel = (JPanel) frame.getContentPane();
        this.client = client;
        setPreferredSize(new Dimension(ScreenUtil.SELECT_OPPONENT_PAGE_SIZE));
        frame.setContentPane(this);
        setLocation(ScreenUtil.getCenterPoint(ScreenUtil.SELECT_OPPONENT_PAGE_SIZE));
        frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.SELECT_OPPONENT_PAGE_SIZE));
        frame.pack();
        JScrollPane jScrollPane2 = new JScrollPane();
        JButton battleButton = new JButton();
        JButton returnButton = new JButton();

        setBackground(new java.awt.Color(128, 12, 12));
        JLabel chooseLabel = new JLabel();

        chooseLabel.setFont(ScreenUtil.getBeermoneyFont(22.0f));
        chooseLabel.setForeground(new java.awt.Color(209, 202, 202));
        chooseLabel.setText("Choose your opponent!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(battleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chooseLabel)
                                .addGap(325, 325, 325))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(174, 174, 174)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(174, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(chooseLabel)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(battleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59))
        );



        JTable jTable1 = new JTable(data);
        jTable1.setBackground(new java.awt.Color(128, 12, 12));
        jTable1.setForeground(new Color(209,202,202));
        jTable1.setBorder(new javax.swing.border.MatteBorder(null));
        jTable1.setFont(ScreenUtil.getBeermoneyFont(18.0f)); // NOI18N
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int[] selectedRows = jTable1.getSelectedRows();
                if (selectedRows.length == 0) {
                    battleButton.setEnabled(false);
                    return;
                }
                int i = selectedRows[0];
                selectedPlayer = data.getRow(i).getName();
                battleButton.setEnabled(!selectedPlayer.equals(client.name));
                //TODO fill detailsList
            }
        });
        jScrollPane2.setViewportView(jTable1);
        battleButton.setBackground(new java.awt.Color(128, 12, 12));
        battleButton.setForeground(new java.awt.Color(209, 202, 202));
        battleButton.setFont(ScreenUtil.getBeermoneyFont(18.0f));
        battleButton.setText("Battle");

        returnButton.setBackground(new java.awt.Color(128, 12, 12));
        returnButton.setFont(ScreenUtil.getBeermoneyFont(18.0f));
        returnButton.setForeground(new java.awt.Color(209, 202, 202));
        returnButton.setText("Return");
        battleButton.setEnabled(false);
        battleButton.addActionListener(this::startBattle);
        returnButton.addActionListener(this::returnToMain);
        UserStats[] stats = new UserStats[0];
        try {
            stats = client.getStats();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(UserStats stat : stats) {

            data.addRow(new PlayerModel(stat.name, stat.lastpos, stat.score, stat.avgpos, stat.fighters));
        }
    }// </editor-fold>

    public void paintComponent(Graphics g) {
        try {

            URL resource = ScreenController.class.getResource("/Choosing_opponent1.png");
            Image image = ImageIO.read(Paths.get(resource.toURI()).toFile());

            g.drawImage(image, 0, 0, null);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
    private void startBattle(ActionEvent event) {
        try {

            int song =  (int)(Math.round((Math.random()*3+1)));
            AudioController.startAudio( song + "_version.wav");



            BattleInfo battleInfo = client.declareBattle(selectedPlayer);
            new BattleController(battleInfo, frame);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void returnToMain(ActionEvent event){
        frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.MAIN_PAGE_SIZE));
        frame.setContentPane(oldPanel);
        frame.pack();
        AudioController.startAudio("Main_menu_track.wav");

    }


}

