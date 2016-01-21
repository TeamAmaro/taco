/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import it.unisalento.taco.business.MagazziniereDelegate;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLSpedizioneController implements Initializable {

    private Main application;
    private MagazziniereDelegate delegate = MagazziniereDelegate.getInstance();
    private Magazzino magazzino;
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void setMagazzino(Magazzino magazzino){
        this.magazzino = magazzino;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // EMPTY
    }    
    
    public void initData(){
        
    }
}
