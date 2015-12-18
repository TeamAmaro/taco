package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.IdentificabileID;
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

    public List<Dipendente> getAllDipendenti() throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.*, nome_sede FROM dipendenti,utenti WHERE id_utente = utenti.id");
        Iterator<String[]> i = result.iterator();
        List<Dipendente> listaDipendenti = new ArrayList<>();

        while(i.hasNext()) {
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            try{
            Dipendente dipendente = getByID(id);
            listaDipendenti.add(dipendente);
            }
            catch(NoIDMatchException e){
                throw e;
            }
        }
        return listaDipendenti;
}

    @Override public Dipendente getByID(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email,nome_sede FROM utenti,dipendenti WHERE id = id_utente AND id = " + id);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            Dipendente dip = new Dipendente(id, riga[1], riga[2], riga[3], Sede.parseSede(riga[4]));
            return dip; 
        }
        else {
            throw new NoIDMatchException(this);
        }
    }
    
    
    public Set<Dipendente> getListaDipendenti(int idProg) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM dipendenti WHERE id_progetto = " + idProg);
        Iterator<String[]> i = result.iterator();
        Set<Dipendente> listaDipendenti = new LinkedHashSet<>();
        while(i.hasNext()){
            try{
                String[] riga = i.next();
                Dipendente dipendente = getByID(Integer.parseInt(riga[0]));
                listaDipendenti.add(dipendente);
            }
            catch (NoIDMatchException e){
                throw e;
            }
        }
        return listaDipendenti;
    }

    @Override public void create(Dipendente dip){
        //DA AGGIORNARE PER ID_PROGETTO
        //DBConnection.getInstance().updateDB("INSERT INTO dipendenti(id_utente, id_progetto, nome_sede) VALUES(" + dip.getID() + "," + IDPROGETTO + ",'" + dip.getSede() + "')");
    }
    
    @Override public void update(Dipendente dip) {
        //DA AGGIORNARE PER ID_PROGETTO
        //DBConnection.getInstance().updateDB("UPDATE dipendenti SET id_utente = " + dip.getID() + ", id_progetto = " + IDPROGETTO + ", nome_sede = " + dip.getSede() + "WHERE id = " + dip.getID());
    }

    @Override public void delete(IdentificabileID obj) {
        DBConnection.getInstance().updateDB("DELETE FROM dipendenti WHERE id_utente = " + obj.getID());
    }
}
