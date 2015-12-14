package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Sede;

public class DipendenteDAO {
	
	private static DipendenteDAO instance;
	public static DipendenteDAO getInstance(){
		if(instance == null)
			instance = new DipendenteDAO();
		return instance;
	}
	
	private DipendenteDAO(){};
	
	public List<Dipendente> getAllDipendenti() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.*, nome_sede FROM dipendenti,utenti WHERE id_utente = utenti.id");
		Iterator<String[]> i = result.iterator();
		List<Dipendente> listDipendente = new ArrayList<>();
		
		while(i.hasNext()) {
			String[] riga = i.next();
			int id = Integer.parseInt(riga[0]);
                        //DA SISTEMARE SE SERVE, ALTRIMENTI METODO DA SCARTARE
			//Dipendente dipendente = new Dipendente(id, riga[1], riga[2], riga[3], Sede.parseSede(riga[5]));
			//listDipendente.add(dipendente);
		}
		
		return listDipendente;
	}

	public Dipendente getDipendente(int id) {
		Dipendente dip;
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email,id_progetto,nome_sede FROM utenti,dipendenti WHERE id = id_utente AND id = " + id);
		Iterator<String[]> i = result.iterator();
		String[] riga = i.next();
                //CREA IL DIPENDENTE CON CARRELLO E PROGETTO NULL
		dip = new Dipendente(id, riga[1], riga[2], riga[3], Sede.parseSede(riga[4]), null, null);
                //VEDE SE HA UN PROGETTO DAL DATABASE
		if(!riga[4].equals("null") || riga[4] != null) {
                        //OTTIENE LE INFORMAZIONI SUL PROGETTO DAL DATABASE E GLIELO ASSOCIA
			dip.setProgetto(ProgettoDAO.getInstance().getProgetto(Integer.parseInt(riga[4])));
		}
                //OTTIENE IL SUO CARRELLO DAL DB E GLIELO ASSOCIA
                dip.setCarrello(CarrelloDAO.getInstance().getCarrello(dip));
		return dip; 
	}

	public void updateDipendente(Dipendente dipendente) {
		DBConnection.getInstance().updateDB("UPDATE utenti SET nome =" + dipendente.getNome() + ", cognome = " + dipendente.getCognome() + ", email = " + dipendente.getEmail() + " WHERE id = " + dipendente.getID());
	}

	public void deleteDipendente(Dipendente dipendente) {
		// DA SCRIVERE QUANDO SERVIRA'
		
	}
	
}
