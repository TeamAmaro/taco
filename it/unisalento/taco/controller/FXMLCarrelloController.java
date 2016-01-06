/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FXMLCarrelloController implements Initializable {

    @FXML GridPane content;
    @FXML ImageView leftLogo;
    @FXML Label logout;
    
    private Main application;
    private Carrello carrello;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO-DO
    }    
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void initData(){
        int i = 2;
        try {
            carrello = delegate.getCarrello((Dipendente) application.getUtente());
        } catch (NoIDMatchException e){
            System.err.println(e.getMessage());
        }
        Map<Prodotto, Integer> listaProdotti = carrello.getListaProdotti();
        System.out.println(carrello);
        if(listaProdotti.isEmpty()){
            content.getChildren().clear();
            Label message = new Label("Il tuo carrello è vuoto!");
            Label message2 = new Label("Visita il catalogo per aggiungere prodotti al carrello.");
            message2.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    application.dipendenteView();
                }
            });
            content.add(message, 0, 0);
            content.add(message2, 0, 1);
        }
        else{
            for(Map.Entry<Prodotto,Integer> lp : listaProdotti.entrySet()) {
                
                final Prodotto prodotto = lp.getKey();
                int quantita = lp.getValue();

                final HBox hb = new HBox();
                
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setPrefHeight(100.0);
                hb.setSpacing(20.0);

                ImageView iv = new ImageView(new Image("it/unisalento/taco/view/img/thumbnail.jpg"));
                iv.setFitHeight(100.0);
                iv.setPreserveRatio(true);

                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER_LEFT);
                vb.setSpacing(10.0);

                Label nomeProdotto = new Label(prodotto.getNome());
                nomeProdotto.getStyleClass().add("nome-prodotto");
                Label prezzoProdotto = new Label(Double.toString(prodotto.getPrezzo()));
                prezzoProdotto.getStyleClass().add("prezzo-prodotto");
                Label prodProdotto = new Label(prodotto.getProduttore().nome());
                prodProdotto.getStyleClass().add("info-text");

                Label quantitaLabel = new Label(Integer.toString(quantita));
                quantitaLabel.getStyleClass().add("info-text");
                Label disponibilita = new Label();
                disponibilita.getStyleClass().add("info-text");

                try{
                    int quantitaMag = delegate.chiediDisponibilità((Dipendente) application.getUtente(), prodotto);
                    if(quantitaMag <= 0)
                        disponibilita.setText("No");
                    else 
                        disponibilita.setText("Sì");
                }
                catch(NoQueryMatchException e){
                    disponibilita.setText("?");
                }
                
                
                final Button rimuovi = new Button("Rimuovi");
                rimuovi.getStyleClass().add("remove-button");
                
                rimuovi.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        delegate.removeProdotto(carrello, prodotto);
                        rimuovi.setDisable(true);
                        rimuovi.setText("Rimosso!");
                    }
                });

                vb.getChildren().addAll(nomeProdotto, prezzoProdotto, prodProdotto);
                hb.getChildren().addAll(iv, vb);

                content.add(hb, 0, i);
                content.add(quantitaLabel, 1, i);
                content.add(disponibilita, 2, i);
                content.add(rimuovi, 3, i);
                i++;
            }
        }
        
        leftLogo.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    application.dipendenteView();
                }
        });
        
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
    }
}
