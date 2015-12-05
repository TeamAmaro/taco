package it.unisalento.taco.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

public class Progetto {
	
	private final int id;
	private final String nome;
	private final double budget;
	private CapoProgetto capoProgetto;
	
	private double saldo;
	private Set<Dipendente> listaDipendenti = new LinkedHashSet<Dipendente>();
	
	public Progetto(int id, String nome, CapoProgetto capoProgetto, double budget) {
		this.id = id;
		this.nome = nome;
		this.capoProgetto = capoProgetto;
		this.budget = budget;
		saldo = budget;
	}
	
	public void aggiungiDipendente(Dipendente... args){
		for(Dipendente val : args)
			listaDipendenti.add(val);
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
			stringDipendenti.append(val.toString() + " ");
		return stringDipendenti.toString();
	}
	
	@Override public String toString(){
		StringBuilder stringProgetto = new StringBuilder();
		stringProgetto.append(id + " " + nome + " " + budget + " " + capoProgetto + " " + saldo + " " + getListaDipendentiAsString());
		return stringProgetto.toString();
	}
	
}
