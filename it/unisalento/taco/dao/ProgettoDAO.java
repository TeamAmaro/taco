package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProgettoDAO {
	
	private static ProgettoDAO instance;
	
	public static ProgettoDAO getInstance(){
		if(instance == null)
                    instance = new ProgettoDAO();
		return instance;
	}
	private ProgettoDAO (){};
	
	
	public List<Progetto> getAllProgetto() {
		
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM progetti");
		Iterator<String[]> i = result.iterator();
		List<Progetto> listProgetto = new ArrayList<>();
		CapoProgettoDAO capProgDAO = CapoProgettoDAO.getInstance();
		
		
		while(i.hasNext()) {
			String[] riga = i.next();
			int id = Integer.parseInt(riga[0]);
			double saldo = Double.parseDouble(riga[2]);
			double budget = Double.parseDouble(riga[3]);
			Progetto progetto = new Progetto(id, riga[1], null, saldo, budget);
			listProgetto.add(progetto);
			CapoProgetto capProg = capProgDAO.getCapoProgetto(progetto);
			progetto.setCapoProgetto(capProg);
		}
		
		return listProgetto;
	}

	public Progetto getProgetto(int id) {
		ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT progetti.id,progetti.nome,progetti.saldo,progetti.budget,utenti.id,utenti.nome,utenti.cognome,utenti.email FROM utenti,progetti,capiprogetto WHERE capiprogetto.id_progetto = " + id + " AND capiprogetto.id_utente = utenti.id");
		Iterator<String[]> i = result.iterator();
		String[] riga = i.next();
		double saldo = Double.parseDouble(riga[2]);
		double budget = Double.parseDouble(riga[3]);
		int capoProgID = Integer.parseInt(riga[4]);
		CapoProgetto capoProg = new CapoProgetto(capoProgID, riga[5], riga[6], riga[7]);
		Progetto prog = new Progetto(id, riga[1], capoProg, saldo, budget, new LinkedHashSet<Ordine>());
                //Cerco Ordini nel database per quell'ordine
                prog.setListaOrdini(OrdineDAO.getInstance().getListaOrdini(prog));
		return prog;
	}
        
        public Set<Progetto> getProgetto(CapoProgetto capoProgetto) {
            ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT progetti.* FROM progetti,capiprogetto WHERE id_progetto = progetti.id AND id_utente =" + capoProgetto.getID());
            Iterator<String[]> i = result.iterator();
            Set<Progetto> listaProgetti = new LinkedHashSet<>();
            while(i.hasNext()) {
                String[] riga = i.next();
                //DA IMPLEMENTARE !
                Progetto progetto = new Progetto(Integer.parseInt(riga[0]), riga[1], capoProgetto, Double.parseDouble(riga[2]), Double.parseDouble(riga[3]), ... );
                listaProgetti.add(progetto);
            }
            
            return listaProgetti;
        }
	
	public void addProgetto() {
		
	}

	public void updateProgetto() {
		// TODO Auto-generated method stub
		
	}

	public void deleteProgetto() {
		// TODO Auto-generated method stub
		
	}
	
}
