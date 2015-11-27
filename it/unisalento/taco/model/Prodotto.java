package it.unisalento.taco.model;

public class Prodotto {
	
	int id;
	private String nome;
	private String descrizione;
	private double prezzo;
	private Produttore produttore;

	String produttore(int id){
		
		String nomeProduttore = "Sconosciuto"; //default
		
		if(id == 1)
			nomeProduttore = "SaporiPreziosi";
		
		return nomeProduttore;
	}
}
