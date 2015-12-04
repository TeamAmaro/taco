package it.unisalento.taco.business;

import it.unisalento.taco.controller.DipendenteController;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Prodotto;

public class DipendenteDelegate {
	private DipendenteDelegate instance ;
	
	public DipendenteDelegate getInstance(){
		if(instance == null)
			instance = new DipendenteDelegate();
		return instance;
	}
	private DipendenteDelegate(){};
	
	public void acquistaProdotto(Prodotto prodotto, int quantita, DipendenteController controller){
		Carrello carrello = controller.getCarrelloDipendente();
		carrello.aggiungiProdotto(prodotto, quantita);
	}
	
}
