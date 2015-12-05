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
		
	}
}
