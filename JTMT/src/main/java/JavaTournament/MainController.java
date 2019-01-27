package JavaTournament;

import bgs99c.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Arrays;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.WEST;

public class MainController {
    private JTextField login;
    private JPasswordField password;

    private Client client = new Client();
    private JFrame frame;

    MainController(JFrame frame) {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        login = new JTextField();
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = WEST;
        c.fill = BOTH;
        c.gridx = 0;
        c.gridy = RELATIVE;
        panel1.add(login, c);
        password = new JPasswordField();
        panel1.add(password, c);
        JButton loginButton = new JButton();
        loginButton.setText("Button");
        panel1.add(loginButton, c);
        this.frame = frame;
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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
        String name = login.getText();
        char[] passwordArray = password.getPassword();
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
        } finally {
            Arrays.fill(passwordArray, '0');
        }
    }
}