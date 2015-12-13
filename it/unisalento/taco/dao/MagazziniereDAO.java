/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Sede;
import java.util.ArrayList;
import java.util.Iterator;

public class MagazziniereDAO {
    
    private static MagazziniereDAO instance;
    public static MagazziniereDAO getInstance(){
            if(instance == null)
                    instance = new MagazziniereDAO();
            return instance;
    }
    
    private MagazziniereDAO() {}
        
    public Magazziniere getMagazziniere(int id) {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.id,utenti.nome,utenti.cognome,utenti.email,magazzini.* FROM utenti,magazzinieri,magazzini WHERE utenti.id = id_utente AND utenti.id = " + id + " AND magazzini.id = magazzinieri.id_magazzino");
        Iterator<String[]> i = result.iterator();
	String[] riga = i.next();
        Magazziniere magazziniere = new Magazziniere(id, riga[1], riga[2], riga[3]);
        Magazzino magazzino = new Magazzino(Integer.parseInt(riga[4]), riga[5], Sede.parseSede(riga[6]));
        magazziniere.setMagazzino(magazzino);
        return magazziniere;
    }
        
}
