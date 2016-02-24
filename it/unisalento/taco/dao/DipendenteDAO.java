package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;
import java.util.LinkedHashSet;
import java.util.Set;

public class DipendenteDAO implements DAOInterface<Dipendente>{
	
    private static DipendenteDAO instance;

    public static DipendenteDAO getInstance(){
        if(instance == null)
            instance = new DipendenteDAO();
        return instance;
    }

    private DipendenteDAO(){};

    @Override 
    public Dipendente getById(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email,nome_sede FROM utenti,dipendenti WHERE id = id_utente AND id = " + id);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            Dipendente dip = new Dipendente(id, riga[1], riga[2], riga[3], Sede.parseSede(riga[4]));
            return dip; 
        }
        else {
            throw new NoIDMatchException(this, id);
        }
    }
    
    public Dipendente getDipendente(String email){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti WHERE email = '" + email + "'");
        Iterator<String[]> i = result.iterator();
        String[] riga = i.next();
        Dipendente dip = new Dipendente(Integer.parseInt(riga[0]), riga[1], riga[2], riga[3], null);
        return dip;
    }
    
    
    public Set<Dipendente> getListaDipendenti(int idProg) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM dipendenti WHERE id_progetto = " + idProg);
        Iterator<String[]> i = result.iterator();
        Set<Dipendente> listaDipendenti = new LinkedHashSet<>();
        while(i.hasNext()){
            try{
                String[] riga = i.next();
                Dipendente dipendente = getById(Integer.parseInt(riga[0]));
                listaDipendenti.add(dipendente);
            }
            catch (NoIDMatchException e){
                throw e;
            }
        }
        return listaDipendenti;
    }
    
    public void setProgetto(Dipendente dip, Progetto progetto){
        DBConnection.getInstance().updateDB("UPDATE dipendenti SET id_progetto = " + progetto.getId() + " WHERE id_utente = " + dip.getId());
    }

    @Override public void create(Dipendente dip){
        DBConnection.getInstance().updateDB("INSERT INTO dipendenti(id_utente, nome_sede) VALUES(" + dip.getId() + ",'" + dip.getSede() + "')");
    }
    
    @Override public void update(Dipendente dip) {
        DBConnection.getInstance().updateDB("UPDATE dipendenti SET id_utente = " + dip.getId() + ", nome_sede = " + dip.getSede() + "WHERE id = " + dip.getId());
    }

    @Override public void delete(IdentificabileID obj) {
        DBConnection.getInstance().updateDB("DELETE FROM dipendenti WHERE id_utente = " + obj.getId());
    }
}
