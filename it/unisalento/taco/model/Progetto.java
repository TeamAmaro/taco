package it.unisalento.taco.model;

public class Progetto {
	
	private final String nome;
	
	private double saldo;
	private final double budget;
	
	public Progetto(String nome, double budget){
		this.nome = nome;
		this.budget = budget;
		saldo = budget;
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
	
	public boolean aggiornaSaldo(double spesa){
		if(saldo < spesa) 
			return false;
		else {
			saldo -= spesa;
			return true;
		}
	}
	
	
}
