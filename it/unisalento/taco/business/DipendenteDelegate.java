package it.unisalento.taco.business;

import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Prodotto;

public class DipendenteDelegate {
	private static DipendenteDelegate instance ;
	
	public static DipendenteDelegate getInstance(){
		if(instance == null)
			instance = new DipendenteDelegate();
		return instance;
	}
	private DipendenteDelegate(){};
	
	public void aggiungiProdotto(Prodotto prodotto, int quantita){
		System.out.println("Il delegato ha aggiunto " + quantita + " " + prodotto + "al carrello");
		Carrello.getInstance().aggiungiProdotto(prodotto, quantita);
	}
	
	public void rimuoviProdotto(Prodotto prodotto, int quantita){
		System.out.println("Il delegato ha rimosso " + quantita + " " + prodotto.getNome() + " dal carrello");
		Carrello.getInstance().rimuoviProdotto(prodotto, quantita);
	}
	
	public void acquista(){
		//Carrello.getInstance().acquista();
	}
	
}
