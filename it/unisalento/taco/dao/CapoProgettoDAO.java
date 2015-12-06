package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Progetto;

public class CapoProgettoDAO {

	public ArrayList getAllCapoProgetto() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM capiprogetto");
		return result;
	}
	
	public CapoProgetto getCapoProgetto(Progetto progetto) {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email FROM utenti,capiprogetto WHERE id_progetto = " + progetto.getID() + " AND utenti.id = capiprogetto.id_utente");
		Iterator<String[]> i = result.iterator();
		String[] riga = i.next();
		int id = Integer.parseInt(riga[0]);
		CapoProgetto capoProg = new CapoProgetto(id, riga[1], riga[2], riga[3], progetto);
		return capoProg;
	}
	
}
