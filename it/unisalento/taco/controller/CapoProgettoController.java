package it.unisalento.taco.controller;

import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.view.CapoProgettoView;

public class CapoProgettoController {

  private CapoProgetto model;
  private CapoProgettoView view;

  public CapoProgettoController(CapoProgetto model, CapoProgettoView view) {
    this.model = model;
    this.view = view;
  }

  public void setProgettoCapoProgetto(Progetto progetto){
    model.setProgetto(progetto);
  }

  public Progetto getProgettoCapoProgetto() {
    return model.getProgetto();
  }

  public void updateView() {
	  view.stampaDettagliCapoProgetto(model.getNome(), model.getCognome(), model.getEmail(), model.getProgetto());
  }

}
