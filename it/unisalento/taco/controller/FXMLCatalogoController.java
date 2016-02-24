package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoProgettoException;
import it.unisalento.taco.exception.NoQueryMatchException;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class FXMLCatalogoController extends AnchorPane implements Initializable{

    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label numCarrello;

    @FXML HBox gridSearchBox;
    @FXML TextField gridSearchBar;

    @FXML HBox searchBox;
    @FXML TextField searchBar;
    
    @FXML Label logout;
    @FXML Label queryMessage;
    @FXML ImageView leftLogo;
    @FXML ImageView gridImage;

    @FXML GridPane gridPane;
    @FXML AnchorPane content;
    
    @FXML Label gridMessage;

    @FXML VBox leftMenu;

    @FXML StackPane iconaCarrello;
    @FXML StackPane stackSearch;
    
    @FXML Label quantitaMsg;
    
    private ScrollPane scrollLeft;
    private GridPane gridRight;
    private VBox vbLeft;

    private Main application;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();
    
    private Carrello carrello;


    @Override public void initialize(URL location, ResourceBundle resources){

        //Definisce lo ScrollPane e il VBox per ospitare gli elementi già da subito e una volta sola.
        scrollLeft = new ScrollPane();
        vbLeft = new VBox();
        gridRight = new GridPane();
        gridImage = new ImageView(new Image("it/unisalento/taco/view/img/rocket.png"));
        gridMessage = new Label("Clicca su un prodotto per visualizzarne i dettagli.");
        gridMessage.getStyleClass().add("grid-message");
        gridMessage.setWrapText(true);
        gridMessage.setMaxWidth(170.0);
        vbLeft.setAlignment(Pos.CENTER);
        vbLeft.setSpacing(10.0);
        vbLeft.setPadding(new Insets(10.0,20.0,10.0,10.0));
        
        gridRight.setAlignment(Pos.TOP_CENTER);
        gridRight.setVgap(10.0);
        gridRight.setHgap(20.0);
        gridRight.setPadding(new Insets(20.0,20.0,20.0,20.0));
        gridRight.add(gridMessage, 0, 1);
        gridRight.add(gridImage, 0, 0);
        gridRight.setAlignment(Pos.CENTER);

        scrollLeft.getStyleClass().add("scrollpane");
        gridRight.getStyleClass().add("grid-right");
        scrollLeft.setContent(vbLeft);

    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void initData() throws NoIDMatchException, NoProgettoException {

        initInfo();
        initMenu();
        initListener();
        
    }

    private void initInfo() throws NoIDMatchException, NoProgettoException{
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
        String nomeProg;
        int numeroProd = 0;
        String saldo;

        nomeProg = delegate.getProgetto((Dipendente) application.getUtente()).getNome();
        saldo = delegate.getProgetto((Dipendente) application.getUtente()).getFormatSaldo();
        carrello = delegate.getCarrello((Dipendente) application.getUtente());
        numeroProd = carrello.numeroProdotti();


        nomeProgetto.setText(nomeProg);
        saldoProgetto.setText(saldo);
        numCarrello.setText(Integer.toString(numeroProd));


    }
    
    private void initMenu(){
        
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

        for(final Node label : leftMenu.getChildren()){
            if(label == leftLogo)
                continue;
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
                        //Salta il prodotto
                    }
                }
            });
        }

        gridSearchBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                
                if(gridSearchBar.getText().isEmpty())
                    return;

                try {
                    Set<Prodotto> listaProdotti = delegate.cercaProdotti(gridSearchBar.getText());
                    if(listaProdotti.isEmpty()){
                        queryMessage.setText("Nessuna corrispondenza trovata per \"" + gridSearchBar.getText() +"\"");
                    }else {
                        displayProdotti(listaProdotti);
                    }
                }
                catch (NoIDMatchException e){
                    //Salta il prodotto
                }
            }
        });
        
        gridSearchBar.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override public void handle(KeyEvent arg0) {
                if(arg0.getCode() == KeyCode.ENTER){
                    if(gridSearchBar.getText().isEmpty() || !gridSearchBar.isFocused())
                        return;
                    try {
                        Set<Prodotto> listaProdotti = delegate.cercaProdotti(gridSearchBar.getText());
                        if(listaProdotti.isEmpty()){
                            queryMessage.setText("Nessuna corrispondenza trovata per \"" + gridSearchBar.getText() +"\"");
                        }else {
                            displayProdotti(listaProdotti);
                        }
                    }
                    catch (NoIDMatchException e){
                        //Salta il prodotto 
                    }
                }
            }
        });
        
        searchBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {

                if(searchBar.getText().isEmpty())
                    return;

                try {
                    Set<Prodotto> listaProdotti = delegate.cercaProdotti(searchBar.getText());
                    if(!listaProdotti.isEmpty()){
                        displayProdotti(listaProdotti);
                    }
                }
                catch (NoIDMatchException e){
                    //Salta il prodotto
                }
            }
        });
        
        searchBar.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override public void handle(KeyEvent arg0) {
                if(arg0.getCode() == KeyCode.ENTER){
                    
                    if(searchBar.getText().isEmpty() || !searchBar.isFocused())
                        return;
                    try {
                        Set<Prodotto> listaProdotti = delegate.cercaProdotti(searchBar.getText());
                        if(!listaProdotti.isEmpty()){
                            displayProdotti(listaProdotti);
                        }
                    }
                    catch (NoIDMatchException e){
                        //Salta il prodotto
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
    
    private void initListener(){
        application.getStage().widthProperty().addListener(new ChangeListener<Number>(){
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                if(content.getChildren().contains(scrollLeft) && newSceneWidth.intValue() < 1130){
                    scrollLeft.setMinWidth(newSceneWidth.intValue() - 265);
                    scrollLeft.setFitToWidth(true);
                    gridRight.setVisible(false);
                }
                else
                {
                    scrollLeft.setMinWidth(400.0);
                    scrollLeft.setMaxWidth(400.0);
                    scrollLeft.setFitToWidth(true);
                    gridRight.setVisible(true);
                }
            }
        });
    }

    private void displayProdotti(Set<Prodotto> listaProdotti) throws NoIDMatchException{

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
            ImageView iv = new ImageView(new Image("it/unisalento/taco/view/img/" + p.getImmagine()));
            iv.setFitHeight(100.0);
            iv.setPreserveRatio(true);
            iv.getStyleClass().add("icona-prodotto");

            VBox valori = new VBox();
            Label nome = new Label(p.getNome());
            nome.getStyleClass().add("nome-prodotto");

            nome.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    showOnRightPanel(p);
                }
            });
            
            iv.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override public void handle(MouseEvent arg0) {
                    showOnRightPanel(p);
                }
            });

            Label prezzo = new Label(p.getFormatPrezzo());
            prezzo.getStyleClass().add("prezzo-prodotto");
            Label descrizione = new Label();
            Label dettagli = new Label("Dettagli");
            dettagli.getStyleClass().add("dettagli-prodotto");
            
            Label annoDiProduzione = new Label();
            annoDiProduzione.setText(Integer.toString(p.getAnnoDiProduzione()));

            try{
                int quantita = delegate.chiediDisponibilita((Dipendente) application.getUtente(), p);
                if(quantita <= 10){
                    if(quantita == 0){
                        descrizione.setText("Non disponibile presso la tua sede");
                        descrizione.getStyleClass().add("non-disponibile");
                    } else {
                        descrizione.setText("Solo " + quantita + " in magazzino!");
                        descrizione.getStyleClass().add("esaurimento");
                    }
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

            valori.getChildren().addAll(nome, prezzo, annoDiProduzione, descrizione, dettagli);
            hb.getChildren().addAll(iv, valori);
            vbLeft.getChildren().add(hb);
        }
    
        content.getChildren().add(scrollLeft);
        content.getChildren().add(gridRight);

        content.setTopAnchor(scrollLeft,50.0);
        content.setLeftAnchor(scrollLeft, 250.0);
        content.setBottomAnchor(scrollLeft, 0.0);

        content.setTopAnchor(gridRight,50.0);
        content.setLeftAnchor(gridRight, 650.0);
        content.setBottomAnchor(gridRight, 0.0);
        content.setRightAnchor(gridRight, 0.0);

        if(application.getStage().getWidth() < 1130.0){
            gridRight.setVisible(false);
            scrollLeft.setMinWidth(635.0);
            scrollLeft.setFitToWidth(true);
        }
        else{
            scrollLeft.setMaxWidth(400.0);
            scrollLeft.setMinWidth(400.0);
            scrollLeft.setFitToWidth(true);
            gridRight.setVisible(true);
        }
    }


    private void showOnRightPanel(final Prodotto prodotto){

        gridRight.getChildren().clear();
        gridRight.setAlignment(Pos.TOP_CENTER);
        
        ImageView iv = new ImageView(new Image("it/unisalento/taco/view/img/" + prodotto.getImmagine()));
        iv.setFitHeight(250.0);
        iv.setPreserveRatio(true);

        Label nome = new Label(prodotto.getNome());
        nome.getStyleClass().add("nome-prodotto-grid");
        
        Label prezzo = new Label(prodotto.getFormatPrezzo());
        prezzo.getStyleClass().add("prezzo-prodotto-grid");
        
        Label produttore = new Label(prodotto.getProduttore().nome());
        produttore.getStyleClass().add("produttore-prodotto-grid");
        
        Label descrizione = new Label(prodotto.getDescrizione());
        descrizione.getStyleClass().add("descrizione-grid");
        
        final TextField quantita = new TextField();
        quantita.setMaxWidth(100);
        quantita.getStyleClass().add("quantita-field");
        final Button add = new Button("Aggiungi");
        add.getStyleClass().add("add-button");
        
        quantitaMsg = new Label();
        
        gridRight.add(iv, 0, 0, 1, 5);
        gridRight.add(nome, 1, 0);
        gridRight.add(prezzo,1, 1);
        
        gridRight.add(produttore, 1, 2);
        
        Label descrizioneLabel = new Label("Descrizione");
        descrizioneLabel.getStyleClass().add("right-label");
        
        gridRight.add(descrizioneLabel, 0, 6);
        gridRight.add(descrizione, 0, 7);
        
        Label quantitaLabel = new Label("Quantità da ordinare");
        quantitaLabel.getStyleClass().add("right-label");
        
        quantitaMsg.getStyleClass().add("error-message");
         
        gridRight.add(quantitaLabel, 0, 8);
        gridRight.add(quantitaMsg, 0, 9);
        gridRight.add(quantita, 1, 8);
        gridRight.add(add, 1, 9);
        if(application.getStage().getWidth() < 1130.0){
            scrollLeft.setMinWidth(400.0);
            scrollLeft.setMaxWidth(400.0);
            scrollLeft.setFitToWidth(true);
            application.getStage().setWidth(1130.0);
            gridRight.setVisible(true);
            application.getStage().centerOnScreen();
            content.setLeftAnchor(gridRight, 650.0);
        }

        add.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                try {
                    int q = Integer.parseInt(quantita.getText());
                    if(q > 0){
                        delegate.addProdotto(carrello, prodotto, q);
                        add.setDisable(true);
                        add.setText("Aggiunto!");
                        quantitaMsg.setText("");
                        numCarrello.setText(Integer.toString(carrello.numeroProdotti()));
                    }
                } catch(NumberFormatException e){
                    quantitaMsg.setText("Inserisci una quantità valida.");
                }
            }
        });
    }
}

