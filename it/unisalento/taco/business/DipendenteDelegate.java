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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
    
    public int chiediDisponibilita(Dipendente dipendente, Prodotto prodotto) throws NoQueryMatchException, NoIDMatchException{
        Sede sede = dipendente.getSede();
        try{
            Magazzino magVicino = Magazzino.getMagazzino(sede);
            return Magazzino.getQuantita(magVicino, prodotto);
        }catch(NoQueryMatchException | NoIDMatchException e){
            throw e;
        }
    }
    
    public int chiediDisponibilitaAll(Prodotto prodotto) throws NoQueryMatchException, NoIDMatchException{
        return Magazzino.getQuantitaAll(prodotto);
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

    public void modificaQuantita(Carrello carrello, Prodotto prodotto, int q) {
        carrello.updateQuantita(prodotto, q);
    }
    
}
