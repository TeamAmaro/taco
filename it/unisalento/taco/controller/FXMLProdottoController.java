/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FXMLProdottoController implements Initializable {

    private Prodotto prodotto;
    private Main application;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();

    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label carrello;
    
    @FXML Label nomeProdotto;
    @FXML Label prezzoProdotto;
    @FXML Label descrizione;
    @FXML Label prodProdotto;
    @FXML Label disponibilita;
    
    @FXML Label logout;
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void setProdotto(Prodotto prodotto){
        this.prodotto = prodotto;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           //TO-DO
    }    

    public void initData(){
        
        String nomeProg = "Nessun Progetto";
        int numeroProd = 0;
        double saldo = 0.0;
        
        try{
            nomeProg = delegate.getProgetto((Dipendente) application.getUtente()).getNome();
            saldo = delegate.getProgetto((Dipendente) application.getUtente()).getSaldo();
            numeroProd = delegate.getCarrello((Dipendente) application.getUtente()).numeroProdotti();
        }
        catch(NoIDMatchException e){
            System.exit(1); //ERRORE GRAVE
        }
        catch(NoQueryMatchException e){
            //Non fare nulla
        }
        finally{
            nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
            nomeProgetto.setText(nomeProg);
            saldoProgetto.setText(Double.toString(saldo) +"€");
            carrello.setText(Integer.toString(numeroProd));
            nomeProdotto.setText(prodotto.getNome());
            prezzoProdotto.setText(Double.toString(prodotto.getPrezzo()) + "€");
            descrizione.setText(prodotto.getDescrizione());
            prodProdotto.setText(prodotto.getProduttore().toString());
        }
        
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        
    }


}
