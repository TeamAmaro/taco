package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProgettoDAO implements DAOInterface{
	
    private static ProgettoDAO instance;

    public static ProgettoDAO getInstance(){
        if(instance == null)
            instance = new ProgettoDAO();
        return instance;
    }
    private ProgettoDAO (){};


    public List<Progetto> getAllProgetto() throws NoIDMatchException{

        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM progetti. capiprogetto where id_progetto = id");
        Iterator<String[]> i = result.iterator();
        List<Progetto> listProgetto = new ArrayList<>();
        CapoProgettoDAO capProgDAO = CapoProgettoDAO.getInstance();
        Set<Ordine> listaOrdini = new LinkedHashSet<>();

        while(i.hasNext()) {
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            double saldo = Double.parseDouble(riga[2]);
            double budget = Double.parseDouble(riga[3]);
            int idCapo = Integer.parseInt(riga[4]);
            try{
                Set<Dipendente> listaDipendenti = DipendenteDAO.getInstance().getListaDipendenti(id);
                CapoProgetto capoProgetto = CapoProgettoDAO.getInstance().getByID(idCapo);
                Progetto progetto = new Progetto(id, riga[1], capoProgetto, saldo, budget, listaDipendenti);
                listProgetto.add(progetto);
            }
            catch (NoIDMatchException e){
                throw e;
            }
        }
        return listProgetto;
    }

    @Override public Progetto getByID(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT progetti.id,progetti.nome,progetti.saldo,progetti.budget,utenti.id,utenti.nome,utenti.cognome,utenti.email FROM utenti,progetti,capiprogetto WHERE capiprogetto.id_progetto = " + id + " AND capiprogetto.id_utente = utenti.id");
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            double saldo = Double.parseDouble(riga[2]);
            double budget = Double.parseDouble(riga[3]);
            int capoProgID = Integer.parseInt(riga[4]);
            try {
            CapoProgetto capoProg = CapoProgettoDAO.getInstance().getByID(capoProgID);
            Set<Dipendente> listaDipendenti = DipendenteDAO.getInstance().getListaDipendenti(id);
            Progetto prog = new Progetto(id, riga[1], capoProg, saldo, budget, listaDipendenti);
            return prog;
            }
            catch (NoIDMatchException e){
                throw e;
            }
        }
        else {
            throw new NoIDMatchException(this);
        }
    }

    public Set<Progetto> getListaProgetti(CapoProgetto capoProgetto) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT progetti.* FROM progetti,capiprogetto WHERE id_progetto = progetti.id AND id_utente =" + capoProgetto.getID());
        Iterator<String[]> i = result.iterator();
        Set<Progetto> listaProgetti = new LinkedHashSet<>();
        while(i.hasNext()) {
            String[] riga = i.next();
            int idProg = Integer.parseInt(riga[0]);
            try{
            Set<Dipendente> listaDipendenti = DipendenteDAO.getInstance().getListaDipendenti(idProg);
            Progetto progetto = new Progetto(idProg, riga[1], capoProgetto, Double.parseDouble(riga[2]), Double.parseDouble(riga[3]), listaDipendenti);
            listaProgetti.add(progetto);
            }
            catch (NoIDMatchException e) {
                throw e;
            }
        }
        return listaProgetti;
    }

    public void updateProgetto() {
        // TODO Auto-generated method stub

    }

    public void deleteProgetto() {
        // TODO Auto-generated method stub

    }
	
}
