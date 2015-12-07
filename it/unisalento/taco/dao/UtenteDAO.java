package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Utente;

public class UtenteDAO {

	public Utente[] getAllUtenti() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti");
		Iterator<String[]> i = result.iterator();
		Utente[] utenti = new Utente[result.size()];
		int j = 0;
		
		while(i.hasNext()) {
			String[] riga = i.next();
			int id = Integer.parseInt(riga[0]);
			//utenti[j++] = new Utente(id, riga[1], riga[2], riga[3]);
		}
		
		return utenti;
	}
	
}
