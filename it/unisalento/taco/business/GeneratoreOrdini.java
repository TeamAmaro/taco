package it.unisalento.taco.business;

import it.unisalento.taco.dao.MagazzinoDAO;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Sede;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GeneratoreOrdini{
		
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

    public Map<Magazzino,Map<Prodotto,Integer>> magazzinoPerProdotto(Dipendente dipendente){
        //Recupero il contenuto del carrello
        Map<Prodotto,Integer> contenutoCarrello = dipendente.getCarrello().getListaProdotti();
        //Ottengo la sede del dipendente
        Sede sede = dipendente.getSede();
        //Cerco il magazzino più vicino al dipendente
        Magazzino magVicino = MagazzinoDAO.getInstance().getMagazzino(sede);
        //Mappo la quantita di ogni prodotto nel magazzino corrispondente. La struttura è un po' artificiosa ma,
        // [Magazzino, [Prodotto, Integer][Prodotto, Integer]....]
        //Per ogni magazzino ci sono i seguenti prodotti con le rispettive quantita
        //Tuttavia, non è necessario chiedere a magazzini esterni la disponibilità del prodotto se 
        //E' gia disponbile nel magazzino della sede, (magVicino)
        Map<Magazzino,Map<Prodotto,Integer>> magPerProd = new LinkedHashMap<>();
        Map<Prodotto,Integer> prodPerQuant = new LinkedHashMap<>();
        
        //Per ogni prodotto e relativa quantita nel carrello
        for (Map.Entry<Prodotto, Integer> e : contenutoCarrello.entrySet()){
            Prodotto prod = e.getKey(); //Prodotto nel carrello
            int quantCar = e.getValue(); //Quantita nel carrello
            //Prelevo la quantita
            int quantMag = MagazzinoDAO.getInstance().getQuantita(magVicino, prod); //Quantita nel magazzino
            prodPerQuant.put(prod, quantMag); //Aggiungo la quantita
            magPerProd.put(magVicino, prodPerQuant); //Aggiungo nel magazzino vicino
            //Se la quantita di prodotti nel magazzino più vicino non soddisfa la richiesta
            if (quantMag < quantCar){
                //Chiedo i magazzini esterni che hanno il prodotto
                Set<Magazzino> magConProd = MagazzinoDAO.getInstance().cercaProdotto(prod);
                //Rimuovo il magazzino vicino
                magConProd.remove(magVicino);
                //Chiedo la quantita di prodotto nei magazzini esterni
                for(Magazzino magExt : magConProd){
                    int quantMagExt = MagazzinoDAO.getInstance().getQuantita(magExt, prod);
                    prodPerQuant.put(prod, quantMagExt);
                    magPerProd.put(magExt, prodPerQuant);
                }
            } 
        }
        return magPerProd;
    }
    
    public Set<Ordine> generaOrdini(Dipendente dipendente, Map<Magazzino,Map<Prodotto,Integer>> magPerProd){
        //Lista degli ordini
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        //Per ogni magazzino si fa un ordine diverso
        for(Map.Entry<Magazzino,Map<Prodotto,Integer>> mag : magPerProd.entrySet()){
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            Ordine ordine = new Ordine(dipendente, dipendente.getProgetto(), mag.getKey(), mag.getValue(), date);
        }
        
        return listaOrdini;
    }
}
