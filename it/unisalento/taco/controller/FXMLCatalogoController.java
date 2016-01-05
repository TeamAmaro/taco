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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class FXMLCatalogoController extends AnchorPane implements Initializable{
    
    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label carrello;
    
    @FXML ImageView searchIcon;
    @FXML TextField searchBar;
    
    @FXML Label logout;
    
    @FXML GridPane gridPane;
    @FXML AnchorPane content;
    
    @FXML VBox leftMenu;
    
    private ScrollPane sp;
    private VBox vb;
    
    private Main application;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();
    
    
    @Override public void initialize(URL location, ResourceBundle resources){
        
        //Definisce lo ScrollPane e il VBox per ospitare gli elementi già da subito e una volta sola.
        sp = new ScrollPane();
        vb = new VBox();
        
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(10.0);
        vb.setPadding(new Insets(10.0,20.0,10.0,10.0));
        
        sp.getStyleClass().add("green");
        sp.setContent(vb);        
        
    }
    
    public void setApplication(Main application) {
        this.application = application;
    }

    public void initData() {
        
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
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
            nomeProgetto.setText(nomeProg);
            saldoProgetto.setText(Double.toString(saldo) +"€");
            carrello.setText(Integer.toString(numeroProd));
        }
        
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        for(final Node label : leftMenu.getChildren()){
            label.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    Categoria cat;
                    switch(label.getId()){
                        case "categoriaA":
                            cat = Categoria.CATEGORIA_A;
                            break;
                        case "categoriaB":
                            cat = Categoria.CATEGORIA_B;
                            break;
                        case "categoriaC":
                            cat= Categoria.CATEGORIA_C;
                            break;
                        case "categoriaD":
                            cat = Categoria.CATEGORIA_D;
                            break;
                        default :
                            cat = Categoria.CATEGORIA_0;
                            break;
                   }
                    try {
                        Set<Prodotto> listaProdotti = Prodotto.cerca(cat);
                        displayProdotti(listaProdotti);
                    }
                    catch(NoIDMatchException e){
                        System.err.println(e.getMessage());
                    }
                }
            });
        }
        
        searchIcon.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {

                if(searchBar.getText().isEmpty())
                    return;
                
                try {
                    Set<Prodotto> listaProdotti = delegate.cercaProdotti(searchBar.getText());
                    displayProdotti(listaProdotti);
                }
                catch (NoIDMatchException e){
                    System.err.println(e.getMessage());
                }
            }
        });     
    }
    
    
    private void displayProdotti(Set<Prodotto> listaProdotti){
        
        if(content.getChildren().contains(gridPane))
            content.getChildren().remove(gridPane);
        
        if(content.getChildren().contains(sp))
            content.getChildren().remove(sp);
        
        vb.getChildren().clear();
        
        for(final Prodotto p : listaProdotti){

            HBox hb = new HBox();

            hb.setAlignment(Pos.CENTER_LEFT);
            hb.setPrefHeight(100.0);
            hb.setSpacing(20.0);
            hb.setMaxWidth(350.0);

            ImageView iv = new ImageView(new Image("it/unisalento/taco/view/img/thumbnail.jpg"));
            iv.setFitHeight(100.0);
            iv.setPreserveRatio(true);

            VBox valori = new VBox();
            Label nome = new Label(p.getNome());
            nome.getStyleClass().add("nome-prodotto");
            
            nome.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    application.getDetails(p);
                }
            });
            
            Label prezzo = new Label(Double.toString(p.getPrezzo()) + "€");
            prezzo.getStyleClass().add("prezzo-prodotto");
            Label descrizione = new Label();

            try{
                int quantita = delegate.chiediDisponibilità((Dipendente) application.getUtente(), p);
                if(quantita < 10){
                    descrizione.setText("In esaurimento");
                    descrizione.getStyleClass().add("esaurimento");
                }
                else if(quantita == 0){
                    descrizione.setText("Non disponibile");
                    descrizione.getStyleClass().add("non-disponibile");
                }
                else{ 
                    descrizione.setText("Disponibile");
                    descrizione.getStyleClass().add("disponibile");
                }
            }
            catch(NoQueryMatchException e){
                descrizione.setText("Impossibile reperire disponibilità");
                descrizione.getStyleClass().add("non-disponibile");
            }
            
            valori.getChildren().addAll(nome, prezzo, descrizione);
            hb.getChildren().addAll(iv, valori);
            vb.getChildren().add(hb);
        }
        
        content.getChildren().add(sp);
        
        content.setTopAnchor(sp,50.0);
        content.setLeftAnchor(sp, 200.0);
        content.setBottomAnchor(sp, 0.0);
        System.out.println(application.getStage().getWidth());
        if(application.getStage().getWidth() < 1000.0)
            content.setRightAnchor(sp, 0.0);
        else{
            content.setRightAnchor(sp, 500.0);
        }
    }
    

}
