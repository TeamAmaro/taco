package it.unisalento.taco.model;

public abstract class Utente {
	
	private final String nome;
	private final String cognome;
	private String email;
	
	public Utente(String nome, String cognome, String email){
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getCognome(){
		return cognome;
	}
	
	public String getEmail(){
		return email;
	}
}