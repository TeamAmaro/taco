package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoProgettoException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProgettoDAO implements DAOInterface<Progetto>{
	
    private static ProgettoDAO instance;

    public static ProgettoDAO getInstance(){
        if(instance == null)
            instance = new ProgettoDAO();
        return instance;
    }
    private ProgettoDAO (){};

    @Override 
    public Progetto getById(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT progetti.*,utenti.nome,cognome,email FROM progetti JOIN utenti ON id_capoprog = utenti.id WHERE progetti.id = " + id);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            double saldo = Double.parseDouble(riga[3]);
            double budget = Double.parseDouble(riga[4]);
            CapoProgetto capoProg = new CapoProgetto(Integer.parseInt(riga[2]), riga[5], riga[6], riga[7]);
            Set<Dipendente> listaDipendenti = DipendenteDAO.getInstance().getListaDipendenti(id);
            Progetto prog = new Progetto(id, riga[1], capoProg, saldo, budget, listaDipendenti);
            return prog;
        }
        else {
            throw new NoIDMatchException(this, id);
        }
    }
    
    public Progetto getProgetto(Dipendente dipendente) throws NoIDMatchException, NoProgettoException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id_progetto FROM dipendenti WHERE id_utente = " + dipendente.getId());
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            int idProgetto = Integer.parseInt(riga[0]);
            Progetto progetto = getById(idProgetto);
            return progetto;

        }
        else
            throw new NoProgettoException(dipendente.getId());
    }
    
    public Progetto getProgetto(String nome){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT p.*,u.* FROM progetti p JOIN utenti u ON u.id = p.id_capoprog WHERE p.nome = '" + nome + "'");
        Iterator<String[]> i = result.iterator();
        String[] riga = i.next();
        CapoProgetto capo = new CapoProgetto(Integer.parseInt(riga[5]), riga[6], riga[7], riga[8]);
        Progetto progetto =  new Progetto(Integer.parseInt(riga[0]), riga[1], capo, Double.parseDouble(riga[3]), Double.parseDouble(riga[4]), null);
        progetto.setListaDipendenti(getListaDipendenti(progetto));
        return progetto;
    }
    
    public List<Progetto> getProgetto(CapoProgetto capoProgetto){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT p.*,u.* FROM progetti p JOIN utenti u ON u.id = p.id_capoprog WHERE id_capoprog = " + capoProgetto.getId());
        Iterator<String[]> i = result.iterator();
        List<Progetto> listaProgetti = new ArrayList<>();
        while(i.hasNext()){
            String[] riga = i.next();
            Progetto progetto =  new Progetto(Integer.parseInt(riga[0]), riga[1], capoProgetto, Double.parseDouble(riga[3]), Double.parseDouble(riga[4]), null);
            progetto.setListaDipendenti(getListaDipendenti(progetto));
            listaProgetti.add(progetto);
        }
        return listaProgetti;
    }
    
    public Set<Dipendente> getListaDipendenti(Progetto prog){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT u.*,d.nome_sede FROM utenti u JOIN dipendenti d ON u.id = d.id_utente WHERE d.id_progetto = " + prog.getId());
        Iterator<String[]> i = result.iterator();
        Set<Dipendente> listaDipendenti = new LinkedHashSet<>();
        while(i.hasNext()){
            String[] riga = i.next();
            Dipendente dipendente = new Dipendente(Integer.parseInt(riga[0]), riga[1], riga[2], riga[3], Sede.parseSede(riga[5]));
            listaDipendenti.add(dipendente);
        }
        return listaDipendenti;
    }
    
    public Set<Progetto> getListaProgetti(CapoProgetto capoProgetto) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT progetti.* FROM progetti,capiprogetto WHERE id_progetto = progetti.id AND id_utente =" + capoProgetto.getId());
        Iterator<String[]> i = result.iterator();
        Set<Progetto> listaProgetti = new LinkedHashSet<>();
        while(i.hasNext()) {
            String[] riga = i.next();
            int idProg = Integer.parseInt(riga[0]);
            Set<Dipendente> listaDipendenti = DipendenteDAO.getInstance().getListaDipendenti(idProg);
            Progetto progetto = new Progetto(idProg, riga[1], capoProgetto, Double.parseDouble(riga[2]), Double.parseDouble(riga[3]), listaDipendenti);
            listaProgetti.add(progetto);

        }
        return listaProgetti;
    }
    
    public void setCapoProgetto(Progetto prog, CapoProgetto capo){
        DBConnection.getInstance().updateDB("UPDATE progetti SET id_capoprog = " + capo.getId() + " WHERE id = " + prog.getId());
    }
    
    public void updateSaldo(Progetto prog, double saldo){
        DBConnection.getInstance().updateDB("UPDATE progetti SET saldo = " + saldo + " WHERE id = " + prog.getId());
    }

    @Override public void create(Progetto prog){
        DBConnection.getInstance().updateDB("INSERT INTO progetti(nome,id_capoprog,saldo,budget) VALUES(nome = '" + prog.getNome() + "', id_capoprog = " + prog.getCapoProgetto().getId() + ", saldo = " + prog.getSaldo() + ", budget = " + prog.getBudget() + ")");
    }
    
    @Override public void update(Progetto prog) {
        DBConnection.getInstance().updateDB("UPDATE progetti SET nome = '" + prog.getNome() + "', id_capoprog = " + prog.getCapoProgetto().getId() + ", saldo = " + prog.getSaldo() + ", budget = " + prog.getBudget());
    }

    @Override public void delete(IdentificabileID obj) {
        DBConnection.getInstance().updateDB("DELETE FROM progetti WHERE id = " + obj.getId());
    }
	
}
