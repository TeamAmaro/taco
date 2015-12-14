package it.unisalento.taco.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class CapoProgetto extends Utente {
    private Set<Progetto> listaProgetti = new LinkedHashSet<>();

    public CapoProgetto(int id, String nome, String cognome, String email){
        super(id,nome,cognome,email);
    }

    public CapoProgetto(int id, String nome, String cognome, String email, Progetto... args) {
        super(id,nome,cognome,email);
        listaProgetti.addAll(Arrays.asList(args));
    }
    
    public CapoProgetto(int id, String nome, String cognome, String email, Set<Progetto> listaProgetti){
        super(id, nome, cognome, email);
        this.listaProgetti = listaProgetti;
    }
    
    public void setListaProgetti(Set<Progetto> listaProgetti){
        this.listaProgetti = listaProgetti;
    }

    public Set<Progetto> getListaProgetti() {
        return listaProgetti;
    }

    public void addProgetto(Progetto progetto) {
        listaProgetti.add(progetto);
    }

    public void removeProgetto(Progetto progetto){
        listaProgetti.remove(progetto);
    }
}
