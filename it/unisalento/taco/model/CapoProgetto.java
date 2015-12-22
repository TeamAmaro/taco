package it.unisalento.taco.model;

import it.unisalento.taco.dao.CapoProgettoDAO;

public class CapoProgetto extends Utente implements IdentificabileID {
    
    public CapoProgetto(int id, String nome, String cognome, String email){
        super(id,nome,cognome,email);
    }
    
    public CapoProgetto(String nome, String cognome, String email){
        super(nome,cognome,email);
    }
    
    public void addToDB(CapoProgetto capo){
        CapoProgettoDAO.getInstance().create(capo);
    }
}
