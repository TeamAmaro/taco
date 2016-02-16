package it.unisalento.taco.view;

import it.unisalento.taco.business.UtenteDelegate;
import it.unisalento.taco.controller.*;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoSuchUserException;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Utente;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
    
    private Stage primaryStage;
    private Utente utente;
    
    private final double MINIMUM_WINDOW_WIDTH = 900.0;
    private final double MINIMUM_WINDOW_HEIGHT = 650.0;
    
    public static void main(String[] args) { 
        launch(args); 
    } 
    
    @Override public void start(Stage primaryStage) { 
        this.primaryStage = primaryStage;
        
        this.primaryStage.setTitle("Galaxy Express");
        
        this.primaryStage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        this.primaryStage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        
        this.primaryStage.getIcons().add(new Image("it/unisalento/taco/view/img/icona.png"));
        
        this.primaryStage.setWidth(MINIMUM_WINDOW_WIDTH);
        this.primaryStage.setHeight(MINIMUM_WINDOW_HEIGHT);
        
        loginLevel();
       
        this.primaryStage.show();
    } 
 
    private void loginLevel(){
        try {
            FXMLLoginController login = (FXMLLoginController) cambiaLivello("fxml/FXMLLogin.fxml");
            login.setApplication(this);
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void catalogoLevel(){
        try {
            FXMLCatalogoController catalogo = (FXMLCatalogoController) cambiaLivello("fxml/FXMLCatalogo.fxml");
            catalogo.setApplication(this);
            catalogo.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void prodottoLevel(Prodotto prodotto){
        try{
            FXMLProdottoController prodottoController = (FXMLProdottoController) cambiaLivello("fxml/FXMLProdotto.fxml");
            prodottoController.setApplication(this);
            prodottoController.setProdotto(prodotto);
            prodottoController.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void carrelloLevel(){
        try {
            FXMLCarrelloController carrello = (FXMLCarrelloController) cambiaLivello("fxml/FXMLCarrello.fxml");
            carrello.setApplication(this);
            carrello.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void progettoLevel(Progetto progetto){
        try {
            FXMLProgettoController progettoController = (FXMLProgettoController) cambiaLivello("fxml/FXMLProgetto.fxml");
            progettoController.setApplication(this);
            progettoController.setProgetto(progetto);
            progettoController.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void progettoElencoLevel(){
        try {
            FXMLProgettoElencoController progettoElenco = (FXMLProgettoElencoController) cambiaLivello("fxml/FXMLProgettoElenco.fxml");
            progettoElenco.setApplication(this);
            progettoElenco.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void magazzinoLevel(){
        try {
            FXMLMagazzinoController magazzino = (FXMLMagazzinoController) cambiaLivello("fxml/FXMLMagazzino.fxml");
            magazzino.setApplication(this);
            magazzino.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void inventarioLevel(Magazzino magazzino){
        try {
            FXMLInventarioController inventario = (FXMLInventarioController) cambiaLivello("fxml/FXMLInventario.fxml");
            inventario.setMagazzino(magazzino);
            inventario.setApplication(this);
            inventario.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void spedizioneLevel(Magazzino magazzino){
        try {
            FXMLSpedizioneController spedizione = (FXMLSpedizioneController) cambiaLivello("fxml/FXMLSpedizione.fxml");
            spedizione.setMagazzino(magazzino);
            spedizione.setApplication(this);
            spedizione.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }
    
    private void ordineDettaglioLevel(Set<Ordine> listaOrdini){
        try {
            FXMLOrdineDettaglioController ordineDettaglio = (FXMLOrdineDettaglioController) cambiaLivello("fxml/FXMLOrdineDettaglio.fxml");
            ordineDettaglio.setListaOrdini(listaOrdini);
            ordineDettaglio.setApplication(this);
            ordineDettaglio.initData();
        } catch (Exception ex) {
            errorDialog(ex);
        }
    }

    public Utente getUtente(){
        return utente;
    }
    
    public Stage getStage(){
        return primaryStage;
    }
    
    public boolean setUtente(String email, String password){
        try {
            utente = UtenteDelegate.getInstance().login(email, password);
            return true;
        }
        catch (NoSuchUserException | NoIDMatchException e) {
            return false;
        }
    }
    
    public void dipendenteView(){
        catalogoLevel();
    }
    
    public void capoProgettoView(){
        progettoElencoLevel();
    }
    
    public void magazziniereView(){
        magazzinoLevel();
    }
        
    public void logout(){
        utente = null;
        loginLevel();
    }
    
    public void getDetails(Prodotto prodotto){
        prodottoLevel(prodotto);
    }
    
    public void getDetails(Progetto progetto){
        progettoLevel(progetto);
    }
    
    public void getInventario(Magazzino magazzino){
        inventarioLevel(magazzino);
    }
    
    public void getSpedizione(Magazzino magazzino){
        spedizioneLevel(magazzino);
    }
    
    public void getCarrello(){
        carrelloLevel();
    }
    
    private Initializable cambiaLivello(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        Pane root;
        try {
            root = (Pane) loader.load(in);
        } finally {
            in.close();
        } 
        //Non so perché, ma ci sono 15 px di differenza ..
        Scene scene = new Scene(root, primaryStage.getWidth() - 15, primaryStage.getHeight() - 15);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        return (Initializable) loader.getController();
    }

    public void ordina(Set<Ordine> listaOrdini) {
        ordineDettaglioLevel(listaOrdini);
    }
    
    private void errorDialog(Exception ex){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Attenzione!");        
        Label msg = new Label(ex.getMessage());
        msg.setWrapText(true);
        Button okBtn = new Button("Ok");

        okBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                dialog.close();
                utente = null;
                loginLevel();
            }
        });

        dialog.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override public void handle(WindowEvent arg0) {
                dialog.close();
                utente = null;
                loginLevel();
            }
        });

        GridPane grid = new GridPane();
        grid.add(msg, 0, 0);
        grid.add(okBtn, 0, 1);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20.0);
        Scene scene = new Scene(grid, 400, 300);
        dialog.setScene(scene);
        dialog.show();
        dialog.setResizable(false);
    }
    
}
