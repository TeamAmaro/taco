package it.unisalento.taco.controller;

import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;
import it.unisalento.taco.view.DipendenteView;

public class DipendenteController {
	
	private Dipendente model;
	private DipendenteView view;
	
	public DipendenteController(Dipendente model, DipendenteView view) {
		this.model = model;
		this.view = view;
	}
	
	public void setProgettoDipendente(Progetto progetto) {
		model.setProgetto(progetto);
	}
	
	public Progetto getProgettoDipendente() {
		return model.getProgetto();
	}
	
	public void setSedeDipendente(Sede sede) {
		model.setSede(sede);
	}
	
	public Sede getSedeDipendente() {
		return model.getSede();
	}
	
	public void updateView(){
		view.stampaDettagliDipendente(model.getNome(), model.getCognome(), model.getEmail(), model.getProgetto(), model.getSede());
	}
	
}
