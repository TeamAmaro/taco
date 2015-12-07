package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Progetto;

public class DipendenteDAO {
	
	public Dipendente[] getAllDipendenti() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.* FROM dipendenti,utenti WHERE id_utente = utenti.id");
		Iterator<String[]> i = result.iterator();
		Dipendente[] dipendenti = new Dipendente[result.size()];
		int j = 0;
		
		while(i.hasNext()) {
			String[] riga = i.next();
			int id = Integer.parseInt(riga[0]);
			dipendenti[j++] = new Dipendente(id, riga[1], riga[2], riga[3]);
		}
		
		return dipendenti;
	}

	public Dipendente getDipendente(int id) {
		Dipendente dip;
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email,id_progetto,id_sede FROM utenti,dipendenti WHERE id = id_utente AND id = " + id);
		Iterator<String[]> i = result.iterator();
		String[] riga = i.next();
		dip = new Dipendente(id, riga[1], riga[2], riga[3]);
		if(!riga[4].equals("null") || riga[4] != null) {
			ProgettoDAO progDAO = new ProgettoDAO();
			int id_progetto = Integer.parseInt(riga[4]);
			dip.setProgetto(progDAO.getProgetto(id_progetto));
		} 
		return dip; 
	}

	public void updateDipendente(Dipendente dipendente) {
		DBConnection.getInstance().updateDB("UPDATE utenti SET nome =" + dipendente.getNome() + ", cognome = " + dipendente.getCognome() + ", email = " + dipendente.getEmail() + " WHERE id = " + dipendente.getID());
	}

	public void deleteDipendente(Dipendente dipendente) {
		// DA SCRIVERE QUANDO SERVIRA'
		
	}
	
}
