package it.unisalento.taco.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class CapoProgetto extends Utente {
    private Sede sede;
    private Set<Progetto> listaProgetti = new LinkedHashSet<>();

    public CapoProgetto(int id, String nome, String cognome, String email, Sede sede){
        super(id,nome,cognome,email);
        this.sede = sede;
    }

    public CapoProgetto(int id, String nome, String cognome, String email, Sede sede, Progetto... args) {
        super(id,nome,cognome,email);
        this.sede = sede;
        listaProgetti.addAll(Arrays.asList(args));
    }

    public void setSede(Sede sede){
        this.sede = sede;
    }

    public Sede getSede(){
        return sede;
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
