package JavaTournament;

import bgs99c.client.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.WEST;

public class MainController extends JFrame{
    private JTextField login;
    private JPasswordField password;
    private final int LOGINPAGE_HEIGHT = 160;
    private final int LOGINPAGE_WIDTH = 260;
    private Client client = new Client();
    private JFrame frame;

    MainController(JFrame frame) {
        this.frame = frame;


        //Костыль, у меня в Ubuntu почему-то Java считает что 2 экрана, потом потесчу на винде
        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] devices = environment.getScreenDevices();
            DisplayMode dmode = devices[0].getDisplayMode();
            int screenWidth = dmode.getWidth();
            int screenHeight = dmode.getHeight();


        //frame.setPreferredSize(new Dimension(LOGINPAGE_WIDTH, LOGINPAGE_HEIGHT));
        Dimension screenSize = new Dimension(screenWidth, screenHeight);
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - ( LOGINPAGE_WIDTH / 2),
                middle.y - (LOGINPAGE_HEIGHT / 2 ) );

        LoginPanel panel = new LoginPanel(frame, client);


        frame.setContentPane(panel);
        frame.setLocation(newLocation);
        frame.setVisible(true);
        frame.pack();
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }


}


class LoginPanel extends JPanel implements KeyListener {
    private final int LOGINPAGE_HEIGHT = 130;
    private final int LOGINPAGE_WIDTH = 260;
    JFrame frame;
    Client client;
    JLabel passwordLabel = new javax.swing.JLabel();
    JLabel  loginLabel = new javax.swing.JLabel();
    JTextField loginTextField = new javax.swing.JTextField();
    JPasswordField passwordField = new javax.swing.JPasswordField();
    JLabel  logoLabel = new javax.swing.JLabel();
    JButton loginButton = new JButton();

      LoginPanel(JFrame frame, Client client) {
          this.frame = frame;
          this.client = client;
          setPreferredSize(new Dimension(LOGINPAGE_WIDTH, LOGINPAGE_HEIGHT));
        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        passwordLabel.setText("Password");

        loginLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        loginLabel.setText("Login");

        loginButton.setText("Login");
          loginButton.setBackground(new java.awt.Color(128, 0, 0));
          loginButton.setFont(new java.awt.Font("Algerian", 0, 11));
          loginButton.setForeground(new java.awt.Color(255, 248, 220));
        loginButton.addKeyListener(this);

        loginTextField.setText("");

        passwordField.setText("");

        logoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
          logoLabel.setText("ITMOnsters");
          logoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
          layout.setHorizontalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                          .addComponent(logoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGroup(layout.createSequentialGroup()
                                  .addContainerGap()
                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addGroup(layout.createSequentialGroup()
                                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                          .addComponent(loginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                          .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                          .addComponent(passwordField)
                                                          .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                          .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                  .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          );
          layout.setVerticalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                          .addGroup(layout.createSequentialGroup()
                                  .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                          .addComponent(loginLabel)
                                          .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                          .addComponent(passwordLabel)
                                          .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                  .addComponent(loginButton)
                                  .addContainerGap())
          );
          loginButton.addActionListener(q -> submit());

          frame.addWindowListener(new WindowListener() {
              @Override
              public void windowOpened(WindowEvent windowEvent) {

              }

              @Override
              public void windowClosing(WindowEvent windowEvent) {
                  try {
                      client.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }

              @Override
              public void windowClosed(WindowEvent windowEvent) {

              }

              @Override
              public void windowIconified(WindowEvent windowEvent) {

              }

              @Override
              public void windowDeiconified(WindowEvent windowEvent) {

              }

              @Override
              public void windowActivated(WindowEvent windowEvent) {

              }

              @Override
              public void windowDeactivated(WindowEvent windowEvent) {

              }
          });

      }


    private void submit() {
        String name = loginTextField.getText();
        char[] passwordArray = passwordField.getPassword();
        StringBuilder pass = new StringBuilder();
        for(char c : passwordArray) {
            pass.append(c);
        }
        try {
            if (!client.init(name, pass.toString())) {
                JOptionPane.showMessageDialog(null, "Connection to server refused");
                return;
            }
            if (client.login(name, pass.toString())) {
                new ScreenController(client, frame);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong login/password");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection failed");
            e.printStackTrace();
        } finally {
            Arrays.fill(passwordArray, '0');
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            submit();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    public void paintComponent(Graphics g) {
        try {
            GraphicsEnvironment environment =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();

            GraphicsDevice[] devices = environment.getScreenDevices();
            DisplayMode dmode = devices[0].getDisplayMode();
            int screenWidth = dmode.getWidth();
            int screenHeight = dmode.getHeight();
            URL resource = ScreenController.class.getResource("/bg.png");
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
}


