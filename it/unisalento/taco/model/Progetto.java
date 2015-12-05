package it.unisalento.taco.model;

public class Progetto {
	
	private final int id;
	private final String nome;
	private double saldo;
	private final double budget;
	
	public Progetto(int id, String nome, double budget){
		this.id = id;
		this.nome = nome;
		this.budget = budget;
		saldo = budget;
	}
	
	public Progetto(int id, String nome, double saldo, double budget) {
		this.id = id;
		this.nome = nome;
		this.saldo = saldo;
		this.budget = budget;
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
	
	public boolean aggiornaSaldo(double spesa){
		if(saldo < spesa) 
			return false;
		else {
			saldo -= spesa;
			return true;
		}
	}
	
	
}
