package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Sede;
import java.util.LinkedHashSet;
import java.util.Set;

public class DipendenteDAO implements DAOInterface{
	
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
            Carrello carrello = CarrelloDAO.getInstance().getByID(id);
            Dipendente dip = new Dipendente(id, riga[1], riga[2], riga[3], Sede.parseSede(riga[5]), carrello);
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

    public void updateDipendente(Dipendente dipendente) {
        DBConnection.getInstance().updateDB("UPDATE utenti SET nome =" + dipendente.getNome() + ", cognome = " + dipendente.getCognome() + ", email = " + dipendente.getEmail() + " WHERE id = " + dipendente.getID());
    }

    public void deleteDipendente(Dipendente dipendente) {
        // DA SCRIVERE QUANDO SERVIRA'
    }
}
