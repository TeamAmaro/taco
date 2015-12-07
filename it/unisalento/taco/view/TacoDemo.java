package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dao.*;
import it.unisalento.taco.dbconnections.*;

public class TacoDemo {

	public static void main(String[] args) throws Exception{
		
		DipendenteDAO dipDAO = new DipendenteDAO();
		Dipendente[] dips = dipDAO.getAllDipendenti();
		
		for(int i = 0; i < dips.length; i++) {
			System.out.println("Dipendente");
			System.out.println("Nome: " + dips[i].getNome());
			System.out.println("Cognome: " + dips[i].getCognome());
			System.out.println("Email: " + dips[i].getEmail());
			
			System.out.println("");
		}
		
		ProgettoDAO progDAO = new ProgettoDAO();
		Progetto prog = progDAO.getProgetto(1);
		
		System.out.println("Progetto");
		System.out.println("Nome: " + prog.getNome());
		System.out.println("Budget: " + prog.getBudget());
		System.out.println("Saldo: " + prog.getSaldo());
		System.out.println("Capo progetto: " + prog.getCapoProgetto().getNome() + " " + prog.getCapoProgetto().getCognome());
		System.out.println("");
		
		Progetto[] progetti = progDAO.getAllProgetto();
		
		for(int i = 0; i < progetti.length; i++) {
			System.out.println("Nome Progetto: " + progetti[i].getNome());
			System.out.println("Nome capo progetto: " + progetti[i].getCapoProgetto().getNome() + " " + progetti[i].getCapoProgetto().getCognome());
			System.out.println("Saldo: " + progetti[i].getSaldo());
			System.out.println("Budget: " + progetti[i].getBudget());
			System.out.println("");
		}
		
	}
}
