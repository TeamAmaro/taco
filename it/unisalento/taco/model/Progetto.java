package it.unisalento.taco.model;

import it.unisalento.taco.dao.ProgettoDAO;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import java.util.Arrays;
import java.util.Set;

public class Progetto implements IdentificabileID {
	
    private final int id;
    private final String nome;
    private final double budget;
    private double saldo;
    
    private final CapoProgetto capoProgetto;
    private Set<Dipendente> listaDipendenti;
    
    public Progetto(int id, String nome, CapoProgetto capoProgetto, double budget, Set<Dipendente> listaDipendenti){
        this.id = id;
        this.nome = nome;
        this.capoProgetto = capoProgetto;
        this.budget = budget;
        this.listaDipendenti = listaDipendenti;
        budget = saldo;
    }
    
    public Progetto(int id, String nome, CapoProgetto capoProgetto, double saldo, double budget, Set<Dipendente> listaDipendenti){
        this.id = id;
        this.nome = nome;
        this.capoProgetto = capoProgetto;
        this.budget = budget;
        this.saldo = saldo;
        this.listaDipendenti = listaDipendenti;
    }
        
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    public void setListaDipendenti(Set<Dipendente> listaDipendenti){
        this.listaDipendenti = listaDipendenti;
    }

    public void addListaDipendenti(Set<Dipendente> listaAggiuntiva){
        listaDipendenti.addAll(listaAggiuntiva);
    }
    
    public void addDipendente(Dipendente... args){
        listaDipendenti.addAll(Arrays.asList(args));
    }

    @Override public int getID() {
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
        if(listaDipendenti.isEmpty())
            return "Nessun dipendente";
        StringBuilder stringDipendenti = new StringBuilder();
        for(Dipendente val : listaDipendenti)
            stringDipendenti.append(val.toString()).append(", ");
        int last = stringDipendenti.lastIndexOf(",");
        if(last != -1){
            stringDipendenti.delete(last, last + 2);
        }
        return stringDipendenti.toString();
    }

    public static Progetto getProgetto(Dipendente dipendente) throws NoQueryMatchException, NoIDMatchException{
        return ProgettoDAO.getInstance().getProgetto(dipendente);
    }
    
    @Override public String toString(){
        StringBuilder stringProgetto = new StringBuilder();
        stringProgetto.append("ID: ").append(id).append(", Nome: ").append(nome).append(", Capoprogetto: ").
                       append(capoProgetto).append(", Budget: ").append(budget).append(", Saldo: ").append(saldo).
                       append(", Lista Dipendenti: ").append(getListaDipendentiAsString()).append(".");
        return stringProgetto.toString();
    }
}
