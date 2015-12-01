package it.unisalento.taco.view;

import it.unisalento.taco.model.Dipendente;

public class DipendenteView {
	
	public void stampaDettagliDipendente(String nome, String cognome, String email, String progetto, String sede) {
		
		System.out.println("Dipendente");
		System.out.println("Nome: " + nome);
		System.out.println("Cognome: " + cognome);
		System.out.println("Email: " + email);
		System.out.println("Progetto: " + progetto);
		System.out.println("Sede: " + sede);
	}
	
	public void stampaDettagliDipendente(String nome, String cognome, String email) {

		System.out.println("Dipendente");
		System.out.println("Nome: " + nome);
		System.out.println("Cognome: " + cognome);
		System.out.println("Email: " + email);
	}
	
}
