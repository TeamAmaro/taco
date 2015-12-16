package it.unisalento.taco.model;

public class Dipendente extends Utente {

    private Sede sede;  
    private Carrello carrello;

    public Dipendente(int id, String nome, String cognome, String email, Sede sede, Carrello carrello){
        super(id,nome,cognome,email);
        this.sede = sede;
        this.carrello = carrello;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
    
    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public Sede getSede() {
        return sede;
    }

    public Carrello getCarrello(){
        return carrello;
    }
}
