package it.unisalento.taco.model;

public class CapoProgetto extends Utente {

	private Progetto progetto;

	public CapoProgetto(String nome, String cognome, String email){
		super(nome,cognome,email);
	}

	public CapoProgetto(String nome, String cognome, String email, Progetto progetto) {
		super(nome,cognome,email);
		this.progetto = progetto;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

}
