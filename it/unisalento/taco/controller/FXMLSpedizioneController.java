/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import it.unisalento.taco.business.MagazziniereDelegate;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FXMLSpedizioneController implements Initializable {

    private Main application;
    private MagazziniereDelegate delegate = MagazziniereDelegate.getInstance();
    private Magazzino magazzino;
    
    @FXML Label nomeClient;
    @FXML Label spedizioni;
    @FXML Label inventario;
    @FXML Label logout;
    @FXML HBox leftLogoBox;
    @FXML GridPane gridPane;
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void setMagazzino(Magazzino magazzino){
        this.magazzino = magazzino;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Non può fare nulla
    }    
    
    public void initData(){
        initInfo();
        initMenu();
        initContent();
    }
    
    private void initContent(){
        try{
            Set<Ordine> listaOrdini = delegate.chiediOrdini((Magazziniere) application.getUtente());
            if(listaOrdini.isEmpty()){
                gridPane.getChildren().clear();
                Label message = new Label("Non ci sono ordini da spedire.");
                gridPane.add(message, 0, 0);
            }
            else {
                
                int i = 1;
                
                for(final Ordine o : listaOrdini){

                    Label codice = new Label(Integer.toString(o.getCodice()));
                    Label dipendente = new Label(o.getDipendente().getNome() + " " + o.getDipendente().getCognome());
                    Label sede = new Label(o.getDipendente().getSede().nome());  

                    int j = i;
                    for(Map.Entry<Prodotto,Integer> pq : o.getListaProdotti().entrySet())
                    {
                        Label nomeProdotto = new Label(pq.getKey().getNome());
                        Label quantitaProdotto = new Label(Integer.toString(pq.getValue()));
                        gridPane.add(nomeProdotto, 1, j);
                        gridPane.add(quantitaProdotto, 2, j);
                        j++;
                    }

                    gridPane.add(codice, 0, i);
                    gridPane.add(dipendente, 3, i);
                    gridPane.add(sede, 4, i);

                    final Button spedisciButton = new Button("Spedisci");
                    spedisciButton.getStyleClass().add("spedisci-button");

                    spedisciButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
                        @Override public void handle(MouseEvent arg0) {
                            delegate.spedisciOrdine(o);
                            spedisciButton.setText("Spedito!");
                            spedisciButton.setDisable(true);
                        }
                    });

                    gridPane.add(spedisciButton, 5, i);
                    i = j;
                    i++;
                }
            }
        }catch(NoQueryMatchException e){
            //Non fare niente
        } catch (NoIDMatchException ex) {
            Logger.getLogger(FXMLSpedizioneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    private void initMenu(){
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        spedizioni.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getSpedizione(magazzino);
            }
        });
        
        inventario.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getInventario(magazzino);
            }
        });
        
        leftLogoBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.magazziniereView();
            }
        });
    }
    
    private void initInfo(){
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
    }
    
}
