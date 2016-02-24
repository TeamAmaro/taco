package it.unisalento.taco.controller;

import it.unisalento.taco.business.CapoProgettoDelegate;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class FXMLProgettoElencoController implements Initializable {
    
    @FXML Label nomeClient;
    @FXML Label logout;
    @FXML GridPane gridPane;
    @FXML Label titolo;
    
    private Main application;
    private CapoProgettoDelegate delegate = CapoProgettoDelegate.getInstance();
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initData(){
        
        String nome = application.getUtente().getNome();
        String cognome = application.getUtente().getCognome();
        nomeClient.setText(nome + " " + cognome);
        titolo.setText("Elenco progetti di " + nome + " " + cognome);
        
        List<Progetto> listaProgetti = delegate.getProgetto((CapoProgetto) application.getUtente());

        if(listaProgetti.isEmpty()){
            Label errorMessage = new Label("Al momento non sei a capo di nessun progetto!");
            gridPane.add(errorMessage, 0, 1);
        }
        else {
            
            Label progettoLabel = new Label("Progetto");
            progettoLabel.setMinWidth(300.0);
            Label budgetLabel = new Label("Budget");
            budgetLabel.setMinWidth(150.0);
            Label saldoLabel = new Label("Saldo");
            budgetLabel.setMinWidth(150.0);
            
            gridPane.add(progettoLabel, 0, 1);
            gridPane.add(budgetLabel, 1, 1);
            gridPane.add(saldoLabel, 2, 1);
            
            int i = 2;
            for(final Progetto p : listaProgetti){
                

                Label nomeProgetto = new Label(p.getNome());
                Label budgetProgetto = new Label(p.getFormatBudget());
                Label saldoProgetto = new Label(p.getFormatSaldo());

                nomeProgetto.getStyleClass().add("nome-progetto");

                gridPane.add(nomeProgetto, 0, i);
                gridPane.add(budgetProgetto, 1, i);
                gridPane.add(saldoProgetto, 2, i);
                        
                nomeProgetto.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        application.getDetails(p);
                    }
                });
            }
        }
        
        
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
    } 
}
