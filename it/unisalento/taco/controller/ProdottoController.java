package it.unisalento.taco.controller;

import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import it.unisalento.taco.view.ProdottoView;

public class ProdottoController {
	
	public Prodotto model;
	public ProdottoView view;
	
	public ProdottoController(Prodotto model, ProdottoView view) {
		this.model = model;
		this.view = view;
	}
	
	public String getNome() {
		return model.getNome();
	}
	
	public String getDescrizione() {
		return model.getDescrizione();
	}
	
	public Categoria getCategoria() {
		return model.getCategoria();
	}
	
	public double getPrezzo() {
		return model.getPrezzo();
	}
	
	public Produttore getProduttore() {
		return model.getProduttore();
	}
	
	public void setPrezzo(double prezzo) {
		model.setPrezzo(prezzo);
	}
	
	public void updateView() {
		view.stampaDettagliProdotto(model.getNome(), model.getDescrizione(), model.getCategoria(), model.getPrezzo(), model.getProduttore());
	}
	
}
