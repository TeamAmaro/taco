package it.unisalento.taco.business;

import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Prodotto;

public class DipendenteDelegate {
	private static DipendenteDelegate instance ;
	
	public static DipendenteDelegate getInstance(){
		if(instance == null)
			instance = new DipendenteDelegate();
		return instance;
	}
	private DipendenteDelegate(){};
	
	public void aggiungiProdotto(Dipendente dipendente, Prodotto prodotto, int quantita){
		System.out.println("Il delegato ha aggiunto " + quantita + " " + prodotto + "al carrello");
		dipendente.getCarrello().aggiungiProdotto(prodotto, quantita);
	}
	
	public void rimuoviProdotto(Dipendente dipendente, Prodotto prodotto, int quantita){
		System.out.println("Il delegato ha rimosso " + quantita + " " + prodotto.getNome() + " dal carrello");
		dipendente.getCarrello().rimuoviProdotto(prodotto, quantita);
	}
	
	public void acquista(Dipendente dipendente){
		//Dipendente.getCarrello().acquista();
	}
	
}
