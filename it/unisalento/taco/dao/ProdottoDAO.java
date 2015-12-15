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
    
    public Prodotto getProdotto(int idProdotto){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT prodotti.*, produttori.nome from prodotti, produttori WHERE produttori.id_prodotto = id AND id = " + idProdotto);
        Iterator<String[]> i = result.iterator();
        Prodotto prodotto;
        if(i.hasNext()) {
            String[] riga = i.next();
            Produttore produttore = Produttore.parseProduttore(riga[5]);
            //SEMPLIFICATO
            prodotto = new Prodotto.Builder(idProdotto, riga[1], Double.parseDouble(riga[4]), produttore).build();
        }
        else
            prodotto = null;
        return prodotto;
    }
    
}
