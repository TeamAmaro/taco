/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import java.util.ArrayList;
import java.util.Iterator;

public class ProdottoDAO implements DAOInterface{
    
    private static ProdottoDAO instance;
    
    public static ProdottoDAO getInstance() {
        if(instance == null)
            instance = new ProdottoDAO();
        return instance;
    }
    
    private ProdottoDAO(){}
    
    @Override public Prodotto getByID(int id) throws NoIDMatchException{
        
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT prodotti.*, produttori.nome from prodotti, produttori WHERE produttori.id_prodotto = id AND id = " + id);
        Iterator<String[]> i = result.iterator();

        if(i.hasNext()) {
            String[] riga = i.next();
            Produttore produttore = Produttore.parseProduttore(riga[5]);
            //SI DEVE OTTENERE LA LISTA DEI FORNITORI, LA CATEGORIA, ECC..
            Prodotto prodotto = new Prodotto.Builder(id, riga[1], Double.parseDouble(riga[4]), produttore).build();
            return prodotto;
        }
        else {
            throw new NoIDMatchException(this);
        }
    }
    
}
