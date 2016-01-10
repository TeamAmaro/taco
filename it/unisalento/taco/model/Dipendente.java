package it.unisalento.taco.model;

import it.unisalento.taco.dao.DipendenteDAO;

public class Dipendente extends Utente  implements IdentificabileID {

    private Sede sede;

    public Dipendente(int id, String nome, String cognome, String email, Sede sede){
        super(id,nome,cognome,email);
        this.sede = sede;
    }
    
    public Dipendente(String nome, String cognome, String email, Sede sede){
        super(nome,cognome,email);
        this.sede = sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Sede getSede() {
        return sede;
    }
    
    public static void addDipendente(Dipendente dipendente){
        DipendenteDAO.getInstance().create(dipendente);
    }
    
    public static Dipendente getDipendente(String email){
        return DipendenteDAO.getInstance().getDipendente(email);
    }
    
    public static void setProgetto(Dipendente dipendente, Progetto progetto){
        DipendenteDAO.getInstance().setProgetto(dipendente, progetto);
    }
    
}
