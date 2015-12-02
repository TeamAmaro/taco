package it.unisalento.taco.model;
public class Dipendente extends Utente {

	private Progetto progetto;
	private Sede sede;
	private Carrello carrello;

	public Dipendente(String nome, String cognome, String email){
		super(nome,cognome,email);
	}

	public Dipendente(String nome, String cognome, String email, Progetto progetto, Sede sede, Carrello carrello){
		super(nome,cognome,email);
		this.progetto = progetto;
		this.sede = sede;
		this.carrello = carrello.getInstance();
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
	
	public Carrello getCarrello(){
		return carrello;
	}
}
