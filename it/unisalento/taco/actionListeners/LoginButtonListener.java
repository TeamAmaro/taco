package it.unisalento.taco.actionListeners;

import it.unisalento.taco.business.UtenteDelegate;
import it.unisalento.taco.model.Utente;
import it.unisalento.taco.view.CatalogoWindow;
import it.unisalento.taco.view.LoginWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginButtonListener implements ActionListener {
    
    private final LoginWindow loginWindow;
    
    public LoginButtonListener(LoginWindow loginWindow){
        super();
        this.loginWindow = loginWindow;
    }

    @Override public void actionPerformed(ActionEvent e) {
        String email = loginWindow.getEmailField().getText();
        String password = String.valueOf(loginWindow.getPasswordField().getPassword());
        Utente client = UtenteDelegate.getInstance().login(email, password);
        if(client != null)
        {
            loginWindow.logged();
            System.out.println("Hai effettuato l'accesso come: " + client);
            String className = client.getClass().getSimpleName();
            System.out.println("Sei un'istanza di " + className);
        }
    }
}