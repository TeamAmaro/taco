package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.InsufficientFundException;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;
import it.unisalento.taco.view.Main;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DipendenteDelegate {
	
    private static DipendenteDelegate instance ;

    public static DipendenteDelegate getInstance(){
        if(instance == null)
            instance = new DipendenteDelegate();
        return instance;
    }
    private DipendenteDelegate(){};

    public Progetto getProgetto(Dipendente dipendente) throws NoQueryMatchException, NoIDMatchException{
        return Progetto.getProgetto(dipendente);
    }
    
    public Carrello getCarrello(Dipendente dipendente) throws NoIDMatchException{
        return Carrello.getCarrello(dipendente);
    }
    
    public Set<Prodotto> cercaProdotti(String ricerca) throws NoIDMatchException{
        return Prodotto.cerca(ricerca);
    }
    
    public Set<Prodotto> cercaProdotti(Categoria categoria) throws NoIDMatchException{
        return Prodotto.cerca(categoria);
    }
    
    public void addProdotto(Carrello carrello, Prodotto prodotto, int quantita){
        carrello.addProdotto(prodotto, quantita);
    }
    
    //Questo metodo riduce la quantità di un prodotto nel carrello del valore passato,
    //se il valore è maggiore o uguale a quello presente, il prodotto verrà completamente eliminato
    public void removeProdotto(Carrello carrello, Prodotto prodotto, int quantita){
        carrello.removeProdotto(prodotto, quantita);
    }
    
    public void removeProdotto(Carrello carrello, Prodotto prodotto){
        carrello.removeProdotto(prodotto);
    }
    
    public int chiediDisponibilità(Dipendente dipendente, Prodotto prodotto) throws NoQueryMatchException, NoIDMatchException{
        Sede sede = dipendente.getSede();
        try{
            Magazzino magVicino = Magazzino.getMagazzino(sede);
            return Magazzino.getQuantita(magVicino, prodotto);
        }catch(NoQueryMatchException | NoIDMatchException e){
            throw e;
        }
    }
    
    public Set<Ordine> generaOrdini(Dipendente dipendente) throws NoIDMatchException, NoQueryMatchException{
        
        Map<Magazzino,Map<Prodotto,Integer>> magPerProd = new LinkedHashMap<>();
        Map<Prodotto,Integer> prodPerQuantMag1 = new LinkedHashMap<>();
        Map<Prodotto,Integer> prodPerQuantMag2 = new LinkedHashMap<>();
        Map<Prodotto,Integer> prodPerQuantMag3 = new LinkedHashMap<>();
        Map<Prodotto,Integer> prodPerQuantMag4 = new LinkedHashMap<>();
        
        int quantMag1 = 0, quantMag2, quantMag3, quantMag4, quantMagTot = 0, i = 2;
        
        try {
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
                quantMag1 = Magazzino.getQuantita(magVicino, prod); //Quantita nel magazzino
                quantMagTot += quantMag1;
                prodPerQuantMag1.put(prod, quantCar); //Aggiungo la quantita
                magPerProd.put(magVicino, prodPerQuantMag1); //Aggiungo nel magazzino vicino
                //Se la quantita di prodotti nel magazzino più vicino non soddisfa la richiesta
                if (quantMag1 < quantCar){
                    //Chiedo i magazzini esterni che hanno il prodotto
                    Set<Magazzino> magConProd = Magazzino.cercaProdotto(prod);
                    //Rimuovo il magazzino vicino dalla lista dei risultati
                    magConProd.remove(magVicino);
                    int differenza = quantCar - quantMag1;
                    int remainingQuant = quantCar;
                    
                    //Prendi la quantità totale ordinabile da tutti i magazzini
                    for(Magazzino magExt : magConProd) {
                        quantMagTot += Magazzino.getQuantita(magExt, prod);
                    }
                    
                    //Chiedo la quantita di prodotto nei magazzini esterni
                    //Assumendo che la lista dei magazzini è già presentata in ordine decrescente
                    for(Magazzino magExt : magConProd){
                        //Il contatore 'i' tiene traccia del numero di magazzini che si sta passando in rassegna
                        if(i == 2 && i <= magConProd.size()) {
                            quantMag2 = Magazzino.getQuantita(magExt, prod);
                            //Se la quantità nel secondo magazzino è minore di quella richiesta
                            if(quantMag2 < differenza){
                                //Sottrai alla quantià richiesta quella presente nel secondo magazzino
                                remainingQuant = quantCar - quantMag2;
                                //Metti questa quantità rimanente nel primo magazzino
                                prodPerQuantMag1.put(prod, remainingQuant);
                                //Metti tutta la quantità del secondo magazzino nel secondo magazzino
                                prodPerQuantMag2.put(prod, quantMag2);
                                //Aggiorna la lista dell'ordine per il secondo magazzino
                                magPerProd.put(magExt, prodPerQuantMag2);
                                //Aggiorna la quantità restante da cercare negli altri magazzini
                                differenza -= quantMag2;
                                //Aggiorna il contatore perché è necessario passare al terzo magazzino
                                i++;
                                continue;
                            }
                            //Se nel secondo magazzino la quantità soddisfa la richiesta
                            else {
                                remainingQuant -= differenza;
                                prodPerQuantMag1.put(prod, remainingQuant);
                                prodPerQuantMag2.put(prod, differenza);
                                magPerProd.put(magExt, prodPerQuantMag2);
                                break;
                            }
                        }
                        if(i == 3 && i <= magConProd.size()){
                            quantMag3 = Magazzino.getQuantita(magExt, prod);
                            if(quantMag3 < differenza){
                                remainingQuant = quantCar - quantMag3;
                                prodPerQuantMag1.put(prod, remainingQuant);
                                prodPerQuantMag3.put(prod, quantMag3);
                                magPerProd.put(magExt, prodPerQuantMag3);
                                differenza -= quantMag3;
                                i++;
                                continue;
                            }
                            else{
                                remainingQuant -= differenza;
                                prodPerQuantMag1.put(prod, remainingQuant);
                                prodPerQuantMag3.put(prod, differenza);
                                magPerProd.put(magExt, prodPerQuantMag3);
                                break;
                            }
                        }
                        if(i == 4 && i <= magConProd.size()){
                            quantMag4 = Magazzino.getQuantita(magExt, prod);
                            if(quantMag4 < differenza){
                                //Avvisa che è necessario selezionare una quantità minore
                                //Mostra la quantità massima acquistabile (quantMagTot)
                            }
                            else {
                                remainingQuant -= differenza;
                                prodPerQuantMag1.put(prod, remainingQuant);
                                prodPerQuantMag4.put(prod, differenza);
                                magPerProd.put(magExt, prodPerQuantMag4);
                                break;
                            }
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
            int j = 0;

            for(Map.Entry<Magazzino,Map<Prodotto,Integer>> mag : magPerProd.entrySet()){
                if(quantMag1 == 0 && j == 0) {
                    j++;
                    continue;
                }
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
                Progetto.updateSaldo(progetto, saldo - totale);
                //rimuovi la merce acquistata dal carrello
                Carrello cart = Carrello.getByID(dipendente.getID());
                for(Ordine ordine : listaOrdini){
                    cart.removeListaProdotti(ordine.getListaProdotti());
                    Magazzino mag = ordine.getMagazzino();
                    mag.removeFromInventario(ordine.getListaProdotti());
                    Ordine.addOrdine(ordine);
                }
            }
        }
        catch(NoQueryMatchException | NoIDMatchException e){
            System.err.println(e.getMessage());
        }
        
    }
}
