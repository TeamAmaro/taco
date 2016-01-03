/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.InsufficientFundException;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CarrelloDelegate {
    
    private static CarrelloDelegate instance;
    public static CarrelloDelegate getInstance(){
        if(instance == null)
            instance = new CarrelloDelegate();
        return instance;
    }
    private CarrelloDelegate(){};
    
    public void addProdotto(Carrello carrello, Prodotto prodotto, int quantita){
        carrello.addProdotto(prodotto, quantita);
    }
    
    //Questo metodo riduce la quantità di un prodotto nel carrello del valore passato,
    //se il valore è maggiore o uguale a quello presente, il prodotto verrà completamente eliminato
    public void removeProdotto(Carrello carrello, Prodotto prodotto, int quantita){
        carrello.removeProdotto(prodotto, quantita);
    }
    
    
    public Set<Ordine> generaOrdini(Dipendente dipendente) throws NoIDMatchException, NoQueryMatchException{
        
        Map<Magazzino,Map<Prodotto,Integer>> magPerProd = new LinkedHashMap<>();
        Map<Prodotto,Integer> prodPerQuant = new LinkedHashMap<>();
        
        try{
            //Recupero il contenuto del carrello
            Carrello carrello = Carrello.getByID(dipendente.getID());
            Map<Prodotto,Integer> contenutoCarrello  = carrello.getListaProdotti();
            //Ottengo la sede del dipendente
            Sede sede = dipendente.getSede();
            //Cerco il magazzino più vicino al dipendente
            Magazzino magVicino = Magazzino.getMagazzino(sede);
            //Mappo la quantita di ogni prodotto nel magazzino corrispondente. La struttura è un po' artificiosa ma,
            // [Magazzino, [Prodotto, Integer][Prodotto, Integer]....]
            //Per ogni magazzino ci sono i seguenti prodotti con le rispettive quantita
            //Tuttavia, non è necessario chiedere a magazzini esterni la disponibilità del prodotto se 
            //E' gia disponbile nel magazzino della sede, (magVicino)
            //Per ogni prodotto e relativa quantita nel carrello
            for (Map.Entry<Prodotto, Integer> e : contenutoCarrello.entrySet()){
                Prodotto prod = e.getKey(); //Prodotto nel carrello
                int quantCar = e.getValue(); //Quantita nel carrello
                //Prelevo la quantita
                int quantMag = Magazzino.getQuantita(magVicino, prod); //Quantita nel magazzino
                prodPerQuant.put(prod, quantMag); //Aggiungo la quantita
                magPerProd.put(magVicino, prodPerQuant); //Aggiungo nel magazzino vicino
                //Se la quantita di prodotti nel magazzino più vicino non soddisfa la richiesta
                if (quantMag < quantCar){
                    //Chiedo i magazzini esterni che hanno il prodotto
                    Set<Magazzino> magConProd = Magazzino.cercaProdotto(prod);
                    //Rimuovo il magazzino vicino dalla lista dei risultati
                    magConProd.remove(magVicino);
                    int differenza = quantCar - quantMag;
                    int quantMagExt;
                    //Chiedo la quantita di prodotto nei magazzini esterni
                    //Assumendo che la lista dei magazzini è già presentata in ordine decrescente
                    for(Magazzino magExt : magConProd){
                        quantMagExt = Magazzino.getQuantita(magExt, prod);
                        if(quantMagExt < differenza){
                            prodPerQuant.put(prod, quantMagExt);
                            magPerProd.put(magExt, prodPerQuant);
                            differenza -= quantMagExt;
                        }
                        else {
                            prodPerQuant.put(prod, differenza);
                            magPerProd.put(magExt, prodPerQuant);
                            //Il break dovrebbe essere riferito al for più interno
                            break;
                        }
                    }
                }
            }
            
            //Lista degli ordini
            Set<Ordine> listaOrdini = new LinkedHashSet<>();
            //Per ogni magazzino si fa un ordine diverso
            //Recupero l'ora di creazione
            Date date = new Date();
            long unixTime = date.getTime();

            for(Map.Entry<Magazzino,Map<Prodotto,Integer>> mag : magPerProd.entrySet()){
                Ordine ordine = new Ordine(dipendente, Progetto.getProgetto(dipendente), mag.getKey(), unixTime, mag.getValue());
                listaOrdini.add(ordine);
            }
            
            return listaOrdini;
        }
        catch(NoQueryMatchException | NoIDMatchException e){
            throw e;
        }
    }

    public void acquista(Dipendente dipendente, Set<Ordine> listaOrdini) throws InsufficientFundException{
        
        //Controlla possibilità del dipendente di effettuare l'ordine
        //Calcolare il TOTALE
        double totale = 0;
        
        //Per ogni ordine
        for(Ordine val : listaOrdini){
            //aggiungi il totale di ogni ordine
            totale += val.getTotale();
            //aggiungi la spesa di spedizione
            totale += val.getSpesaSpedizione();
        }
        
        try{
            //prendi il progetto e il saldo
            Progetto progetto = Progetto.getProgetto(dipendente);
            double saldo = progetto.getSaldo();
            //Se il progetto non ha fondi sufficienti interrompi l'acquisto
            if(saldo <= totale){ 
                throw new InsufficientFundException("Acquisto: Impossibile completare l'acquisto per insufficienza di fondi. \nControlla il budget e riprova.");
            }
            else {
                //FINALIZZO L'ACQUISTO
                //cambia il saldo del progetto corrispondente
                progetto.setSaldo(saldo - totale);
                //rimuovi la merce acquistata dal carrello
                Carrello cart = Carrello.getByID(dipendente.getID());
                for(Ordine ordine : listaOrdini){
                    cart.removeListaProdotti(ordine.getListaProdotti());
                    Magazzino mag = ordine.getMagazzino();
                    mag.removeFromInventario(ordine.getListaProdotti());
                }
                //Aggiorno il database inviando tutte le informazioni.
            }
        }
        catch(NoQueryMatchException | NoIDMatchException e){
            e.printStackTrace();
        }
        
    }
}