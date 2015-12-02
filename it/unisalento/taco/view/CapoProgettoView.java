package it.unisalento.taco.view;

import it.unisalento.taco.model.Progetto;

public class CapoProgettoView {

  public void stampaDettagliCapoProgetto(String nome, String cognome, String email, Progetto progetto) {

    System.out.println("CapoProgetto");
    System.out.println("Nome: " + nome);
    System.out.println("Cognome: " + cognome);
    System.out.println("Email: " + email);
    if(progetto != null)
    	System.out.println("Progetto: " + progetto.getNome());
    System.out.println("");
  }
}
