package it.unisalento.taco.controller;

import it.unisalento.taco.business.CapoProgettoDelegate;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.view.Main;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;


public class FXMLProgettoController implements Initializable {

    @FXML Label logout;
    @FXML Label nomeClient;
    @FXML HBox topLeft;
    @FXML Label nomeProgetto;
    @FXML Label budget;
    @FXML Label saldo;
    
    @FXML AnchorPane anchorPane;
    
    @FXML Label moreButton;
    
    @FXML HBox backArrowBox;
    
    @FXML TextField dipFiltro;
    @FXML Button dipButton;
    
    @FXML TextField ordFiltro;
    @FXML Button ordButton;
    
    @FXML ImageView backArrow;
    
    @FXML private TableView<Dipendente> tableViewDip;
    @FXML private TableColumn<Dipendente, String> nomeCol;
    @FXML private TableColumn<Dipendente, String> cognomeCol;
    @FXML private TableColumn<Dipendente, String> emailCol;
    @FXML private TableColumn<Dipendente, String> sedeCol;
    
    @FXML private TableView<Ordine> tableViewOrd;
    @FXML private TableColumn<Ordine, Double> spesaCol;
    @FXML private TableColumn<Ordine, String> dipendenteCol;
    @FXML private TableColumn<Ordine, Integer> codiceCol;
    @FXML private TableColumn<Ordine, String> prodottoCol;
    @FXML private TableColumn<Ordine, String> quantitaCol;
    @FXML private TableColumn<Ordine, String> dataCol;
    
    private ObservableList<Ordine> ordineData = FXCollections.observableArrayList();
    private ObservableList<Dipendente> dipendenteData = FXCollections.observableArrayList();
    
    private Main application;
    private Progetto progetto;
    private CapoProgettoDelegate delegate = CapoProgettoDelegate.getInstance();
    private Set<Ordine> listaOrdini;
    private Set<Dipendente> listaDipendenti;
    
    private int offset;
    private int numOrdini;
    
    public void setApplication(Main application){
        this.application = application;
    }
    
    public void setProgetto(Progetto progetto){
        this.progetto = progetto;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void initData(){
        initInfo();
        initMenu();
        initDipTable();
        initOrdTable();
        initAnimation();
    }
    
    private void initInfo(){
        offset = 0;
        String nome = application.getUtente().getNome();
        String cognome = application.getUtente().getCognome();
        nomeClient.setText(nome + " " + cognome);
        nomeProgetto.setText(progetto.getNome());
        budget.setText(progetto.getFormatBudget());
        saldo.setText(progetto.getFormatSaldo());
    }
    
    private void initMenu(){
    
        moreButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                offset += 5;
                try {
                    listaOrdini.addAll(delegate.getListaOrdini(progetto, offset));
                } catch (NoIDMatchException ex) {
                    application.errorDialog(ex);
                }
                ordineData.clear();
                ordineData.addAll(listaOrdini);
                if (numOrdini - offset < 5){
                    moreButton.setVisible(false);
                    moreButton.setManaged(false);
                }
                
            }
        });
        
        backArrowBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.capoProgettoView();
            }
        });
        
        logout.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                application.logout();
            }
        });
        
        dipButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                String search = dipFiltro.getText().toLowerCase();
                String nomeIntero;
                for(Dipendente d : listaDipendenti){
                    nomeIntero = d.getNome().toLowerCase() + " " + d.getCognome().toLowerCase();
                    if(!nomeIntero.contains(search)){
                        dipendenteData.remove(d);
                    }
                    else if (!dipendenteData.contains(d))
                        dipendenteData.add(d);
                }
            }
        });
       
        ordButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent arg0) {
                String search = ordFiltro.getText();
                try{
                    Integer.parseInt(search);
                    for(Ordine o : listaOrdini){
                        if(!Integer.toString(o.getCodice()).contains(search)){
                            ordineData.remove(o);
                        }
                        else if (!ordineData.contains(o))
                            ordineData.add(o);
                    }
                }catch(NumberFormatException e){
                    //Non fare nulla
                }
            }
        });
    }
    private void initDipTable(){
        
        listaDipendenti = progetto.getListaDipendenti();
        dipendenteData.addAll(listaDipendenti);
        
        nomeCol.setCellValueFactory(new PropertyValueFactory<Dipendente, String>("nome"));
        cognomeCol.setCellValueFactory(new PropertyValueFactory<Dipendente, String>("cognome"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Dipendente, String>("email"));
        
        sedeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dipendente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dipendente, String> p) {
                return new SimpleStringProperty(p.getValue().getSede().toString());
            }
        });
        
        tableViewDip.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Dipendente>() {
            @Override
            public void changed(ObservableValue<? extends Dipendente> observable, Dipendente oldValue, Dipendente newValue) {
                if(newValue != null){
                for(Ordine o : listaOrdini){
                   if(o.getDipendente().getId() != newValue.getId()){
                       ordineData.remove(o);
                   }
                   else if (!ordineData.contains(o))
                       ordineData.add(o);
                    }
                }      
            }
        });
        
        tableViewDip.setItems(dipendenteData);
        tableViewDip.setMaxHeight(200.0);
        tableViewDip.setMaxWidth(300.0);
        tableViewDip.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    private void initOrdTable(){
        try {
            if((numOrdini = delegate.chiediNumeroOrdini(progetto)) < 5){
                listaOrdini = delegate.getListaOrdini(progetto);
                anchorPane.getChildren().remove(moreButton);
            }
            else{
                listaOrdini = delegate.getListaOrdini(progetto, offset);
            }
            ordineData.addAll(listaOrdini);
        } catch (NoIDMatchException e){
            application.errorDialog(e);
        }
                
        codiceCol.setCellValueFactory(new PropertyValueFactory<Ordine, Integer>("codice"));
        
        dipendenteCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new SimpleStringProperty(p.getValue().getDipendente().getNome() + " " + p.getValue().getDipendente().getCognome());
            }
        });
        
        spesaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Ordine, Double> p) {
                Double tot = p.getValue().getTotale() + p.getValue().getSpesaSpedizione();
                return new ReadOnlyObjectWrapper<>(tot);
            }
        });
        
        prodottoCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new SimpleStringProperty(p.getValue().getListaNomiProdotti());
            }
        });
        
        quantitaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new SimpleStringProperty(p.getValue().getListaQuantitaProdotti());
            }
        });
        
        dataCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ordine, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ordine, String> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getReadableData());
            }
        });
        
        tableViewOrd.setItems(ordineData);
        tableViewOrd.setMaxHeight(200.0);
        tableViewOrd.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    
    }
    
    private void initAnimation(){
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setFromValue(0.3f);
        ft.setToValue(1.0f);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        tt.setFromX(-100f);
        tt.setToX(0);
        ParallelTransition pt = new ParallelTransition(backArrow, ft, tt);
        pt.play();
    }
}
