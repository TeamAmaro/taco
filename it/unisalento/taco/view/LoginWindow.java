
package it.unisalento.taco.view;

import it.unisalento.taco.actionListeners.LoginButtonListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame{
            
    private JPanel centro;
    private JPanel nord;
    private JPanel sud;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    
    private Dimension dim;

    public LoginWindow(){
        super("TACO");
        Container c = this.getContentPane();
        
        centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.PAGE_AXIS));
        nord = new JPanel();
        sud = new JPanel();

        emailField = new JTextField();
        passwordField = new JPasswordField();
        emailLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");
        loginButton = new JButton("Login");

        loginButton.addActionListener(new LoginButtonListener(this));
        centro.add(emailLabel);
        centro.add(emailField);
        centro.add(passwordLabel);
        centro.add(passwordField);
        centro.add(loginButton);
        
        nord.add(new JLabel("Effettua il login"));
        
        sud.add(new JLabel("Sviluppato con amore dall'Amaro Team"));
        
        c.add(nord, BorderLayout.NORTH);
        c.add(centro, BorderLayout.CENTER);
        c.add(sud, BorderLayout.SOUTH);
        
        pack();
        
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        setLocation((int)((dim.getWidth()-this.getWidth())/2),(int)((dim.getHeight()-this.getHeight())/2));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    public JTextField getEmailField(){
        return emailField;
    }
    
    public JPasswordField getPasswordField(){
        return passwordField;
    }

    public void logged() {
        setVisible(false);
        dispose();
    }
}
