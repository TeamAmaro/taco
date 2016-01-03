package it.unisalento.taco.business;

import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Prodotto;

public class DipendenteDelegate {
	
    private static DipendenteDelegate instance ;

    public static DipendenteDelegate getInstance(){
        if(instance == null)
            instance = new DipendenteDelegate();
        return instance;
    }
    private DipendenteDelegate(){};

    
    
}
