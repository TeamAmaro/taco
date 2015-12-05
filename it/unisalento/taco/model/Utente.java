package it.unisalento.taco.model;

public abstract class Utente {
	
	private final int id;
	private final String nome;
	private final String cognome;
	private String email;
	
	public Utente(int id, String nome, String cognome, String email){
		this.id = id;
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
	
	public int getID(){
		return id;
	}
}