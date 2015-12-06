package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dao.*;
import it.unisalento.taco.dbconnections.*;

public class TacoDemo {

	public static void main(String[] args) throws Exception{
		
		DipendenteDAO dipDAO = new DipendenteDAOImpl();
		Dipendente dip = dipDAO.getDipendente(1);
		
		System.out.println("Dipendente");
		System.out.println("Nome: " + dip.getNome());
		System.out.println("Cognome: " + dip.getCognome());
		System.out.println("Email: " + dip.getEmail());
		System.out.println("Progetto: " + dip.getProgetto().getNome());
		
		System.out.println("");
		
		ProgettoDAO progDAO = new ProgettoDAOImpl();
		Progetto prog = progDAO.getProgetto(1);
		
		System.out.println("Progetto");
		System.out.println("Nome: " + prog.getNome());
		System.out.println("Budget: " + prog.getBudget());
		System.out.println("Saldo: " + prog.getSaldo());
		System.out.println("Capo progetto: " + prog.getCapoProgetto().getNome() + " " + prog.getCapoProgetto().getCognome());
		
	}
}
