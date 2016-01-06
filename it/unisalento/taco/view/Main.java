package it.unisalento.taco.view;

import it.unisalento.taco.business.UtenteDelegate;
import it.unisalento.taco.controller.*;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoSuchUserException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Utente;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    
    private Stage stage;
    private Utente utente;
    
    private final double MINIMUM_WINDOW_WIDTH = 800.0;
    private final double MINIMUM_WINDOW_HEIGHT = 600.0;
    
    public static void main(String[] args) { 
        launch(args); 
    } 
    
    @Override public void start(Stage primaryStage) { 
        stage = primaryStage;
        
        stage.setTitle("Taco");
        
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        
        stage.getIcons().add(new Image("it/unisalento/taco/view/img/tacoicon.ico"));
        
        stage.setWidth(800.0);
        stage.setHeight(600.0);
        
        loginLevel();
        
        stage.show(); 
    } 
 
    private void loginLevel(){
        try {
            FXMLLoginController login = (FXMLLoginController) cambiaLivello("fxml/FXMLLogin.fxml");
            login.setApplication(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void catalogoLevel(){
        try {
            FXMLCatalogoController catalogo = (FXMLCatalogoController) cambiaLivello("fxml/FXMLCatalogo.fxml");
            catalogo.setApplication(this);
            catalogo.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void prodottoLevel(Prodotto prodotto){
        try{
            FXMLProdottoController prodottoController = (FXMLProdottoController) cambiaLivello("fxml/FXMLProdotto.fxml");
            prodottoController.setApplication(this);
            prodottoController.setProdotto(prodotto);
            prodottoController.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void carrelloLevel(){
        try {
            FXMLCarrelloController carrello = (FXMLCarrelloController) cambiaLivello("fxml/FXMLCarrello.fxml");
            carrello.setApplication(this);
            carrello.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void progettoLevel(){
        try {
            FXMLProgettoController progetto = (FXMLProgettoController) cambiaLivello("fxml/FXMLProgetto.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void progettoElencoLevel(){
        try {
            FXMLProgettoElencoController progettoElenco = (FXMLProgettoElencoController) cambiaLivello("fxml/FXMLProgettoElenco.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void magazzinoLevel(){
        try {
            FXMLMagazzinoController magazzino = (FXMLMagazzinoController) cambiaLivello("fxml/FXMLMagazzino.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void inventarioLevel(){
        try {
            FXMLInventarioController carrello = (FXMLInventarioController) cambiaLivello("fxml/FXMLInventario.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ordineDettaglioLevel(){
        try {
            FXMLOrdineDettaglioController ordineDettaglio = (FXMLOrdineDettaglioController) cambiaLivello("fxml/FXMLOrdineDettaglio.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ordineElencoLevel(){
        try {
            FXMLOrdineElencoController ordineElenco = (FXMLOrdineElencoController) cambiaLivello("fxml/FXMLOrdineElenco.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Utente getUtente(){
        return utente;
    }
    
    public Stage getStage(){
        return stage;
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
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        return (Initializable) loader.getController();
    }
    
}
