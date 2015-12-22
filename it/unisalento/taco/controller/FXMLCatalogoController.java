/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


public class FXMLCatalogoController extends AnchorPane implements Initializable{
    private Main application;
    
    public void setApp(Main application){
        this.application = application;
    }
    @Override public void initialize(URL location, ResourceBundle resources){
        //Da implementare
    }
}
