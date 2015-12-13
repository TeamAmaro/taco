package it.unisalento.taco.business;

import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
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
    private Map<Magazzino,Prodotto[]> magazzinoPerListaProdotti = new LinkedHashMap<>();
    
    public void generaOrdini(Dipendente dipendente){
        Map<Prodotto,Integer> contenutoCarrello = dipendente.getCarrello().getListaProdotti();
        





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
