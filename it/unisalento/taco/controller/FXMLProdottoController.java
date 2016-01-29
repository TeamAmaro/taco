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
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


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
    @FXML ImageView leftLogo;
    @FXML ImageView thumbnail;
    
    @FXML HBox topLeft;
    
    @FXML ImageView iv;
    @FXML Button addButton;
    @FXML TextField quantita;
    
    @FXML ImageView iconaCarrello;
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void setProdotto(Prodotto prodotto){
        this.prodotto = prodotto;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        iv = new ImageView(new Image("it/unisalento/taco/view/img/back.jpg"));
        iv.setFitHeight(50.0);
        iv.setPreserveRatio(true);
        
        topLeft.getChildren().add(0, iv);

        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setFromValue(0.3f);
        ft.setToValue(1.0f);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        tt.setFromX(-100f);
        tt.setToX(0);
        
        ParallelTransition pt = new ParallelTransition(iv, ft, tt);
        pt.play();
        
        
    }    

    public void initData(){
        
        thumbnail.setImage(new Image("it/unisalento/taco/view/img/" + prodotto.getImmagine()));
        String nomeProg = "Nessun Progetto";
        int numeroProd = 0;
        String saldo = "0.0€";
        
        try{
            nomeProg = delegate.getProgetto((Dipendente) application.getUtente()).getNome();
            saldo = delegate.getProgetto((Dipendente) application.getUtente()).getFormatSaldo();
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
            saldoProgetto.setText(saldo);
            carrello.setText(Integer.toString(numeroProd));
            nomeProdotto.setText(prodotto.getNome());
            prezzoProdotto.setText(prodotto.getFormatPrezzo());
            descrizione.setText(prodotto.getDescrizione());
            prodProdotto.setText(prodotto.getProduttore().toString());
        }
        
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        leftLogo.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    application.dipendenteView();
                }
        });
        
        iv.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    application.lastView();
                }
        });

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    int q = Integer.parseInt(quantita.getText());
                if(q > 0){
                    try{
                        Carrello carrello = delegate.getCarrello((Dipendente) application.getUtente());
                        delegate.addProdotto(carrello, prodotto, q);
                        addButton.setDisable(true);
                        addButton.setText("Aggiunto!");
                    } catch (NoIDMatchException e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        });
        
        iconaCarrello.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getCarrello();
            }
        });
    }


}
