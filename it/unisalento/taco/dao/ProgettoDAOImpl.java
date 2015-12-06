package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Progetto;

public class ProgettoDAOImpl implements ProgettoDAO{

	@Override
	public ArrayList getAllProgetto() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM progetti");
		return result;
	}

	@Override
	public Progetto getProgetto(int id) {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT progetti.id,progetti.nome,progetti.saldo,progetti.budget,utenti.id,utenti.nome,utenti.cognome,utenti.email FROM utenti,progetti,capiprogetto WHERE capiprogetto.id_progetto = " + id + " AND capiprogetto.id_utente = utenti.id");
		Iterator<String[]> i = result.iterator();
		String[] riga = i.next();
		double saldo = Double.parseDouble(riga[2]);
		double budget = Double.parseDouble(riga[3]);
		int capoProgID = Integer.parseInt(riga[4]);
		CapoProgetto capoProg = new CapoProgetto(capoProgID, riga[5], riga[6], riga[7]);
		Progetto prog = new Progetto(id, riga[1], capoProg, saldo, budget);
		return prog;
	}
	
	//public void addProgetto() {}

	@Override
	public void updateProgetto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProgetto() {
		// TODO Auto-generated method stub
		
	}

}
