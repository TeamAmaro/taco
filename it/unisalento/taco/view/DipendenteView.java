package it.unisalento.taco.view;

import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;

public class DipendenteView {

	public void stampaDettagliDipendente(String nome, String cognome, String email, Progetto progetto, Sede sede) {

		System.out.println("Dipendente");
		System.out.println("Nome: " + nome);
		System.out.println("Cognome: " + cognome);
		System.out.println("Email: " + email);
		if(progetto != null)
			System.out.println("Progetto: " + progetto.getNome());
		if(sede != null)
			System.out.println("Sede: " + sede);
		System.out.println("");
	}
}
