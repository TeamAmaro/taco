package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Progetto;
import java.util.Set;

public class CapoProgettoDAO {
    
    private static CapoProgettoDAO instance;
    
    public static CapoProgettoDAO getInstance(){
		if(instance == null)
			instance = new CapoProgettoDAO(){};
		return instance;
	}
	private CapoProgettoDAO (){};

    public ArrayList getAllCapoProgetto() {
            ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM capiprogetto");
            return result;
    }

    //OTTIENE IL CAPOPROGETTO TRAMITE IL PROGETTO
    public CapoProgetto getCapoProgetto(Progetto progetto) {
            ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id,nome,cognome,email FROM utenti,capiprogetto WHERE id_progetto = " + progetto.getID() + " AND utenti.id = capiprogetto.id_utente");
            Iterator<String[]> i = result.iterator();
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            CapoProgetto capoProg = new CapoProgetto(id, riga[1], riga[2], riga[3], progetto);
            return capoProg;
    }

    public CapoProgetto getCapoProgetto(int id){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.id,utenti.nome,utenti.cognome,utenti.email,progetti.id,progetti.nome FROM utenti,progetti,capiprogetto WHERE utenti.id = " + id + " AND utenti.id = id_utente AND id_progetto = progetti.id");
        Iterator<String[]> i = result.iterator();
        String[] riga = i.next();
        CapoProgetto capoProgetto = new CapoProgetto(Integer.parseInt(riga[0]), riga[1], riga[2], riga[3]);
        Set<Progetto> listaProgetti = ProgettoDAO.getInstance().getProgetto(capoProgetto);
        capoProgetto.setListaProgetti(listaProgetti);
        return capoProgetto;
    }

    /*//OTTIENE IL CAPOPROGETTO TRAMITE L'ID DEL PROGETTO
    public CapoProgetto getCapoProgetto(int idProgetto) {
            ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.* FROM progetti,capiprogetto,utenti WHERE id_progetto = progetti.i AND id_progetto = " + idProgetto + " AND id_utente = utenti.id");
            Iterator<String[]> i = result.iterator();
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            CapoProgetto capoProg = new CapoProgetto(id, riga[1], riga[2], riga[3]);
            return capoProg;
    }*/
	
}
