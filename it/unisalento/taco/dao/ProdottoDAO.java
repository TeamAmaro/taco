/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import java.util.ArrayList;
import java.util.Iterator;

public class ProdottoDAO {
    
    private static ProdottoDAO instance;
    
    public static ProdottoDAO getInstance() {
        if(instance == null)
            instance = new ProdottoDAO();
        return instance;
    }
    
    private ProdottoDAO(){}
    
}
