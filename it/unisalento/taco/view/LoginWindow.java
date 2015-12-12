/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author tomma
 */
public class LoginWindow extends JFrame{
    public LoginWindow(){
        super("Login Window");
        JTextField usernameField = new JTextField("Inserisci il tuo username");
        JPasswordField passwordField = new JPasswordField("Inserisci la tua password");
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(3,5));
        c.add(usernameField);
        c.add(passwordField);
        JLabel label = new JLabel("A label");
        label.setFont(new Font("Serif", Font.PLAIN, 14));
        label.setForeground(new Color(0xffffdd));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,600);
        this.setVisible(true);
        
    }
}
