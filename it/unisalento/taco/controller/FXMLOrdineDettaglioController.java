package it.unisalento.taco.controller;

import it.unisalento.taco.business.DipendenteDelegate;
import it.unisalento.taco.business.DocumentManager;
import it.unisalento.taco.exception.InsufficientFundException;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoProgettoException;
import it.unisalento.taco.exception.NoQueryMatchException;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.view.Main;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;


public class FXMLOrdineDettaglioController implements Initializable {

    @FXML Label logout;
    
    @FXML AnchorPane anchorPane;
    
    @FXML Label nomeClient;
    @FXML Label nomeProgetto;
    @FXML Label saldoProgetto;

    @FXML HBox topLeft;
    
    @FXML Label prcButton;
    @FXML Label scsButton;
    
    @FXML Button acquistaButton;
    @FXML Label titoloLabel;
    @FXML Label saldoLabel;
    @FXML Label totaleLabel;
    @FXML Label errorLabel;
    @FXML GridPane gridPane;
    
    @FXML HBox backArrowBox;
    
    /*
    *   Table View è inadeguato a trattare
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
        //Nulla da fà
    }    
    
    public void initData() throws NoIDMatchException, NoProgettoException{
        initInfo();
        initContent();
        initMenu();
        initTable();
        initAnimation();
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
        tableView.setMaxHeight(200.0);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    private void initInfo() throws NoIDMatchException, NoProgettoException{
        nomeClient.setText(application.getUtente().getNome() + " " + application.getUtente().getCognome());
        String nomeProg = "Nessun Progetto";
        int numeroProd = 0;
        String saldo = "0.0€";

        nomeProg = delegate.getProgetto((Dipendente) application.getUtente()).getNome();
        saldo = delegate.getProgetto((Dipendente) application.getUtente()).getFormatSaldo();

        titoloLabel.setText("Ordine per " + nomeProg);
        nomeProgetto.setText(nomeProg);
        saldoProgetto.setText(saldo);
        saldoLabel.setText(saldo);
    }
    
    private void initContent(){
         
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
        
    }
    
    private void initAnimation(){
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setFromValue(0.3f);
        ft.setToValue(1.0f);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        tt.setFromX(-100f);
        tt.setToX(0);
        
        ParallelTransition pt = new ParallelTransition(backArrowBox, ft, tt);
        pt.play();
    }
    
    private void initMenu(){

        
        acquistaButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {                
                
                final GridPane grid = new GridPane();
                
                anchorPane.getChildren().add(grid);
                anchorPane.setBottomAnchor(grid, 0.0);
                anchorPane.setTopAnchor(grid, 0.0);
                anchorPane.setRightAnchor(grid, 0.0);
                anchorPane.setLeftAnchor(grid, 0.0);
                
                grid.getStyleClass().add("dialog-blur");
                HBox hb = new HBox();
                hb.getStyleClass().add("dialog-box");
                grid.setAlignment(Pos.CENTER);
                
                Button okBtn = new Button("Ok");
                Button cncBtn = new Button("Annulla");
                
                okBtn.getStyleClass().add("ok-button");
                cncBtn.getStyleClass().add("cnc-button");
                
                String msg = "L'acquisto sarà effettuato su " + nomeProgetto.getText();
                
                Label warn = new Label(msg);
                
                grid.add(hb, 0, 0, 5, 4);
                grid.add(warn, 1, 1, 3, 1);
                grid.add(cncBtn, 1, 2);
                grid.add(okBtn, 3, 2);
                                
                grid.setVgap(30.0);
                grid.setHgap(20.0);
                
                okBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        anchorPane.getChildren().remove(grid);
                        try{
                            delegate.acquista((Dipendente) application.getUtente(), listaOrdini);
                        } catch (InsufficientFundException ex) { 
                            acquistaButton.setDisable(true);
                            errorLabel.setText("Fondi insufficenti per completare l'acquisto.");
                            errorLabel.setWrapText(true);
                            return;
                        } catch (NoProgettoException | NoIDMatchException ex) {
                            application.errorDialog(ex);
                        }
                        
                        File file = null;
                        
                        FileChooser fileChooser = new FileChooser();
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
                        fileChooser.getExtensionFilters().add(extFilter);
                        fileChooser.setInitialFileName("ricevuta.pdf");
                        fileChooser.setTitle("Salva con nome");
                        
                        while(file == null){
                            file = fileChooser.showSaveDialog(application.getStage());
                        }
                        if(file != null){
                            try {
                                DocumentManager.getInstance().generaRicevuta(listaOrdini, file);
                               
                                gridPane.getChildren().clear();
                                Label result = new Label();
                                Label titolo = new Label("Ordine effettuato!");
                                result.setText("La ricevuta è stata salvata al percorso\n" + file.getAbsolutePath() + "\n"
                                        + "Ricorda di stampare la ricevuta e presentarla al magazziniere al momento della consegna della merce.\n"
                                        + "Continua a usare Galaxy Express!");
                                result.setWrapText(true);
                                ImageView img = new ImageView(new Image("it/unisalento/taco/view/img/shuttle2.png"));
                                
                                Label lbl = new Label("Torna al catalogo!");
                                lbl.getStyleClass().add("label-blue");
                                
                                lbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
                                    @Override public void handle(MouseEvent arg0){
                                        application.dipendenteView();
                                    }
                                });
                                titolo.getStyleClass().add("titolo");
                                
                                gridPane.add(titolo, 0, 0, 2, 1);
                                gridPane.add(result, 0, 1);
                                gridPane.add(lbl, 0, 2);
                                gridPane.add(img, 0, 3);                                
                                
                            } catch (IOException | COSVisitorException ex) {
                                application.errorDialog(ex);
                            }
                        }
                    }
                });
                
                cncBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override public void handle(MouseEvent arg0) {
                        anchorPane.getChildren().remove(grid);
                    }
                });
                
            }
        });
        
        backArrowBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.getCarrello();
            }
        });
        
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
    }
}
