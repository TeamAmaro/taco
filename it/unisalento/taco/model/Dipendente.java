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
	
	public Dipendente(String nome, String cognome, String email, Progetto progetto, Sede sede){
		super(nome,cognome,email);
		this.progetto = progetto;
		this.sede = sede;
	}
	
	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}
	
	public Progetto getProgetto() {
		return progetto;
	}
	
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	
	public Sede getSede() {
		return sede;
	}
}