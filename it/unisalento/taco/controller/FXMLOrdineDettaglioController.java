package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.exceptions.InsufficientFundException;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.view.Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;


public class FXMLOrdineDettaglioController implements Initializable {

    @FXML Label logout;
    @FXML ImageView leftLogo;
    @FXML ImageView iconaCarrello;
    
    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;
    @FXML Label carrello;

    
    @FXML ImageView iv;
    @FXML HBox topLeft;
    
    @FXML Button acquistaButton;
    @FXML Label titoloLabel;
    @FXML Label saldoLabel;
    @FXML Label totaleLabel;
    @FXML Label errorLabel;
    @FXML GridPane gridPane;
    
    /*
    *   Table View sembra poco adeguato a trattare
        tipi di dato omogeneo. 
    */
    
    @FXML private TableView<Ordine> tableView;
    @FXML private TableColumn<Ordine, Integer> codice;
    @FXML private TableColumn<Ordine, String> nomeProdotto;
    @FXML private TableColumn<Ordine, String> quantitaProdotto;
    @FXML private TableColumn<Ordine, String> prezzoProdotto;
    @FXML private TableColumn<Ordine, Double> totale;
    @FXML private TableColumn<Ordine, Double> spedizione;
    @FXML private TableColumn<Ordine, String> magazzino;


    private ObservableList<Ordine> ordineData = FXCollections.observableArrayList();

    private Main application;
    private Set<Ordine> listaOrdini;
    private DipendenteDelegate delegate = DipendenteDelegate.getInstance();

    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void setListaOrdini(Set<Ordine> listaOrdini){
        this.listaOrdini = listaOrdini;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        iv = new ImageView(new Image("it/unisalento/taco/view/img/back.jpg"));
        iv.setFitHeight(50.0);
        iv.setPreserveRatio(true);
        
        topLeft.getChildren().add(0, iv);

        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setFromValue(0.3f);
        ft.setToValue(1.0f);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        tt.setFromX(-100f);
        tt.setToX(0);
        
        ParallelTransition pt = new ParallelTransition(iv, ft, tt);
        pt.play();
        
    }    
    
    public void initData(){
        
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
        String nomeProg = "Nessun Progetto";
        int numeroProd = 0;
        String saldo = "0.0€";

        try{
            nomeProg = delegate.getProgetto((Dipendente) application.getUtente()).getNome();
            saldo = delegate.getProgetto((Dipendente) application.getUtente()).getFormatSaldo();
            numeroProd = delegate.getCarrello((Dipendente) application.getUtente()).numeroProdotti();
        }
        catch(NoIDMatchException e){
            Logger.getLogger(FXMLOrdineDettaglioController.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(NoQueryMatchException e){
            //Logger.getLogger(FXMLOrdineDettaglioController.class.getName()).log(Level.FINE, null, e);
        }
        finally{
            titoloLabel.setText("Ordine per " + nomeProg);
            nomeProgetto.setText(nomeProg);
            saldoProgetto.setText(saldo);
            saldoLabel.setText(saldo);
            carrello.setText(Integer.toString(numeroProd));
        }
        
        //Aggiungere totale al label
        double totaleOrdine = 0;

        for(Ordine o : listaOrdini){
            totaleOrdine += o.getTotale();
            totaleOrdine += o.getSpesaSpedizione();
        }
        
        NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
        formatoEuro.setMinimumFractionDigits( 2 );
        formatoEuro.setMaximumFractionDigits( 2 );
        formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
        totaleLabel.setText(formatoEuro.format(totaleOrdine));
        
        initTable();
        
        iconaCarrello.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getCarrello();
            }
        });
        
        acquistaButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                
                try{
                    delegate.acquista((Dipendente) application.getUtente(), listaOrdini);
                } catch (InsufficientFundException ex) { 
                    acquistaButton.setDisable(true);
                    errorLabel.setText("Fondi insufficenti per completare l'acquisto.");
                    errorLabel.setWrapText(true);
                    return;
                }
                
                FileChooser fileChooser = new FileChooser();
  
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                File file = fileChooser.showSaveDialog(application.getStage());
                
                if(file != null){
                    try {
                        
                        FileWriter fileWriter = new FileWriter(file);
                        
                        char desinenza ;
                        
                        if(listaOrdini.size() > 1)
                            desinenza = 'i';
                        else
                            desinenza = 'e';
                        StringBuilder ricevuta = new StringBuilder();
                        ricevuta.append("Ricevuta ordin" + desinenza + " di " + application.getUtente().getNome() + " " + application.getUtente().getCognome() + System.lineSeparator());
                        int i = 1;
                        
                        for(Ordine o : listaOrdini){
                            ricevuta.append("ORDINE #" + i + System.lineSeparator());
                            ricevuta.append("CODICE: " + o.getCodice() + System.lineSeparator());
                            ricevuta.append("DIPENDENTE: " + o.getDipendente().getNome() +" " + o.getDipendente().getCognome() + System.lineSeparator());
                            ricevuta.append("PROGETTO: " + o.getProgetto().getNome() + " - " + o.getProgetto().getCapoProgetto().getNome() +" " + o.getProgetto().getCapoProgetto().getCognome() + System.lineSeparator());
                            ricevuta.append("DATA: " + o.getReadableData() + System.lineSeparator());
                            ricevuta.append("PRODOTTI: " + System.lineSeparator());
                            
                            for(Map.Entry<Prodotto,Integer> e : o.getListaProdotti().entrySet())
                                ricevuta.append(e.getKey().getPrezzo() + "€ " + e.getKey().getNome()+ " x " + e.getValue() + System.lineSeparator());
                            
                            ricevuta.append("MAGAZZINO: " + o.getMagazzino().getNome() + " (" + o.getMagazzino().getSede().nome() +  ")" + System.lineSeparator());
                            ricevuta.append("TOTALE PARZIALE: " + o.getTotale() + System.lineSeparator());
                            ricevuta.append("COSTO SPEDIZIONE: " + o.getSpesaSpedizione() + System.lineSeparator());
                            
                            i++;
                        }
                        
                        double totale = 0;

                        NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
                        formatoEuro.setMinimumFractionDigits( 2 );
                        formatoEuro.setMaximumFractionDigits( 2 );
                        formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
                        
                        for(Ordine o : listaOrdini){
                            totale += o.getTotale();
                            totale += o.getSpesaSpedizione();
                        }
                        
                        ricevuta.append("TOTALE :" + formatoEuro.format(totale));
                        
                        fileWriter.write(ricevuta.toString());
                        fileWriter.close();
                        
                        gridPane.getChildren().clear();
                        Label result = new Label();
                        result.setText("Ordine effettuato. La ricevuta è stata salvata al percorso\n" + file.getAbsolutePath() + "\n"
                                + "Ricorda di stampare la ricevuta e presentarla al magazziniere al momento della consegna della merce.\n"
                                + "Continua a usare TACO!");
                        result.setWrapText(true);
                        gridPane.add(result, 0, 0);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLOrdineDettaglioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        iv.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.lastView();
            }
        });
        
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
        
    }
    
    private void initTable(){
        
        ordineData.addAll(listaOrdini);
        
        codice.setCellValueFactory(new PropertyValueFactory<Ordine, Integer>("codice"));
        spedizione.setCellValueFactory(new PropertyValueFactory<Ordine, Double>("spesaSpedizione"));
        totale.setCellValueFactory(new PropertyValueFactory<Ordine, Double>("totale"));
        
        nomeProdotto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new SimpleStringProperty(p.getValue().getListaNomiProdotti());
            }
        });
        
        quantitaProdotto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new SimpleStringProperty(p.getValue().getListaQuantitaProdotti());
            }
        });
        
        prezzoProdotto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new SimpleStringProperty(p.getValue().getListaPrezziProdotti());
            }
        });
        
        magazzino.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new SimpleStringProperty(p.getValue().getMagazzino().getSede().nome());
            }
        });
        
        tableView.setItems(ordineData);
        
    }
}
