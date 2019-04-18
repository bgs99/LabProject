package JavaTournament;

import bgs99c.client.BattleInfo;
import bgs99c.client.Client;
import bgs99c.shared.UserStats;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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

        JButton tournamentButton = new JButton("Tournament");
        JButton downloadButton = new JButton("Upload Jar");
        JLabel orLabel = new JLabel();
        JButton battleButton = new JButton();
        connectionStatusLabel = new JLabel();
        JButton disconnectButton = new JButton();
        JLabel yourStatsLabel = new JLabel();
        JLabel stat1Label = new JLabel();
        JLabel stat2Label = new JLabel();
        JLabel stat3Label = new JLabel();
        JLabel youCanStartLabel = new JLabel();
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
        tournamentButton.setFont(customFont18);
        connectionStatusLabel.setFont(customFont18);
        disconnectButton.setFont(customFont18);
        youCanStartLabel.setFont(customFont18);
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

        downloadButton.setBackground((new Color(128, 12, 12)));
        downloadButton.setForeground(new Color(209,209,202));
        downloadButton.setFont(customFont18);

        battleButton.addActionListener(actionEvent -> new SelectOpponentController(frame,client));
        downloadButton.addActionListener(actionEvent -> new FileSelectionController(frame, client));
        tournamentButton.addActionListener(this::startTournament);
        tournamentButton.setText("Tournament");
        tournamentButton.setBackground(new Color(128, 12, 12));
        tournamentButton.setForeground(new Color(209,209,202));

        connectionStatusLabel.setText("Connected as " + client.name);
        connectionStatusLabel.setFont(customFont18);
        connectionStatusLabel.setForeground(new Color(209,209,202));


        disconnectButton.setText("Disconnect");
        disconnectButton.setBackground(new Color(128, 12, 12));
        disconnectButton.setForeground(new Color(209,209,202));
        disconnectButton.addActionListener(this::disconnect);

        yourStatsLabel.setText("Your stats:");
        yourStatsLabel.setForeground(new Color(209,209,202));

        youCanStartLabel.setText("You can start a");
        youCanStartLabel.setForeground(new Color(209,209,202));
        youCanStartLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        try {
            DecimalFormat df = new DecimalFormat("#.##");

            stat1Label.setText("rank: " + client.getStats()[0].lastpos);
            stat2Label.setText("score: " + client.getStats()[0].score);
            stat3Label.setText("avgrank: " + df.format(client.getStats()[0].avgpos));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(connectionStatusLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                                                .addComponent(downloadButton)
                                                .addGap(236, 236, 236))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(77, 77, 77)
                                                                .addComponent(orLabel))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(49, 49, 49)
                                                                .addComponent(battleButton))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(29, 29, 29)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(tournamentButton)
                                                                        .addComponent(youCanStartLabel))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(stat1Label)
                                        .addComponent(stat3Label)
                                        .addComponent(disconnectButton)
                                        .addComponent(stat2Label)
                                        .addComponent(yourStatsLabel))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(youCanStartLabel)
                                        .addComponent(yourStatsLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(battleButton)
                                        .addComponent(stat1Label))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(stat2Label)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(stat3Label))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(orLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tournamentButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(disconnectButton)
                                                        .addComponent(downloadButton))
                                                .addContainerGap())
                                        .addComponent(connectionStatusLabel, javax.swing.GroupLayout.Alignment.TRAILING)))
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
        DecimalFormat df = new DecimalFormat("#.##");

        for(UserStats stat : stats) {

            data.addRow(new PlayerModel(stat.name, stat.lastpos, stat.score,
                    new BigDecimal(stat.avgpos).setScale(2, RoundingMode.HALF_UP).doubleValue(),
                    stat.fighters));
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

class FileSelectionController extends JPanel{
    JFrame frame;
    Client client;
    JLabel l;
    File file;
    JPanel oldPanel;
    JButton button1;
    JButton button2;
    JButton returnButton;
    FileSelectionController(JFrame frame, Client client){
        this.frame = frame;
        this.client  = client;
        oldPanel = (JPanel) frame.getContentPane();
        frame.setContentPane(this);
        setPreferredSize(new Dimension(ScreenUtil.FILE_SELECTION_PAGE_SIZE));
        setLocation(ScreenUtil.getCenterPoint(ScreenUtil.FILE_SELECTION_PAGE_SIZE));
        frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.FILE_SELECTION_PAGE_SIZE));
        // button to open save dialog
        button1 = new JButton("upload");

        // button to open open dialog
        button2 = new JButton("choose");

        returnButton = new JButton("Return to main");

        // add action listener to the button to capture user
        // response on buttons
        button1.addActionListener(this::actionPerformed);
        button2.addActionListener(this::actionPerformed);
        returnButton.addActionListener(this::returnToMain);
        button1.setEnabled(false);


        add(button1);
        add(button2);
        add(returnButton);
        // set the label to its initial value
        l = new JLabel("no file selected");
        add(l);
        frame.pack();


    }
    public void actionPerformed(ActionEvent evt)
    {
        // if the user presses the save button show the save dialog
        String com = evt.getActionCommand();

        if (com.equals("upload")) {
            try {
                client.sendFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.MAIN_PAGE_SIZE));
            frame.setContentPane(oldPanel);
            frame.pack();
            AudioController.startAudio("Main_menu_track.wav");

        }

        // if the user presses the open dialog show the open dialog
        else if(com.equals("choose")){
            // create an object of JFileChooser class
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            FileNameExtensionFilter jarFilter = new FileNameExtensionFilter("jar ( .jar ) ", "jar");
            j.addChoosableFileFilter(jarFilter);

            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION)

            {
                // set the label to the path of the selected file
                l.setText(j.getSelectedFile().getAbsolutePath());
                System.out.println(j.getSelectedFile().getAbsoluteFile());
                file = new File(j.getSelectedFile().getAbsolutePath());
                button1.setEnabled(true);


            }
            // if the user cancelled the operation
            else
                l.setText("the user cancelled the operation");
        }

    }

    private void returnToMain(ActionEvent event){
        frame.setContentPane(oldPanel);
        frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.MAIN_PAGE_SIZE));
        frame.pack();
        AudioController.startAudio("Main_menu_track.wav");

    }
}
