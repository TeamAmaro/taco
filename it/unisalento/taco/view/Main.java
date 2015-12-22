package it.unisalento.taco.view;

import it.unisalento.taco.business.UtenteDelegate;
import it.unisalento.taco.controller.FXMLCatalogoController;
import it.unisalento.taco.controller.FXMLLoginController;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    
    private Stage stage;
    private Utente client;
    
    private final double MINIMUM_WINDOW_WIDTH = 500.0;
    private final double MINIMUM_WINDOW_HEIGHT = 450.0;
    
    public static void main(String[] args) { 
        launch(args); 
    } 
    
    @Override public void start(Stage primaryStage) { 
        stage = primaryStage;
        
        stage.setTitle("Taco Login");
        
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        
        loginLevel();
        stage.show(); 
    } 
 
    private void loginLevel(){
        try {
            FXMLLoginController login = (FXMLLoginController) replaceSceneContent("fxml/FXMLLogin.fxml");
            login.setApp(this);
            //stage.setResizable(false);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void catalogoLevel(){
        try {
            FXMLCatalogoController catalogo = (FXMLCatalogoController) replaceSceneContent("fxml/FXMLCatalogo.fxml");
            catalogo.setApp(this);
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
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
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
        Scene scene = new Scene(root, 500.0, 450.0);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
}
