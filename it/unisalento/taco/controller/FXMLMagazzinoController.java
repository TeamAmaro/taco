package it.unisalento.taco.controller;

import it.unisalento.taco.business.MagazziniereDelegate;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoMagazzinoException;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class FXMLMagazzinoController implements Initializable {
    
    @FXML AnchorPane anchorPane;
    
    @FXML Label nomeClient;
    @FXML Label spedizioni;
    @FXML Label inventario;
    @FXML Label nomeMagazzino;
    @FXML Label notificaSpedizione;
    @FXML Label notificaInventario;
    @FXML Label logout;
    @FXML HBox leftLogoBox;
    
    private Main application;
    private MagazziniereDelegate delegate = MagazziniereDelegate.getInstance();
    private Magazzino magazzino;
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initData() throws NoMagazzinoException, NoIDMatchException{
        initInfo();
        initMenu();
    }
    
    private void initInfo() throws NoMagazzinoException, NoIDMatchException{
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
        
        magazzino = MagazziniereDelegate.getInstance().getMagazzino((Magazziniere) application.getUtente());
        nomeMagazzino.setText(magazzino.getNome());
        Set<Ordine> listaOrdini = delegate.chiediOrdini((Magazziniere) application.getUtente());
        notificaSpedizione.setText("Hai " + listaOrdini.size() + " ordini da spedire.");
        boolean check = false;
        StringBuilder sb = new StringBuilder();
        sb.append("Esaurimento scorte dei seguenti prodotti:").append(System.lineSeparator());
        for(Map.Entry<Prodotto,Integer> k : magazzino.getInventario().entrySet()){
            if(k.getValue() < 10){
                check = true;
                sb.append(k.getKey().getNome()).append(" : ").append(k.getValue()).append(" in magazzino.").append(System.lineSeparator());
            }    
        }
        if(check == false)
            notificaInventario.setText("Nessun prodotto in esaurimento scorte.");
        else
            notificaInventario.setText(sb.toString());
    }

    private void initMenu(){
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        spedizioni.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getSpedizione(magazzino);
            }
        });
        
        inventario.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getInventario(magazzino);
            }
        });
        
        leftLogoBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.magazziniereView();
            }
        });
    }
}
