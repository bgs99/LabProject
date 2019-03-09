package JavaTournament;

import bgs99c.client.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;





public class MainController extends JFrame{


    MainController(JFrame frame) {
        Client client = new Client();
        LoginPanel panel = new LoginPanel(frame, client);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.setLocation(ScreenUtil.getCenterPoint(ScreenUtil.LOGIN_PAGE_SIZE));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setFocusable(true);
        panel.requestFocusInWindow();





    }


}


class LoginPanel extends JPanel implements KeyListener {

    private JFrame frame;
    private Client client;
    private JTextField loginTextField = new JTextField();
    private  JPasswordField passwordField = new JPasswordField();

    LoginPanel(JFrame frame, Client client) {
        this.frame = frame;
        this.client = client;
        setPreferredSize(ScreenUtil.LOGIN_PAGE_SIZE);

        Font CustomFont;
        Font customFont18 = null;
        try {
            URL resource  = ScreenController.class.getResource("/beermoney.ttf");
            if (resource == null) {
                System.err.println("Failed to get resource: /beermoney.ttf");
                return;
            }
            String TTFpath = Paths.get(resource.toURI()).toString();
            CustomFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(TTFpath));
            customFont18 = CustomFont.deriveFont(19.0F); //11 шрифт
        }
        catch (FontFormatException | IOException | URISyntaxException e2) 	 {e2.printStackTrace();}

        JLabel passwordLabel = new JLabel();
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordLabel.setText("Password");
        passwordLabel.setForeground(new Color(209,202,202));
        passwordLabel.setFont(customFont18);
        JLabel loginLabel = new JLabel();
        loginLabel.setHorizontalAlignment(SwingConstants.LEFT);
        loginLabel.setText("Login");
        loginLabel.setForeground(new Color(209,202,202));
        loginLabel.setFont(customFont18);

        JButton loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.setBackground(new Color(128, 12, 12));
        loginButton.setFont(customFont18);
        loginButton.setForeground(new Color(209,209,202));
        loginButton.addKeyListener(this);

        loginTextField.setText("");

        passwordField.setText("");

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setText("ITMOnsters");
        logoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        logoLabel.setFont(customFont18);
        logoLabel.setForeground(new Color(209,209,202));



        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(logoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(loginLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(passwordField)
                                                        .addComponent(loginTextField, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginLabel)
                                        .addComponent(loginTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
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

            URL resource = ScreenController.class.getResource("/bg.png");
            Image image = ImageIO.read(Paths.get(resource.toURI()).toFile());

            g.drawImage(image,0,0,null);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}



