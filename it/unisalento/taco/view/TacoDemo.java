package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.*;

public class TacoDemo {

	public static void main(String[] args) throws Exception{
		
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti");
		Iterator<String[]> i = result.iterator();
		
		String[] riga = i.next();
		Dipendente dip = new Dipendente(riga[1], riga[2], riga[3]);
		
		System.out.println("Dipendente");
		System.out.println("Nome: " + dip.getNome());
		System.out.println("Cognome: " + dip.getCognome());
		System.out.println("Email: " + dip.getEmail());
		
	}
}
