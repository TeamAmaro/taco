package it.unisalento.taco.model;

import java.util.Arrays;
import java.util.Set;
import java.util.LinkedHashSet;

public class Progetto {
	
    private final int id;
    private final String nome;
    private final double budget;
    private CapoProgetto capoProgetto;

    private double saldo;
    private Set<Dipendente> listaDipendenti = new LinkedHashSet<Dipendente>();

    //PER LA CREAZIONE DI UN NUOVO PROGETTO
    public Progetto(int id, String nome, CapoProgetto capoProgetto, double budget) {
        this.id = id;
        this.nome = nome;
        this.capoProgetto = capoProgetto;
        this.budget = budget;
        saldo = budget;
    }

    //PER PROGETTI GIA' ESISTENTI
    public Progetto(int id, String nome, CapoProgetto capoProgetto, double saldo, double budget) {
        this.id = id;
        this.nome = nome;
        this.capoProgetto = capoProgetto;
        this.saldo = saldo;
        this.budget = budget;
    }
    
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }
    
    public void setCapoProgetto(CapoProgetto capProg) {
        this.capoProgetto = capProg;
    }

    public void aggiungiDipendente(Set<Dipendente> listaAggiuntiva){
        listaDipendenti.addAll(listaAggiuntiva);
    }
    
    public void aggiungiDipendente(Dipendente... args){
        listaDipendenti.addAll(Arrays.asList(args));
    }

    public int getID() {
        return id;
    }

    public String getNome(){
        return nome;
    }

    public double getSaldo(){
        return saldo;
    }

    public double getBudget(){
        return budget;
    }

    public CapoProgetto getCapoProgetto(){
        return capoProgetto;
    }

    public Set<Dipendente> getListaDipendenti(){
        return listaDipendenti;
    }

    public String getListaDipendentiAsString(){
        StringBuilder stringDipendenti = new StringBuilder();
        for(Dipendente val : listaDipendenti)
            stringDipendenti.append(val.toString()).append(", ");
        int last = stringDipendenti.lastIndexOf(",");
        stringDipendenti.delete(last, last + 2);
        return stringDipendenti.toString();
    }

    @Override public String toString(){
        StringBuilder stringProgetto = new StringBuilder();
        stringProgetto.append("ID: ").append(id).append(", Nome: ").append(nome).append(", Capoprogetto: ").
                       append(capoProgetto).append(", Budget: ").append(budget).append(", Saldo: ").append(saldo).
                       append(", Lista Dipendenti: ").append(getListaDipendentiAsString()).append(".");
        return stringProgetto.toString();
    }
}
