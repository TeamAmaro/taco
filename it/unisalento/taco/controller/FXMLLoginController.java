package it.unisalento.taco.controller;

import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class FXMLLoginController extends AnchorPane implements Initializable {

    @FXML TextField emailField;
    @FXML PasswordField passwordField;
    @FXML Button loginButton;
    @FXML Label errorMessage;

    private Main application;
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    @Override public void initialize(URL location, ResourceBundle resources) {
        //Non utilizzato
    }
    
    public void effettuaLogin(ActionEvent event) {
        if (!application.setUtente(emailField.getText(), passwordField.getText())){
            errorMessage.setText("Combinazione Email/Password non corretta!");
        }
        else {            
            if(application.getUtente() instanceof Dipendente)
                application.dipendenteView();
            else if(application.getUtente() instanceof CapoProgetto)
                application.capoProgettoView();
            else if(application.getUtente() instanceof Magazziniere)
                application.magazziniereView();
        }
    }
}