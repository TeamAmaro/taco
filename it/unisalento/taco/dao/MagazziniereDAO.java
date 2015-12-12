/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Magazziniere;
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
        
        
}
