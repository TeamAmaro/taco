package it.unisalento.taco.model;
public class Magazziniere extends Utente {
	
    private Magazzino magazzino;
	
    public Magazziniere(int id, String nome,String cognome,String email){
            super(id,nome,cognome,email);
    }
	
    public void setMagazzino(Magazzino magazzino){
            this.magazzino = magazzino;
    }
	
    public Magazzino getMagazzino(){
            return magazzino;
    }
}