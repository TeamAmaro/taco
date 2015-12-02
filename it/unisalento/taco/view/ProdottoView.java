package it.unisalento.taco.view;

import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Produttore;

public class ProdottoView {
	
	public void stampaDettagliProdotto(String nome, String descrizione, Categoria categoria, double prezzo, Produttore produttore) {
		System.out.println("Prodotto");
		System.out.println("Nome: "+ nome);
		if(descrizione != null)
			System.out.println("Descrizione: " + descrizione);
		System.out.println("Categoria: " + categoria);
		System.out.println("Prezzo: " + prezzo);
		System.out.println("Produttore: " + produttore);
	}
	
}
