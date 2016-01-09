package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import java.util.List;
import java.util.Set;

public class CapoProgettoDelegate {
    private static CapoProgettoDelegate instance;
    public static CapoProgettoDelegate getInstance(){
        if(instance == null)
            instance = new CapoProgettoDelegate();
        return instance;
    }
    
    private CapoProgettoDelegate(){}

    public List<Progetto> getProgetto(CapoProgetto capoProgetto) {
        return Progetto.getProgetto(capoProgetto);
    }
    
     public Set<Ordine> getListaOrdini(Progetto progetto) throws NoIDMatchException {
        return Ordine.getListaOrdini(progetto);
    }
}
