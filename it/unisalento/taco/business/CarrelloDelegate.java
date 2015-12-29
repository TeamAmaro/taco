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
        Carrello.addProdotto(carrello, prodotto, quantita);
        carrello.addProdotto(prodotto, quantita);
    }
    
    public void deleteProdotto(Carrello carrello, Prodotto prodotto){
        Carrello.deleteProdotto(carrello, prodotto);
        carrello.removeProdotto(prodotto, 1);
    }
    
}
