package it.unisalento.taco.model;

import it.unisalento.taco.dao.ProgettoDAO;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoProgettoException;
import it.unisalento.taco.exception.NoQueryMatchException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
        this.saldo = budget;
        this.listaDipendenti = listaDipendenti;
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
    
    /*
    NOTA BENE
    L'OGGETTO PROGETTO HA LISTADIPENDENTI MA NEL DB E' DIPENDENTE AD AVERE L'ID DEL PROGETTO
    LA FUNZIONE setProgetto() PER IL DB E' IN DIPENDENTEDAO
    */

    @Override public int getId() {
        return id;
    }

    public String getNome(){
        return nome;
    }

    public double getSaldo(){
        return saldo;
    }
    
    public String getFormatSaldo(){
        NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
        formatoEuro.setMinimumFractionDigits( 2 );
        formatoEuro.setMaximumFractionDigits( 2 );
        formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
        return formatoEuro.format(saldo);
    }
    
    public String getFormatBudget(){
        NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
        formatoEuro.setMinimumFractionDigits( 2 );
        formatoEuro.setMaximumFractionDigits( 2 );
        formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
        return formatoEuro.format(budget);
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

    public static Progetto getProgetto(Dipendente dipendente) throws NoIDMatchException, NoProgettoException{
        return ProgettoDAO.getInstance().getProgetto(dipendente);
    }
    
    public static Progetto getProgetto(String nome){
        return ProgettoDAO.getInstance().getProgetto(nome);
    }
    
    public static List<Progetto> getProgetto(CapoProgetto capoProgetto){
        return ProgettoDAO.getInstance().getProgetto(capoProgetto);
    }
    
    public static void addProgetto(Progetto progetto){
        ProgettoDAO.getInstance().create(progetto);
    }
    
    public static void setCapoProgetto(Progetto prog, CapoProgetto capo){
        ProgettoDAO.getInstance().setCapoProgetto(prog, capo);
    }
    
    public static void updateSaldo(Progetto prog, double saldo){
        ProgettoDAO.getInstance().updateSaldo(prog, saldo);
    }
    
    @Override public String toString(){
        StringBuilder stringProgetto = new StringBuilder();
        stringProgetto.append("ID: ").append(id).append(", Nome: ").append(nome).append(", Capoprogetto: ").
                       append(capoProgetto).append(", Budget: ").append(budget).append(", Saldo: ").append(saldo).
                       append(", Lista Dipendenti: ").append(getListaDipendentiAsString()).append(".");
        return stringProgetto.toString();
    }
}
