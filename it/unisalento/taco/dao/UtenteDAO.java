package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoSuchUserException;
import it.unisalento.taco.model.Dipendente;
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
			 * Dopodich� basta istanziare DOPO il model corrispondente.
			 * 
			 * Attenzione:
			 * non dobbiamo istanziare tutti i dati del database come model! Questa cosa si fa solo quando
			 * � richiesto, ad esempio, se il superadmin ha bisogno di una lista completa di utenti e deve 
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
	
        public int getID(String email, String psw) throws NoSuchUserException{
            
            ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti WHERE email = '" + email + "' AND password = '" + psw +"'");
            Iterator<String[]> i = result.iterator();
            if(i.hasNext()){
                String[] riga = i.next();
                return Integer.parseInt(riga[0]);
            }
            else {
                throw new NoSuchUserException("Login: Nessuna corrispondenza tra mail e password.");
            }
        }
        
        //SI SUPPONE CHE L'ID ESISTA
        public Utente getLogin(int id){
            String tabella = "";
            for(int i = 0; i < 3; i++){
                switch(i){
                    case 0: tabella = "dipendenti";
                        break;
                    case 1: tabella = "capiprogetto";
                        break;
                    case 2: tabella = "magazzinieri";
                        break;                    
                }
                
                ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM " + tabella + " WHERE id_utente = " + id);
                Iterator<String[]> j = result.iterator();
                if(j.hasNext())
                    switch(i){
                        case 0: return DipendenteDAO.getInstance().getByID(id);
                        case 1: return CapoProgettoDAO.getInstance().getByID(id);
                        case 2: return MagazziniereDAO.getInstance().getMagazziniere(id);
                    }
                
            }
            return null;
            
        }
}
