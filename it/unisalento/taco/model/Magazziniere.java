package it.unisalento.taco.model;

import it.unisalento.taco.dao.MagazziniereDAO;

public class Magazziniere extends Utente implements IdentificabileID {
		
    public Magazziniere(int id, String nome,String cognome,String email){
        super(id,nome,cognome,email);
    }
    
    public Magazziniere(String nome,String cognome,String email){
        super(nome,cognome,email);
    }
    
    public static void addMagazziniere(Magazziniere mag){
        MagazziniereDAO.getInstance().create(mag);
    }
    
    public static Magazziniere getMagazziniere(String email){
        return MagazziniereDAO.getInstance().getMagazziniere(email);
    }
}