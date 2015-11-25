package it.unisalento.pps.model;

public class CapoProgetto extends Utente {
	
	private Progetto progetto;
	
	public CapoProgetto(String nome, String cognome, String email){
		super(nome,cognome,email);
	}
	
	//void creaProgetto(){};
	//void resocontoProgetto(){};
	//void aggiungiDipendente(int idProgetto, int idDipendente){};
	
	public void setProgetto(String nome, double budget){
		progetto = new Progetto(nome, budget);
	}
	
	public Progetto getProgetto(){
		return progetto;
	}
	
}