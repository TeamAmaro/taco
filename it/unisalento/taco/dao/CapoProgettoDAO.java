package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Progetto;

public class CapoProgettoDAO implements DAOInterface<CapoProgetto>{
    
    private static CapoProgettoDAO instance;
    
    public static CapoProgettoDAO getInstance(){
        if(instance == null)
            instance = new CapoProgettoDAO(){};
        return instance;
    }
	
    private CapoProgettoDAO (){};

    public ArrayList getAllCapoProgetto() {
        //FUN FACT: QUESTA QUERY NON SI ABBINA CON LE POLACCHINE
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.id,utenti.nome,utenti.cognome,utenti.email FROM capiprogetto JOIN utenti ON id = id_utente");
        Iterator<String[]> i = result.iterator();
        ArrayList<CapoProgetto> capiProgetto = new ArrayList<>();;
        while(i.hasNext()){
            String[] riga = i.next();
            CapoProgetto capo = new CapoProgetto(Integer.parseInt(riga[0]), riga[1], riga[2], riga[3]);
            capiProgetto.add(capo);
        }
        return capiProgetto;
    }

    //OTTIENE IL CAPOPROGETTO TRAMITE IL PROGETTO
    public CapoProgetto getByProgetto(Progetto progetto) throws NoQueryMatchException {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email FROM utenti,capiprogetto WHERE id_progetto = " + progetto.getID() + " AND utenti.id = capiprogetto.id_utente");
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            CapoProgetto capoProg = new CapoProgetto(id, riga[1], riga[2], riga[3]);
            return capoProg;
        }
        else {
            throw new NoQueryMatchException(this);
        }
    }

    @Override public CapoProgetto getByID(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.id,utenti.nome,utenti.cognome,utenti.email FROM utenti WHERE utenti.id = " + id);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            CapoProgetto capoProgetto = new CapoProgetto(Integer.parseInt(riga[0]), riga[1], riga[2], riga[3]);
            return capoProgetto;
        }
        else {
            throw new NoIDMatchException(this);
        }
    }
    
    @Override public void create(CapoProgetto capoProgetto){
        DBConnection.getInstance().updateDB("INSERT INTO capiprogetto(id_utente) VALUES(" + capoProgetto.getID() + ")");
    }
    
    @Override public void update(CapoProgetto capoProgetto){
        //LA TABELLA CAPIPROGETTO HA SOLO L'ID DEL'UTENTE, NON DOVRA' MAI ESSERE AGGIORNATA
    }
    
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM capiprogetto WHERE id_utente = " + obj.getID());
    }
    
}
