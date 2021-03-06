package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoQueryMatchException;
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
    
    public CapoProgetto getCapoProgetto(String email) throws NoQueryMatchException {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti WHERE email = '" + email + "'");
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            CapoProgetto capoProg = new CapoProgetto(id, riga[1], riga[2], riga[3]);
            return capoProg;
        }
        else {
            throw new NoQueryMatchException("(#5) Nessun capoprogetto trovato per " + email);
        }
    }

    public CapoProgetto getCapoProgetto(Progetto progetto) throws NoQueryMatchException {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email FROM utenti JOIN capiprogetto ON utenti.id = capiprogetto.id_utente WHERE id_progetto = " + progetto.getId());
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            CapoProgetto capoProg = new CapoProgetto(id, riga[1], riga[2], riga[3]);
            return capoProg;
        }
        else {
            throw new NoQueryMatchException("(#5) Nessun capoprogetto trovato per " + progetto.getNome());
        }
    }

    @Override public CapoProgetto getById(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.id,utenti.nome,utenti.cognome,utenti.email FROM utenti WHERE utenti.id = " + id);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            CapoProgetto capoProgetto = new CapoProgetto(Integer.parseInt(riga[0]), riga[1], riga[2], riga[3]);
            return capoProgetto;
        }
        else {
            throw new NoIDMatchException(this, id);
        }
    }
    
    @Override public void create(CapoProgetto capoProgetto){
        DBConnection.getInstance().updateDB("INSERT INTO capiprogetto(id_utente) VALUES(" + capoProgetto.getId() + ")");
    }
    
    @Override public void update(CapoProgetto capoProgetto){
        //LA TABELLA CAPIPROGETTO HA SOLO L'ID DEL'UTENTE, NON DOVRA' MAI ESSERE AGGIORNATA
    }
    
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM capiprogetto WHERE id_utente = " + obj.getId());
    }
    
}
