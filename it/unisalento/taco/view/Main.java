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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;
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
    private Scene lastScene;
    
    private final double MINIMUM_WINDOW_WIDTH = 800.0;
    private final double MINIMUM_WINDOW_HEIGHT = 600.0;
    
    public static void main(String[] args) { 
        launch(args); 
    } 
    
    @Override public void start(Stage primaryStage) { 
        stage = primaryStage;
        
        stage.setTitle("Galaxy Express");
        
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        
        stage.getIcons().add(new Image("it/unisalento/taco/view/img/icona.png"));
        
        stage.setWidth(MINIMUM_WINDOW_WIDTH);
        stage.setHeight(MINIMUM_WINDOW_HEIGHT);
        
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
    
    private void progettoLevel(Progetto progetto){
        try {
            FXMLProgettoController progettoController = (FXMLProgettoController) cambiaLivello("fxml/FXMLProgetto.fxml");
            progettoController.setApplication(this);
            progettoController.setProgetto(progetto);
            progettoController.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void progettoElencoLevel(){
        try {
            FXMLProgettoElencoController progettoElenco = (FXMLProgettoElencoController) cambiaLivello("fxml/FXMLProgettoElenco.fxml");
            progettoElenco.setApplication(this);
            progettoElenco.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void magazzinoLevel(){
        try {
            FXMLMagazzinoController magazzino = (FXMLMagazzinoController) cambiaLivello("fxml/FXMLMagazzino.fxml");
            magazzino.setApplication(this);
            magazzino.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void inventarioLevel(Magazzino magazzino){
        try {
            FXMLInventarioController inventario = (FXMLInventarioController) cambiaLivello("fxml/FXMLInventario.fxml");
            inventario.setMagazzino(magazzino);
            inventario.setApplication(this);
            inventario.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void spedizioneLevel(Magazzino magazzino){
        try {
            FXMLSpedizioneController spedizione = (FXMLSpedizioneController) cambiaLivello("fxml/FXMLSpedizione.fxml");
            spedizione.setMagazzino(magazzino);
            spedizione.setApplication(this);
            spedizione.initData();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ordineDettaglioLevel(Set<Ordine> listaOrdini){
        try {
            FXMLOrdineDettaglioController ordineDettaglio = (FXMLOrdineDettaglioController) cambiaLivello("fxml/FXMLOrdineDettaglio.fxml");
            ordineDettaglio.setListaOrdini(listaOrdini);
            ordineDettaglio.setApplication(this);
            ordineDettaglio.initData();
            
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
        lastScene = stage.getScene();
        prodottoLevel(prodotto);
    }
    
    public void getDetails(Progetto progetto){
        lastScene = stage.getScene();
        progettoLevel(progetto);
    }
    
    public void getInventario(Magazzino magazzino){
        lastScene = stage.getScene();
        inventarioLevel(magazzino);
    }
    
    public void getSpedizione(Magazzino magazzino){
        lastScene = stage.getScene();
        spedizioneLevel(magazzino);
    }
    
    public void getCarrello(){
        lastScene = stage.getScene();
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
        Scene scene = new Scene(root, stage.getWidth() - 15, stage.getHeight() - 15);
        stage.setScene(scene);
        stage.centerOnScreen();
        return (Initializable) loader.getController();
    }

    public void lastView() {
        stage.setScene(lastScene);
        stage.sizeToScene();
        stage.centerOnScreen();

    }

    public void ordina(Set<Ordine> listaOrdini) {
        ordineDettaglioLevel(listaOrdini);
    }
    
}
