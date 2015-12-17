package it.unisalento.taco.model;
public class Magazziniere extends Utente  implements IdentificabileID {
	
    private final Magazzino magazzino;
	
    public Magazziniere(int id, String nome,String cognome,String email, Magazzino magazzino){
        super(id,nome,cognome,email);
        this.magazzino = magazzino;
    }

    public Magazzino getMagazzino(){
        return magazzino;
    }
}