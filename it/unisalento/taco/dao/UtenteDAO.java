package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Utente;

public class UtenteDAO {
	
	private static UtenteDAO instance;
	public static UtenteDAO getInstance(){
		if(instance == null)
			instance = new UtenteDAO(){};
		return instance;
	}
	private UtenteDAO (){};
	
	public List<? super Utente> getAllUtenti() {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti");
		Iterator<String[]> i = result.iterator();
		List<Utente> listUtente = new ArrayList<>();
		
		while(i.hasNext()) {
			String[] riga = i.next();
			int id = Integer.parseInt(riga[0]);
			
			/*
			 * Controlla nel database l'informazione che contraddistingue utente nelle categorie 
			 * dipendente - capoprogetto - magazziniere
			 * 
			 * Dopodiché basta istanziare DOPO il model corrispondente.
			 * 
			 * Attenzione:
			 * non dobbiamo istanziare tutti i dati del database come model! Questa cosa si fa solo quando
			 * è richiesto, ad esempio, se il superadmin ha bisogno di una lista completa di utenti e deve 
			 * effettuare un'operazione su uno di essi.
			 * 
			 * Esempio di istanziazionee
			 * Utente utente = new Dipendente(....);
			 * 
			 * Il compilatore potrebbe richiedere di castare l'oggetto nella classe specifica corrispondente.
			 */
			
			
			
			//Utente utente = new Utente(id, riga[1], riga[2], riga[3]);
			//listUtente.add(utente);
		}
		
		return listUtente;
	}
	
}
