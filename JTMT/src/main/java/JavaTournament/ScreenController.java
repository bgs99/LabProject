package JavaTournament;

import bgs99c.client.*;
import bgs99c.shared.UserStats;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.awt.GridBagConstraints.*;


public class ScreenController {
    private final int LOGINPAGE_HEIGHT = 530;
    private final int LOGINPAGE_WIDTH = 800;
    private Client client;
    private JFrame frame;
    private Label teamName;
    private Button tour;
    private Button battleBtn;
    ScreenController(Client client, JFrame frame) throws URISyntaxException {
        this.client = client;
        this.frame = frame;
        Background panel1 = new Background(client, frame);
        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = environment.getScreenDevices();
        DisplayMode dmode = devices[0].getDisplayMode();
        int screenWidth = dmode.getWidth();
        int screenHeight = dmode.getHeight();


        Dimension screenSize = new Dimension(screenWidth, screenHeight);
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - ( LOGINPAGE_WIDTH / 2),
                middle.y - (LOGINPAGE_HEIGHT / 2 ) );
        frame.setLocation(newLocation);
        //  frame.setContentPane(panel1);
        frame.add(panel1);
        frame.setContentPane(panel1);
        // frame.setPreferredSize(new Dimension(LOGINPAGE_WIDTH, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }



	

	
}


class Background extends JPanel {
    private final int LOGINPAGE_HEIGHT = 530;
    private final int LOGINPAGE_WIDTH = 800;

    private JFrame frame;
    private Client client;

    JLabel orLabel ;
    JButton battleButton ;
    JButton  tournamentLabel ;
    JLabel connectionStatusLabel ;
    JButton  disconnectButton ;
    JLabel yourStatsLabel ;
    JLabel stat1Label;
    JLabel stat2Label;
    JLabel stat3Label ;
    JLabel youcanstartLabel;
    Clip clip;
    public Background (Client client, JFrame frame) {
        this.frame = frame;
        this.client = client;


       orLabel = new JLabel();
      battleButton = new JButton();
       tournamentLabel = new JButton();
      connectionStatusLabel = new JLabel();
        disconnectButton = new JButton();
       yourStatsLabel = new JLabel();
       stat1Label = new JLabel();
       stat2Label = new JLabel();
        stat3Label = new JLabel();
              youcanstartLabel = new JLabel();   
        Font CustomFont;
        Font customFont18 = null;
        try {

            URL resource  = ScreenController.class.getResource("/beermoney.ttf");
            String TTFpath = Paths.get(resource.toURI()).toString();
            CustomFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(TTFpath));
            customFont18 = CustomFont.deriveFont(16.0F); //11 шрифт
        }
        catch (FontFormatException e2) 	 {e2.printStackTrace();}
        catch (IOException e2) 			 {e2.printStackTrace();} catch (URISyntaxException e) {
            e.printStackTrace();
        }

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


        setPreferredSize(new Dimension(800, 560));

        orLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orLabel.setText("or");

        battleButton.setText("Battle");
       battleButton.setBackground(new java.awt.Color(128, 0, 0));
       battleButton.setForeground(new java.awt.Color(255, 248, 220));

       battleButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent actionEvent) {
                new SelectOpponentController(frame,client);
           }
       });

        tournamentLabel.setText("Tournament");
        tournamentLabel.setBackground(new java.awt.Color(128, 0, 0));
        tournamentLabel.setForeground(new java.awt.Color(255, 248, 220));

        connectionStatusLabel.setText("Connected as " + client.name);
        connectionStatusLabel.setFont(customFont18);

        disconnectButton.setText("Disconnect");
        disconnectButton.setBackground(new java.awt.Color(128, 0, 0));
        disconnectButton.setForeground(new java.awt.Color(255, 248, 220));
        disconnectButton.addActionListener(this::disconnect);

        yourStatsLabel.setText("Your stats:");
        youcanstartLabel.setText("You can start a");
        youcanstartLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        try {
            stat1Label.setText("rank: " + client.getStats()[0].lastpos);
            stat2Label.setText("score: " + client.getStats()[0].score);
            stat3Label.setText("avgrank: " + client.getStats()[0].avgpos);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(orLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(youcanstartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(battleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(tournamentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(connectionStatusLabel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 520, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(stat1Label)
                                                        .addGap(100, 100, 100))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(stat2Label)
                                                                .addComponent(stat3Label))
                                                        .addContainerGap()))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(yourStatsLabel)
                                                .addGap(30, 30, 30))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(disconnectButton)
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(youcanstartLabel)
                                        .addComponent(yourStatsLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(battleButton)
                                        .addComponent(stat1Label))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(orLabel)
                                        .addComponent(stat2Label))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tournamentLabel)
                                        .addComponent(stat3Label))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(connectionStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(disconnectButton)))
        );

        URL resource = ScreenController.class.getResource("/Main_menu_track.wav");


        try {
            DataLine.Info daInfo = new DataLine.Info(Clip.class, null);
            try {


                AudioInputStream inputStream = AudioSystem.getAudioInputStream(resource);
                DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("music");
        }

        setPreferredSize(new Dimension(LOGINPAGE_WIDTH, LOGINPAGE_HEIGHT));


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
            GraphicsEnvironment environment =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();

            GraphicsDevice[] devices = environment.getScreenDevices();
            DisplayMode dmode = devices[0].getDisplayMode();
            int screenWidth = dmode.getWidth();
            int screenHeight = dmode.getHeight();
            URL resource = ScreenController.class.getResource("/new.png");
          Image image = ImageIO.read(Paths.get(resource.toURI()).toFile());

            g.drawImage(image,0,0,null);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private void updateConnectionStatus() {
//TODO: mayby use only when setConnection changes or delete
         frame.repaint();
        connectionStatusLabel.setText(client.checkConnection() ? "Connected as " + client.name : "Disconnected");
    }

    private void disconnect(ActionEvent event){
        try {
            client.close();
            clip.stop();
            frame.dispose();
            MainController mainController = new MainController(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectOpponent(){

    }
}

class SelectOpponentController extends JPanel{
    JFrame frame;
    Client client;
    JPanel oldPanel;
    private String selectedPlayer = null;
    private PlayerTableModel data = new PlayerTableModel();
    SelectOpponentController(JFrame frame, Client client){
        this.frame = frame;
        oldPanel = (JPanel) frame.getContentPane();
        this.client = client;
        setPreferredSize(new Dimension(420,400));
        frame.setContentPane(this);
        frame.pack();
        JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        JButton battleButton = new javax.swing.JButton();
        JButton returnButton = new javax.swing.JButton();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(returnButton)
                                .addGap(216, 216, 216)
                                .addComponent(battleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(returnButton)
                                        .addComponent(battleButton))
                                .addContainerGap(17, Short.MAX_VALUE))
        );
        JTable jTable1 = new JTable(data);
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
                //  teamName.setText("Team " + selectedPlayer);
                //TODO fill detailsList
            }
        });
       jScrollPane2.setViewportView(jTable1);

        battleButton.setText("Battle");
        battleButton.setEnabled(false);
        returnButton.setText("Return");
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




    private void startBattle(ActionEvent event) {
        try {
            BattleInfo battleInfo = client.declareBattle(selectedPlayer);
            new BattleController(battleInfo, frame);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void returnToMain(ActionEvent event){

        frame.setContentPane(oldPanel);
      //  frame.setPreferredSize(new Dimension(800,530));
        frame.pack();
    }


}

