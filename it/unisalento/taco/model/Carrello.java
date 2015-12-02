package it.unisalento.taco.model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {

	private List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
	private double prezzoTotale;
	
	private Carrello instance;
	
	public Carrello getInstance(){
		if(instance == null)
			instance = new Carrello();
		return instance;
	}
	
	//private double calcolaPrezzoTotale(){return new Double()};
	//public Ordine generaOrdine() return new Ordine();};
	
	private Carrello(){};
	
	public void aggiungiProdotto(Prodotto prodotto, int quantita){
		for (int i = quantita; i > 0; i--){
			listaProdotti.add(prodotto);
		}
	}
	
	
}
