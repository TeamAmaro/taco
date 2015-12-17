package it.unisalento.taco.model;

public class CapoProgetto extends Utente implements IdentificabileID {
    
    public CapoProgetto(int id, String nome, String cognome, String email){
        super(id,nome,cognome,email);
    }
}
