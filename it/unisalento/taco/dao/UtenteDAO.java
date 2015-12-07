package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Utente;

public class UtenteDAO {

	/*public List<Utente> getAllUtenti() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti");
		Iterator<String[]> i = result.iterator();
		List<Utente> listUtente = new ArrayList<>();
		
		while(i.hasNext()) {
			String[] riga = i.next();
			int id = Integer.parseInt(riga[0]);
			//UTENTE NON PUO' ESSERE ISTANZIATO
			Utente utente = new Utente(id, riga[1], riga[2], riga[3]);
			listUtente.add(utente);
		}
		
		return listUtente;
	}*/
	
}
