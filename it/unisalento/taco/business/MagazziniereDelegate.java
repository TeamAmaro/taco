package it.unisalento.taco.business;

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
}
