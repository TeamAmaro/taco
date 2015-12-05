package it.unisalento.taco.model;
public class Magazziniere extends Utente {
	
	//int idMagazzino;
	/*
	void spedisciOrdine(int idOrdine){};
	void rifornisciMagazzino(){};
	*/
	
	private Magazzino magazzino;
	
	public Magazziniere(int id, String nome,String cognome,String email){
		super(id,nome,cognome,email);
	}
}