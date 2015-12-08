package it.unisalento.taco.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;

public class MagazziniereDelegate {
	
	private static MagazziniereDelegate instance;
	public static MagazziniereDelegate getInstance(){
		if(instance == null)
			instance = new MagazziniereDelegate();
		return instance;
	}
	private MagazziniereDelegate(){};
	
	public void chiediResocontoMagazzino(Magazzino magazzino){
		magazzino.resocontoInventario();
	}
	
	public void rifornisciProdotto(Magazzino magazzino, Prodotto prodotto, int quantita){
		magazzino.aggiungiProdotto(prodotto, quantita);
	};
	
	public void spedisciProdotto(Magazzino magazzino, Prodotto prodotto, int quantita){
		magazzino.rimuoviProdotto(prodotto, quantita);
	};
	
	//Cerca prodotti nei magazzini
	public List<Magazzino> cercaProdotto(Prodotto prodotto){
		List<Magazzino> listaRisultati = new ArrayList<>();
		/*
		 * algoritmo di ricerca da implementare
		 */
		
		return listaRisultati;
	}
	
	//Chiede prodotto al magazzino
	public int chiediProdotto(Magazzino magazzino, Prodotto prodotto, int quantita){
		return magazzino.cercaProdotto(prodotto, quantita);
	}
	
	
}
