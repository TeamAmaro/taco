/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisalento.taco.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class FXMLCatalogoController extends AnchorPane implements Initializable{
    
    @FXML Label categoriaA;
    @FXML Label categoriaB;
    @FXML Label categoriaC;
    @FXML Label categoriaD;
    @FXML Label categoriaE;
    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label carrello;
    
    @Override public void initialize(URL location, ResourceBundle resources){
        //Da implementare
    }
}
