package it.unisalento.taco.model;
public class Dipendente extends Utente {
	/*
	private int idProgetto;
	private int idSede;
	
	void aggiungiAlCarrello(){};
	*/
	
	private Progetto progetto;
	private Sede sede;
	
	public Dipendente(String nome, String cognome, String email){
		super(nome,cognome,email);
	}
}