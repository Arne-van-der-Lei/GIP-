package org.friet.net.login.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.friet.net.UI.Button;
import org.friet.net.main.Main;
import org.friet.net.main.panel.MainPanel;
import org.friet.net.main.panel.PanelLogo;

public class PanelLogin extends JPanel {

    private BufferedImage image;
    public JLabel userLabel;
    public JTextField userText;
    public JLabel passwordLabel;
    public JPasswordField passwordText;
    public JButton login;
    public boolean done = false;

    public PanelLogin() {
        GridLayout g = new GridLayout(2, 1);
        g.setVgap(10);
        this.setLayout(g);
        try {
            this.image = ImageIO.read(new File("src/res/friet.png"));
        } catch (Exception ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        GridLayout g2 = new GridLayout(5, 1);
        g2.setVgap(5);
        JPanel p = new JPanel(g2);

        userText = new JTextField(20);
        userText.setText(Main.config.getString("btnGebruikersNaam"));
        userText.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (Main.config.getString("btnGebruikersNaam").equals(userText.getText())) {
                    userText.setText("");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
        p.add(userText);

        passwordText = new JPasswordField(20);
        passwordText.setText(Main.config.getString("btnWachtwoord"));
        passwordText.addActionListener(new EventLogin());
        passwordText.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (Main.config.getString("btnWachtwoord").equals(passwordText.getText())) {
                    passwordText.setText("");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
        p.add(passwordText);

        login = new Button(Main.config.getString("btnAanmelden"));
        login.addActionListener(new EventLogin());
        p.add(login);
        this.add(new PanelLogo());
        this.add(p);

    }

    public class EventLogin implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            char[] user = passwordText.getPassword();
            try {
                int maybe = Main.db.checkPass(userText.getText(), encode(new String(user)));
                switch (maybe) {
                    case 0:
                        done = false;
                        JOptionPane.showMessageDialog(null, Main.config.getString("LoginFout"));
                        break;
                    case 1:
                        done = true;
                        break;
                    case 2:
                        done = true;
                        Main.manager = true;
                }
            } catch (Exception ex) {
                Logger.getLogger(PanelLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public static String encode(String data) throws Exception {

        MessageDigest m = MessageDigest.getInstance("MD5");

        m.update(data.getBytes(), 0, data.length());

        byte messageDigest[] = m.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xFF & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }
        return hexString.toString();
    }
}
