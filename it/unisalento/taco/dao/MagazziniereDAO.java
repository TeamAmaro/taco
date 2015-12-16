/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Sede;
import java.util.ArrayList;
import java.util.Iterator;

public class MagazziniereDAO implements DAOInterface{
    
    private static MagazziniereDAO instance;
    
    public static MagazziniereDAO getInstance(){
        if(instance == null)
            instance = new MagazziniereDAO();
        return instance;
    }
    
    private MagazziniereDAO(){}
        
    @Override public Magazziniere getByID(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.id,utenti.nome,utenti.cognome,utenti.email,magazzini.* FROM utenti,magazzinieri,magazzini WHERE utenti.id = id_utente AND utenti.id = " + id + " AND magazzini.id = magazzinieri.id_magazzino");
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            int idMag = Integer.parseInt(riga[4]);
            Magazzino magazzino = new Magazzino(idMag, riga[5], Sede.parseSede(riga[6]), MagazzinoDAO.getInstance().getInventario(idMag));
            Magazziniere magazziniere = new Magazziniere(id, riga[1], riga[2], riga[3], magazzino);
            return magazziniere;
        }
        else {
            throw new NoIDMatchException(this);
        }
    }
        
}
