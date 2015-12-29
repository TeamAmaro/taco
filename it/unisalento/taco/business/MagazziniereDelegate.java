package it.unisalento.taco.business;

import java.util.Map;

import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;

public class MagazziniereDelegate {
	
    private static MagazziniereDelegate instance;
    public static MagazziniereDelegate getInstance(){
            if(instance == null)
                    instance = new MagazziniereDelegate();
            return instance;
    }
    private MagazziniereDelegate(){};

    public void rifornisciProdotto(Magazzino magazzino, Prodotto prodotto, int quantita){
            magazzino.addProdotto(prodotto, quantita);
    };

    public void spedisciProdotto(Magazzino magazzino, Prodotto prodotto, int quantita){
            magazzino.removeProdotto(prodotto, quantita);
    };

    
    public Map<Prodotto,Integer> chiediInventario(Magazzino magazzino){
            return magazzino.getInventario();
    }

    //Chiede prodotto al magazzino
    public int chiediProdotto(Magazzino magazzino, Prodotto prodotto, int quantita){
            return magazzino.cercaProdotto(prodotto, quantita);
    }
        
	
}
