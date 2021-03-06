package it.unisalento.taco.business;

import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoMagazzinoException;
import it.unisalento.taco.exception.NoQueryMatchException;
import it.unisalento.taco.model.Magazziniere;
import java.util.Map;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import java.util.LinkedHashSet;
import java.util.Set;

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
    
    public void spedisciOrdine(Ordine ordine){
        ordine.setSpedito(true);
    };
    //........ಠ_ಠ
    public Map<Prodotto,Integer> chiediInventario(Magazzino magazzino){
        return magazzino.getInventario();
    }
    //.......ಠ_ಠ
    public int chiediProdotto(Magazzino magazzino, Prodotto prodotto, int quantita){
        return magazzino.cercaProdotto(prodotto, quantita);
    }

    public Magazzino getMagazzino(Magazziniere magazziniere) throws NoMagazzinoException{
        return Magazzino.getMagazzino(magazziniere);
    }
    
    public Set<Ordine> chiediOrdini(Magazziniere magazziniere) throws NoIDMatchException, NoMagazzinoException{
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        Magazzino magazzino = Magazzino.getMagazzino(magazziniere);
        listaOrdini = Ordine.getListaOrdini(magazzino);
        return listaOrdini;
    }
}
