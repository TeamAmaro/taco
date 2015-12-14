package it.unisalento.taco.business;

import it.unisalento.taco.dao.MagazzinoDAO;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Sede;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class GeneratoreOrdini{
		
    private static GeneratoreOrdini instance;
    public static GeneratoreOrdini getInstance(){
        if(instance == null)
            instance = new GeneratoreOrdini();
        return instance;
    }
    public static void close(){
        instance = null;
    }
    private GeneratoreOrdini(){}
    
    private List<Ordine> listaOrdini = new ArrayList<>();
    private Map<Magazzino,Map<Prodotto, Integer>> magazzinoPerProdotti= new LinkedHashMap<>();
    private Map<Prodotto, Integer> quantitaProdPerMag = new LinkedHashMap<>();
    
    
    public void generaOrdini(Dipendente dipendente){
        //Recupero il contenuto del carrello
        Map<Prodotto,Integer> contenutoCarrello = dipendente.getCarrello().getListaProdotti();
        //Ottengo la sede del dipendente
        Sede sede = dipendente.getSede();
        //Cerco il magazzino più vicino al dipendente
        Magazzino magazzinoVicino = MagazzinoDAO.getInstance().getMagazzino(sede);
        //Per ogni prodotto e relativa quantita
        for (Map.Entry<Prodotto, Integer> e : contenutoCarrello.entrySet()){
            int quantita = MagazzinoDAO.getInstance().getQuantita(magazzinoVicino, e.getKey());
        }
        

        /*
         * Controlla elenco prodotti;  mappaProdotti
         * Controlla dipendente;	Client.DipendenteIstance()
         * Controlla progetto;	Client.DipendenteIstance().getProgetto();
         * Controlla i magazzini per i prodotti;	MagazzinoDelegate.cercaProdotto(prodotto) (foreach prodotto)
         * Crea una lista di ordini, raggruppando i prodotti per magazzino; Dividi la mappa in elenco di magazzini e prodotti
         * Aggiungi eventuali spese di spedizione;	calcolaTotale per ordine
         * Crea un file da stampare con la distinta;   documentoDAO stampa ricevuta
         * Associa gli ordini al progetto
         */
    }
}
