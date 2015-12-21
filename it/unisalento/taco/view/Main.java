package it.unisalento.taco.view;

import it.unisalento.taco.business.UtenteDelegate;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{
    
    private Stage stage;
    private Utente client;
    
    public static void main(String[] args) { 
        launch(args); 
    } 
    
    @Override public void start(Stage primaryStage) { 
        
        stage = primaryStage;
        
        stage.setTitle("Taco Login");
        stage.setMinWidth(ViewUtils.MINIMUM_WINDOW_WIDTH.val());
        stage.setMinHeight(ViewUtils.MINIMUM_WINDOW_HEIGHT.val());
        
        loginLevel();
        stage.show(); 
    } 
 
    private void loginLevel(){
        try {
            FXMLLoginController login = (FXMLLoginController) replaceSceneContent("FXMLLogin.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void catalogoLevel(){
        //Da implementare.
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
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
}
