package it.unisalento.taco.model;

public class CapoProgetto extends Utente {

	private Progetto progetto;

	public CapoProgetto(int id, String nome, String cognome, String email){
		super(id,nome,cognome,email);
	}

	public CapoProgetto(int id, String nome, String cognome, String email, Progetto progetto) {
		super(id,nome,cognome,email);
		this.progetto = progetto;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

}
