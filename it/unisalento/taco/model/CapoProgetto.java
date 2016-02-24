package it.unisalento.taco.model;

import it.unisalento.taco.dao.CapoProgettoDAO;
import it.unisalento.taco.exception.NoQueryMatchException;

public class CapoProgetto extends Utente implements IdentificabileID {
    
    public CapoProgetto(int id, String nome, String cognome, String email){
        super(id,nome,cognome,email);
    }
    
    public static void addCapoProgetto(CapoProgetto capo){
        CapoProgettoDAO.getInstance().create(capo);
    }
    
    public static CapoProgetto getCapoProgetto(String email) throws NoQueryMatchException{
        return CapoProgettoDAO.getInstance().getCapoProgetto(email);
    }
    
    public static CapoProgetto getCapoProgetto(Progetto progetto) throws NoQueryMatchException{
        return CapoProgettoDAO.getInstance().getCapoProgetto(progetto);
    }
}
