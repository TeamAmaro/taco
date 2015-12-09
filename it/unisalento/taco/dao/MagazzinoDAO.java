/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MagazzinoDAO {

    private static MagazzinoDAO instance;
    
    public static MagazzinoDAO getInstance() {
        if(instance == null)
            instance = new MagazzinoDAO();
        return instance;
    }
    
    private MagazzinoDAO() {}
    
    public Map<Prodotto,Integer> getInventario(Magazzino magazzino) {
        //DA SCRIVERE 
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM progetti");
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> inventario = new LinkedHashMap<>();
        
        //inventario.put(prodottox,quantitax);
        
        return inventario;
    }
    
    /*public int getQuantita(Prodotto prodotto) {
        //DA SCRIVERE
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM progetti");
        Iterator<String[]> i = result.iterator();
        String[] riga = i.next();
        int quantita = Integer.parseInt(riga[0]);
        return quantita;
    }*/
    
    public List<Magazzino> cercaProdotto(Prodotto prodotto) {
        /*
        RESTITUISCO I MAGAZZINI CHE CONTENGONO IL PRODOTTO
        */
        List<Magazzino> magazzini = new ArrayList<>();
        return magazzini;
    }
    
    
    
}
