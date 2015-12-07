package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Progetto;

public class ProgettoDAO {
	
	public Progetto[] getAllProgetto() {
		
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM progetti");
		Iterator<String[]> i = result.iterator();
		Progetto[] progetti = new Progetto[result.size()];
		int j = 0;
		CapoProgettoDAO capProgDAO = new CapoProgettoDAO();
		
		
		while(i.hasNext()) {
			
			String[] riga = i.next();
			int id = Integer.parseInt(riga[0]);
			double saldo = Double.parseDouble(riga[2]);
			double budget = Double.parseDouble(riga[3]);
			
			progetti[j] = new Progetto(id, riga[1], null, saldo, budget);
			CapoProgetto capProg = capProgDAO.getCapoProgetto(progetti[j]);
			progetti[j].setCapoProgetto(capProg);
			j++;
			
			/*
			CapoProgetto capProg = capProgDAO.getCapoProgetto(id);
			progetti[j] = new Progetto(id, riga[1], capProg, saldo, budget);
			capProg.setProgetto(progetti[j++]);*/
		}
		
		return progetti;
	}

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
	
	public void addProgetto() {}

	public void updateProgetto() {
		// TODO Auto-generated method stub
		
	}

	public void deleteProgetto() {
		// TODO Auto-generated method stub
		
	}
	
}
