package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Progetto;

public class ProgettoDAOImpl implements ProgettoDAO{

	@Override
	public ArrayList getAllProgetto() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM progetti");
		return result;
	}

	@Override
	public Progetto getProgetto(int id) {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,saldo,budget FROM progetti WHERE id = " + id);
		Iterator<String[]> i = result.iterator();
		String[] riga = i.next();
		double saldo = Double.parseDouble(riga[2]);
		double budget = Double.parseDouble(riga[3]);
		Progetto prog = new Progetto(id, riga[1], saldo, budget);
		return prog;
	}

	@Override
	public void updateProgetto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProgetto() {
		// TODO Auto-generated method stub
		
	}

}
