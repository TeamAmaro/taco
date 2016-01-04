/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class FXMLCatalogoController extends AnchorPane implements Initializable{
    
    @FXML Label categoriaA;
    @FXML Label categoriaB;
    @FXML Label categoriaC;
    @FXML Label categoriaD;

    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label carrello;
    
    @FXML ImageView searchIcon;
    @FXML TextField searchBar;
    
    @FXML Label logout;
    
    @FXML GridPane gridPane;
    @FXML AnchorPane content;
    
    VBox vb;
    ScrollPane sp;
    
    private Main application = Main.getInstance();
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();
    
    @Override public void initialize(URL location, ResourceBundle resources){
        //Da implementare
        
        nomeClient.setText(application.getClient().getNome() + " " + application.getClient().getCognome());
        String nomeProg = "Nessun Progetto";
        int numeroProd = 0;
        double saldo = 0.0;
        try{
            nomeProg = delegate.getProgetto((Dipendente) application.getClient()).getNome();
            saldo = delegate.getProgetto((Dipendente) application.getClient()).getSaldo();
            numeroProd = delegate.getCarrello((Dipendente) application.getClient()).numeroProdotti();
        }
        catch(NoIDMatchException e){
            System.exit(1);
        }
        catch(NoQueryMatchException e){

        }finally {
            nomeProgetto.setText(nomeProg);
            saldoProgetto.setText(Double.toString(saldo) +"€");
            carrello.setText(Integer.toString(numeroProd));
        }

        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        categoriaA.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                //Da implementare
                System.out.println("Hai cliccato su categoria A");
            }
        });
        
        categoriaB.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                //Da implementare
                System.out.println("Hai cliccato su categoria B");

            }
        });
        
        categoriaC.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                //Da implementare
                System.out.println("Hai cliccato su categoria C");
            }
        });
        
        categoriaD.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                //Da implementare
                System.out.println("Hai cliccato su categoria D");
            }
        });
        
        searchIcon.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                //Da implementare
                System.out.println("Hai cliccato sull'avvio ricerca");
                System.out.println("Ricerco prodotti ... " + searchBar.getText());
                if(searchBar.getText().isEmpty())
                    return;
                
                content.getChildren().remove(gridPane);

                sp = new ScrollPane();
                vb = new VBox();

                vb.setAlignment(Pos.CENTER);
                vb.setSpacing(10.0);
                vb.setPadding(new Insets(10.0,20.0,10.0,10.0)); //bozzom="10.0"left="20.0"right="10.0"top="10.0"
                
                try {
                    Set<Prodotto> listaProdotti = delegate.cercaProdotti(searchBar.getText());
                    for(Prodotto p : listaProdotti){
                        System.out.println("Faccio");
                        HBox hb = new HBox();
                        
                        hb.setAlignment(Pos.CENTER_LEFT);
                        hb.setPrefHeight(100.0);
                        hb.setSpacing(20.0);
                        
                        ImageView iv = new ImageView(new Image("it/unisalento/taco/view/img/thumbnail.jpg"));
                        iv.setFitHeight(100.0);
                        iv.setPreserveRatio(true);
                        
                        VBox valori = new VBox();
                        Label nome = new Label(p.getNome());
                        Label prezzo = new Label(Double.toString(p.getPrezzo()) + "€");
                        Label descrizione = new Label();
                        
                        try{
                            int quantita = delegate.chiediDisponibilità((Dipendente) application.getClient(), p);
                            if(quantita < 10)
                               descrizione.setText("In esaurimento");
                            else if(quantita == 0)
                                descrizione.setText("Non disponibile nel magazzino vicino.");
                            else 
                                descrizione.setText("Disponibile");
                        }
                        catch(NoQueryMatchException e){
                            descrizione.setText("Impossibile reperire disponibilità");
                        }
                        valori.getChildren().addAll(nome, prezzo, descrizione);
                        hb.getChildren().addAll(iv, valori);
                        vb.getChildren().add(hb);
                    }

                }
                catch (NoIDMatchException e){
                    System.err.println("OUCH!");
                }
                System.out.println("Fatto");
                sp.setStyle("-fx-background-color: green");
                sp.setContent(vb);

                content.getChildren().add(sp);
                content.setTopAnchor(sp,50.0);
                content.setLeftAnchor(sp, 200.0);
                content.setRightAnchor(sp, 0.0);
                content.setBottomAnchor(sp, 0.0);

            }
        });
    }
}
