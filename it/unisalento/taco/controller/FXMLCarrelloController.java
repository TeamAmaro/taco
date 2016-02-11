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
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

public class FXMLCarrelloController implements Initializable {

    @FXML GridPane content;
    @FXML Label logout;
    @FXML HBox topLeft;
    @FXML Label totale;
    @FXML Button ordinaButton;
    @FXML ImageView backArrow;
    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label titoloLabel;
    @FXML HBox ordinaBox;
    @FXML VBox scrollContent;
    @FXML HBox backArrowBox;
    
    private Main application;
    private Carrello carrello;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setFromValue(0.3f);
        ft.setToValue(1.0f);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        tt.setFromX(-100f);
        tt.setToX(0);
        
        ParallelTransition pt = new ParallelTransition(backArrow, ft, tt);
        pt.play();
        
    }    
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void initData(){
        
        int i = 3;
        
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
        String nomeProg = "Nessun Progetto";
        int numeroProd = 0;
        String saldo = "0.0€";

        try{
            nomeProg = delegate.getProgetto((Dipendente) application.getUtente()).getNome();
            saldo = delegate.getProgetto((Dipendente) application.getUtente()).getFormatSaldo();
            numeroProd = delegate.getCarrello((Dipendente) application.getUtente()).numeroProdotti();
            carrello = delegate.getCarrello((Dipendente) application.getUtente());
        }
        catch(NoIDMatchException e){
            Logger.getLogger(FXMLCarrelloController.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(NoQueryMatchException e){
            //Logger.getLogger(FXMLOrdineDettaglioController.class.getName()).log(Level.FINE, null, e);
        }
        finally{
            nomeProgetto.setText(nomeProg);
            saldoProgetto.setText(saldo);
        }
        
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
            
            totale.setText(carrello.getFormatTotale());
            
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
                } catch (NoIDMatchException ex) {
                    Logger.getLogger(FXMLCarrelloController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                final Button rimuovi = new Button("Rimuovi");
                rimuovi.getStyleClass().add("remove-button");
                
                rimuovi.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        delegate.removeProdotto(carrello, prodotto);
                        rimuovi.setDisable(true);
                        rimuovi.setText("Rimosso!");
                        totale.setText(carrello.getFormatTotale());
                        if(carrello.getListaProdotti().isEmpty())
                            ordinaButton.setDisable(true);
                    }
                });
                
                
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
                try{
                    Set<Ordine> listaOrdini = delegate.generaOrdini((Dipendente) application.getUtente());
                    application.ordina(listaOrdini);
                } catch (NoIDMatchException e){
                    System.err.println(e.getMessage());
                } catch (NoQueryMatchException e){
                    System.err.println("WUT");
                }
            }
        });
    }
}
