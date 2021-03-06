/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.business.GeneratoreOrdini;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoProgettoException;
import it.unisalento.taco.exception.NoQueryMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class FXMLCarrelloController implements Initializable {

    @FXML GridPane content;
    @FXML Label logout;
    @FXML HBox topLeft;
    @FXML Label subtotale;
    @FXML Button ordinaButton;
    @FXML ImageView backArrow;
    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label titoloLabel;
    @FXML HBox ordinaBox;
    @FXML VBox scrollContent;
    @FXML HBox backArrowBox;
    @FXML AnchorPane anchorPane;
    
    private Main application;
    private Carrello carrello;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Nulla da fà
    }    
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void initData() throws NoIDMatchException, NoProgettoException{
        initInfo();
        initMenu();
        initContent();
        initAnimation();
    }
    
    private void initInfo() throws NoIDMatchException, NoProgettoException{
        
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
        String nomeProg = "Nessun Progetto";
        int numeroProd = 0;
        String saldo = "0.0€";


        nomeProg = delegate.getProgetto((Dipendente) application.getUtente()).getNome();
        saldo = delegate.getProgetto((Dipendente) application.getUtente()).getFormatSaldo();
        numeroProd = delegate.getCarrello((Dipendente) application.getUtente()).numeroProdotti();
        carrello = delegate.getCarrello((Dipendente) application.getUtente());

        nomeProgetto.setText(nomeProg);
        saldoProgetto.setText(saldo);

    }
    
    private void initMenu(){
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        backArrowBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.dipendenteView();
            }
        });
        
        
        ordinaButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {                
                
                Map<Prodotto, Integer> prodottiInsufficienti = new LinkedHashMap<>();
                for(Map.Entry<Prodotto,Integer> listaProdotti : carrello.getListaProdotti().entrySet()){
                    int quantitaRichiesta = listaProdotti.getValue();
                    Prodotto prodottoRichiesto = listaProdotti.getKey();
                    int quantitaDisponibile;
                    try {
                        quantitaDisponibile = delegate.chiediDisponibilitaAll(prodottoRichiesto);
                        if (quantitaRichiesta > quantitaDisponibile)
                        prodottiInsufficienti.put(prodottoRichiesto, quantitaDisponibile);
                    } catch (NoQueryMatchException | NoIDMatchException ex) {
                        //Salta richiesta
                    }                    
                }
                if(prodottiInsufficienti.isEmpty()){
                    Set<Ordine> listaOrdini;
                    try {
                        listaOrdini = GeneratoreOrdini.getInstance().generaOrdini((Dipendente) application.getUtente());
                        application.ordina(listaOrdini);
                    } catch (NoQueryMatchException | NoIDMatchException | NoProgettoException ex) {
                        application.errorDialog(ex);
                    }
                } else {
                    final GridPane grid = new GridPane();

                    anchorPane.getChildren().add(grid);
                    anchorPane.setBottomAnchor(grid, 0.0);
                    anchorPane.setTopAnchor(grid, 0.0);
                    anchorPane.setRightAnchor(grid, 0.0);
                    anchorPane.setLeftAnchor(grid, 0.0);

                    grid.getStyleClass().add("dialog-blur");
                    HBox hb = new HBox();
                    hb.getStyleClass().add("dialog-box");
                    grid.setAlignment(Pos.CENTER);

                    Button okBtn = new Button("Ok");
                    Button cncBtn = new Button("Annulla");

                    okBtn.getStyleClass().add("ok-button");
                    cncBtn.getStyleClass().add("cnc-button");

                    StringBuilder warnMsg = new StringBuilder();
                    warnMsg.append("Attenzione! Le quantità dei seguenti prodotti non soddisfano la richiesta:").append(System.lineSeparator());
                    for(Map.Entry<Prodotto,Integer> pInsEntry : prodottiInsufficienti.entrySet()){
                        warnMsg.append("\"" + pInsEntry.getKey().getNome() + "\" in magazzino: " + pInsEntry.getValue() + System.lineSeparator());
                    }
                    warnMsg.append("Proseguire comunque con l'ordine?");

                    Label warn = new Label(warnMsg.toString());

                    grid.add(hb, 0, 0, 5, 4);
                    grid.add(warn, 1, 1, 3, 1);
                    grid.add(cncBtn, 1, 2);
                    grid.add(okBtn, 3, 2);

                    grid.setVgap(30.0);
                    grid.setHgap(20.0);

                    okBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override public void handle(MouseEvent arg0) {
                             try{
                                Set<Ordine> listaOrdini = GeneratoreOrdini.getInstance().generaOrdini((Dipendente) application.getUtente());
                                application.ordina(listaOrdini);
                                anchorPane.getChildren().remove(grid);
                            }catch (NoQueryMatchException | NoIDMatchException | NoProgettoException ex){
                                application.errorDialog(ex);
                            }
                        }
                    });

                    cncBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override public void handle(MouseEvent arg0) {
                            anchorPane.getChildren().remove(grid);
                        }
                    });
                }
            }
        });
    }
    
    private void initContent() throws NoIDMatchException{
        
        int i = 3;
        
        Map<Prodotto, Integer> listaProdotti = carrello.getListaProdotti();
        
        if(listaProdotti.isEmpty()){
            
            content.getChildren().clear();
            scrollContent.getChildren().remove(ordinaBox);
            
            Label message = new Label("Il tuo carrello è vuoto!");
            Label message2 = new Label("Visita il catalogo per aggiungere prodotti al carrello.");
            message2.getStyleClass().add("message");
            
            message2.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    application.dipendenteView();
                }
            });
            
            content.add(message, 0, 0);
            content.add(message2, 0, 1);
        }
        else{
            
            subtotale.setText(carrello.getFormatTotale());
            
            for(Map.Entry<Prodotto,Integer> lp : listaProdotti.entrySet()) {
                
                final Prodotto prodotto = lp.getKey();
                int quantita = lp.getValue();

                final HBox hb = new HBox();
                
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setPrefHeight(100.0);
                hb.setSpacing(20.0);
                ImageView iv = new ImageView(new Image("it/unisalento/taco/view/img/" + prodotto.getImmagine()));
                iv.setFitHeight(100.0);
                iv.setPreserveRatio(true);
                iv.getStyleClass().add("icona-prodotto");

                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER_LEFT);
                vb.setSpacing(10.0);

                Label nomeProdotto = new Label(prodotto.getNome());
                nomeProdotto.getStyleClass().add("nome-prodotto");
                
                Label prezzoProdotto = new Label(prodotto.getFormatPrezzo());
                prezzoProdotto.getStyleClass().add("prezzo-prodotto");
                Label prodProdotto = new Label(prodotto.getProduttore().nome());
                prodProdotto.getStyleClass().add("info-text");

                final Label quantitaLabel = new Label(Integer.toString(quantita));
                quantitaLabel.getStyleClass().add("quantita-text");
                
                final int position = i;
                
                final Button rimuovi = new Button("Rimuovi");
                rimuovi.getStyleClass().add("remove-button");
                
                rimuovi.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        delegate.removeProdotto(carrello, prodotto);
                        rimuovi.setDisable(true);
                        rimuovi.setText("Rimosso!");
                        subtotale.setText(carrello.getFormatTotale());
                        if(carrello.getListaProdotti().isEmpty())
                            ordinaButton.setDisable(true);
                        
                        quantitaLabel.setVisible(false);
                        quantitaLabel.setManaged(false);
                    }
                });
                
                quantitaLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        
                        quantitaLabel.setVisible(false);
                        quantitaLabel.setManaged(false);
                        
                        rimuovi.setVisible(false);
                                                
                        final TextField newQuantita = new TextField();
                        newQuantita.setText(quantitaLabel.getText());
                        newQuantita.setMaxWidth(50.0);
 
                        content.add(newQuantita, 1, position);
                        
                        newQuantita.focusedProperty().addListener(new ChangeListener<Boolean>()
                            {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                                {
                                    if (!newPropertyValue)
                                    {
                                        rimuovi.setVisible(true);
                                        content.getChildren().remove(newQuantita);
                                        quantitaLabel.setVisible(true);
                                        quantitaLabel.setManaged(true);
                                    }
                                }
                            });
                        
                        newQuantita.setOnKeyPressed(new EventHandler<KeyEvent>(){
                            @Override public void handle(KeyEvent arg0) {
                                if(arg0.getCode() == KeyCode.ENTER){
                                    try {
                                        int q = Integer.parseInt(newQuantita.getText());
                                        if(q > 0){
                                            delegate.modificaQuantita(carrello, prodotto, q);
                                            content.getChildren().remove(newQuantita);
                                            quantitaLabel.setVisible(true);
                                            quantitaLabel.setManaged(true);
                                            quantitaLabel.setText(newQuantita.getText());
                                            subtotale.setText(delegate.getCarrello((Dipendente) application.getUtente()).getFormatTotale());
                                            rimuovi.setVisible(true);
                                        }
                                    } catch(NumberFormatException e){
                                        content.getChildren().remove(newQuantita);
                                        quantitaLabel.setVisible(true);
                                        quantitaLabel.setManaged(true);
                                    } catch (NoIDMatchException ex) {
                                        application.errorDialog(ex);
                                    }
                                }
                            }
                        });

                    }
                });
                
                Label disponibilita = new Label();
                disponibilita.getStyleClass().add("info-text");

                try{
                    int quantitaMag = delegate.chiediDisponibilita((Dipendente) application.getUtente(), prodotto);
                    if(quantitaMag <= 0)
                        disponibilita.setText("Disponibile" + System.lineSeparator() + "presso altre" + System.lineSeparator() + "sedi");
                    else 
                        disponibilita.setText("Disponibile");
                } catch (NoQueryMatchException ex) {
                    disponibilita.setText("Errore nel reperire disponibilità");
                }
                
                
                
                nomeProdotto.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        application.getDetails(prodotto);
                    }
                });
                
                
                iv.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        application.getDetails(prodotto);
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
    }
    
    private void initAnimation(){
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setFromValue(0.3f);
        ft.setToValue(1.0f);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        tt.setFromX(-100f);
        tt.setToX(0);
        
        ParallelTransition pt = new ParallelTransition(backArrow, ft, tt);
        pt.play();
    }
}
