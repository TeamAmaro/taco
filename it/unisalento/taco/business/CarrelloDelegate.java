/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.business;

import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Prodotto;

public class CarrelloDelegate {
    
    private static CarrelloDelegate instance ;

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
    
}
