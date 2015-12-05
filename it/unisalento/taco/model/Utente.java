package it.unisalento.taco.model;

public abstract class Utente {
	
	protected final int id;
	protected final String nome;
	protected final String cognome;
	protected String email;
	
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
	
	@Override public String toString(){
		StringBuilder stringUtente = new StringBuilder();
		stringUtente.append(id + " " + nome + " " + cognome);
		return stringUtente.toString();
	}
}