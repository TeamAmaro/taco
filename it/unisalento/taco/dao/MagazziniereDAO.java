/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Magazziniere;
import java.util.ArrayList;
import java.util.Iterator;

public class MagazziniereDAO implements DAOInterface<Magazziniere>{
    
    private static MagazziniereDAO instance;
    
    public static MagazziniereDAO getInstance(){
        if(instance == null)
            instance = new MagazziniereDAO();
        return instance;
    }
    
    private MagazziniereDAO(){}
        
    @Override public Magazziniere getByID(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT utenti.id,utenti.nome,utenti.cognome,utenti.email FROM utenti WHERE utenti.id = " + id);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            Magazziniere magazziniere = new Magazziniere(id, riga[1], riga[2], riga[3]);
            return magazziniere;
        }
        else {
            throw new NoIDMatchException(id);
        }
    }
    
    public Magazziniere getMagazziniere(String email){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti WHERE email = '" + email + "'");
        Iterator<String[]> i = result.iterator();
        String[] riga = i.next();
        Magazziniere mag = new Magazziniere(Integer.parseInt(riga[0]), riga[1], riga[2], riga[3]);
        return mag;
    }
    
    @Override public void create(Magazziniere magazziniere){
        //Niente
    }
    
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM magazzinieri WHERE id_utente = " + obj.getID());
    }
    
    @Override public void update(Magazziniere magazziniere){
        //Niente
    }
}
