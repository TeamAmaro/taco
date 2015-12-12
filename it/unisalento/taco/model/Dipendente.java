package it.unisalento.taco.model;

public class Dipendente extends Utente {

	private Progetto progetto; 
	private Sede sede;  
	private Carrello carrello;

	//Costruttore di base per il dipendente
	public Dipendente(int id, String nome, String cognome, String email, Sede sede){
		super(id,nome,cognome,email);
                this.sede = sede;
	}

	public Dipendente(int id, String nome, String cognome, String email, Sede sede, Progetto progetto, Carrello carrello){
		super(id,nome,cognome,email);
		this.progetto = progetto;
		this.sede = sede;
		this.carrello = carrello;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}
        
	public Progetto getProgetto() {
		return progetto;
	}

	public Sede getSede() {
		return sede;
	}
	
	public Carrello getCarrello(){
		return carrello;
	}
}
