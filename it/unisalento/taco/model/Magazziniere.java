package it.unisalento.taco.model;

import it.unisalento.taco.dao.MagazziniereDAO;

public class Magazziniere extends Utente  implements IdentificabileID {
	
    private final Magazzino magazzino;
	
    public Magazziniere(int id, String nome,String cognome,String email, Magazzino magazzino){
        super(id,nome,cognome,email);
        this.magazzino = magazzino;
    }
    
    public Magazziniere(String nome,String cognome,String email){
        super(nome,cognome,email);
        this.magazzino = null;
    }

    public Magazzino getMagazzino(){
        return magazzino;
    }
    
    public static void setMagazzino(Magazziniere magazziniere, Magazzino magazzino){
        MagazziniereDAO.getInstance().setMagazzino(magazziniere, magazzino);
    }
    
    public static void addMagazziniere(Magazziniere mag){
        MagazziniereDAO.getInstance().create(mag);
    }
    
    public static Magazziniere getMagazziniere(String email){
        return MagazziniereDAO.getInstance().getMagazziniere(email);
    }
}