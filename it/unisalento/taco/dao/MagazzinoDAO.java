/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
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

        String uberQuery = "SELECT produttori.nome,prodotti.nome,prodotti.categoria,prodotti.descrizione,prodotti.prezzo,prod_mag.quantita FROM prodotti,prod_mag,produttori,magazzini WHERE prod_mag.id_magazzino = " + magazzino.getID() + " AND prod_mag.id_magazzino = magazzini.id AND prodotti.id = prod_mag.id_prodotto AND produttori.id_prodotto = prodotti.id";
        ArrayList<String[]> result = DBConnection.getInstance().queryDB(uberQuery);
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> inventario = new LinkedHashMap<>();
        
        while(i.hasNext()) {
            String[] riga = i.next();
            double prezzo = Double.parseDouble(riga[4]);
            Prodotto prodotto = new Prodotto.Builder(riga[1], prezzo, Produttore.PRODUTTORE_A).categoria(Categoria.CATEGORIA_D).descrizione(riga[3]).build();
            int quantita = Integer.parseInt(riga[5]);
            inventario.put(prodotto,quantita);
        }
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
