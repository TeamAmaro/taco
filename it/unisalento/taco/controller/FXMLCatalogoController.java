package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    @FXML Label queryMessage;

    @FXML GridPane gridPane;
    @FXML AnchorPane content;

    @FXML VBox leftMenu;

    @FXML ImageView iconaCarrello;
    
    private ScrollPane scrollLeft;
    private GridPane gridRight;
    private VBox vbLeft;

    private Main application;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();


    @Override public void initialize(URL location, ResourceBundle resources){

        //Definisce lo ScrollPane e il VBox per ospitare gli elementi già da subito e una volta sola.
        scrollLeft = new ScrollPane();
        vbLeft = new VBox();
        gridRight = new GridPane();

        vbLeft.setAlignment(Pos.CENTER);
        vbLeft.setSpacing(10.0);
        vbLeft.setPadding(new Insets(10.0,20.0,10.0,10.0));
        
        gridRight.setAlignment(Pos.TOP_CENTER);
        gridRight.setVgap(10.0);
        gridRight.setHgap(20.0);
        gridRight.setPadding(new Insets(20.0,20.0,20.0,20.0));

        scrollLeft.getStyleClass().add("green");
        scrollLeft.getStyleClass().add("scrollpane");
        gridRight.getStyleClass().add("yellow");
        scrollLeft.setContent(vbLeft);

    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void initData() {

        application.getStage().widthProperty().addListener(new ChangeListener<Number>(){
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                if(content.getChildren().contains(scrollLeft) && newSceneWidth.intValue() < 1130){
                    content.setRightAnchor(scrollLeft, 0.0);
                    gridRight.setVisible(false);
                }
                else{
                    content.setRightAnchor(scrollLeft, 400.0);
                    gridRight.setVisible(true);
                }
            }
        });


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
            Logger.getLogger(FXMLOrdineDettaglioController.class.getName()).log(Level.SEVERE, null, e);
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
                    if(listaProdotti.isEmpty()){
                        queryMessage.setText("Nessuna corrispondenza trovata per \"" + searchBar.getText() +"\"");
                    }else {
                        displayProdotti(listaProdotti);
                    }
                }
                catch (NoIDMatchException e){
                    System.err.println(e.getMessage());
                }
            }
        });
        
        iconaCarrello.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getCarrello();
            }
        });
    }



    private void displayProdotti(Set<Prodotto> listaProdotti){

        if(content.getChildren().contains(gridPane))
            content.getChildren().remove(gridPane);

        if(content.getChildren().contains(scrollLeft))
            content.getChildren().remove(scrollLeft);

        if(content.getChildren().contains(gridRight))
            content.getChildren().remove(gridRight);

        vbLeft.getChildren().clear();

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
                    showOnRightPanel(p);
                }
            });

            Label prezzo = new Label(Double.toString(p.getPrezzo()) + "€");
            prezzo.getStyleClass().add("prezzo-prodotto");
            Label descrizione = new Label();
            Label dettagli = new Label("Dettagli");

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

            dettagli.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    application.getDetails(p);
                }
            });

            valori.getChildren().addAll(nome, prezzo, descrizione, dettagli);
            hb.getChildren().addAll(iv, valori);
            vbLeft.getChildren().add(hb);
        }
    
        content.getChildren().add(scrollLeft);
        content.getChildren().add(gridRight);

        content.setTopAnchor(scrollLeft,50.0);
        content.setLeftAnchor(scrollLeft, 200.0);
        content.setBottomAnchor(scrollLeft, 0.0);

        content.setTopAnchor(gridRight,50.0);
        content.setLeftAnchor(gridRight, 700.0);
        content.setBottomAnchor(gridRight, 0.0);
        content.setRightAnchor(gridRight, 0.0);

        if(application.getStage().getWidth() < 1130.0){
            content.setRightAnchor(scrollLeft, 0.0);
            gridRight.setVisible(false);
        }
        else{
            content.setRightAnchor(scrollLeft, 400.0);
            gridRight.setVisible(true);
        }

    }


    private void showOnRightPanel(final Prodotto prodotto){

        gridRight.getChildren().clear();
        
        
        ImageView iv = new ImageView(new Image("it/unisalento/taco/view/img/thumbnail.jpg"));
        //iv.setFitHeight(200.0);
        iv.setPreserveRatio(true);

        Label nome = new Label(prodotto.getNome());
        nome.getStyleClass().add("nome-prodotto");
        
        Label prezzo = new Label(Double.toString(prodotto.getPrezzo()) + "€");
        prezzo.getStyleClass().add("prezzo-prodotto");
        
        Label produttore = new Label(prodotto.getProduttore().nome());
        produttore.getStyleClass().add("produttore");
        
        Label descrizione = new Label(prodotto.getDescrizione());
        descrizione.getStyleClass().add("descrizione");
        
        final TextField quantita = new TextField();
        quantita.setMaxWidth(100);
        final Button add = new Button("Aggiungi");
        gridRight.add(iv, 0, 0, 1, 5);
        gridRight.add(nome, 1, 0);
        gridRight.add(prezzo,1, 1);
        gridRight.add(new Label("Produttore"), 1, 2);
        gridRight.add(produttore, 1, 3);
        gridRight.add(new Label("Descrizione"), 0, 6);
        gridRight.add(descrizione, 0, 7);
        gridRight.add(new Label("Inserire quantità da ordinare (es. 20):"), 0, 8);
        gridRight.add(quantita, 1, 8);
        gridRight.add(add, 1, 9);
        
        if(application.getStage().getWidth() < 1130.0){
            content.setRightAnchor(scrollLeft, 400.0);
            application.getStage().setWidth(1130.0);
            gridRight.setVisible(true);
            application.getStage().centerOnScreen();
        }

        add.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                int q = Integer.parseInt(quantita.getText());
                if(q > 0){
                    try{
                        Carrello carrello = delegate.getCarrello((Dipendente) application.getUtente());
                        delegate.addProdotto(carrello, prodotto, q);
                        add.setDisable(true);
                        add.setText("Aggiunto!");
                    } catch (NoIDMatchException e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        });
    }
}

