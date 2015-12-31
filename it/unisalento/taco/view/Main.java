package it.unisalento.taco.view;

import it.unisalento.taco.business.UtenteDelegate;
import it.unisalento.taco.controller.*;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoSuchUserException;
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
    private Utente client;
    
    private final double MINIMUM_WINDOW_WIDTH = 400.0;
    private final double MINIMUM_WINDOW_HEIGHT = 400.0;
    
    public static void main(String[] args) { 
        launch(args); 
    } 
    
    @Override public void start(Stage primaryStage) { 
        stage = primaryStage;
        
        stage.setTitle("Taco");
        
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        stage.getIcons().add(new Image("it/unisalento/taco/view/img/tacoicon.ico"));
        carrelloLevel();
        stage.show(); 
    } 
 
    private void loginLevel(){
        try {
            FXMLLoginController login = (FXMLLoginController) cambiaLivello("fxml/FXMLLogin.fxml", 400.0, 600.0);
            login.setApp(this);
            stage.setResizable(false);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void catalogoLevel(){
        try {
            FXMLCatalogoController catalogo = (FXMLCatalogoController) cambiaLivello("fxml/FXMLCatalogo.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void prodottoLevel(){
        try {
            FXMLProdottoController prodotto = (FXMLProdottoController) cambiaLivello("fxml/FXMLProdotto.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void carrelloLevel(){
        try {
            FXMLCarrelloController carrello = (FXMLCarrelloController) cambiaLivello("fxml/FXMLCarrello.fxml");
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
    
    
    public Utente getClient(){
        return client;
    }
    
    
    public boolean login(String email, String password){
        try {
            client = UtenteDelegate.getInstance().login(email, password);
            return true;
        }
        catch (NoSuchUserException | NoIDMatchException e) {
            return false;
        }
    }
    
    public void logout(){
        client = null;
        loginLevel();
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
        Scene scene = new Scene(root, 800.0, 600.0);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
    private Initializable cambiaLivello(String fxml, double width, double height) throws Exception {
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
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
}
