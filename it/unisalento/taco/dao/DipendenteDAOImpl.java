package it.unisalento.taco.dao;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.*;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Progetto;

public class DipendenteDAOImpl implements DipendenteDAO {

	
	@Override
	public ArrayList getAllDipendenti() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti");
		return result;
	}

	@Override
	public Dipendente getDipendente(int id) {
		Dipendente dip;
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email,id_progetto,id_sede FROM utenti,dipendenti WHERE id = id_utente AND id = " + id);
		Iterator<String[]> i = result.iterator();
		String[] riga = i.next();
		dip = new Dipendente(id, riga[1], riga[2], riga[3]);
		if(!riga[4].equals("null") || riga[4] != null) {
			ProgettoDAO progDAO = new ProgettoDAOImpl();
			int id_progetto = Integer.parseInt(riga[4]);
			dip.setProgetto(progDAO.getProgetto(id_progetto));
		} 
		return dip; 
	}

	@Override
	public void updateDipendente(Dipendente dipendente) {
		DBConnection.getInstance().updateDB("UPDATE utenti SET nome =" + dipendente.getNome() + ", cognome = " + dipendente.getCognome() + ", email = " + dipendente.getEmail() + " WHERE id = " + dipendente.getID());
	}

	@Override
	public void deleteDipendente(Dipendente dipendente) {
		// DA SCRIVERE QUANDO SERVIRA'
		
	}
	
}
