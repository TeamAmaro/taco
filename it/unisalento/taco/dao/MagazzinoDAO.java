/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import it.unisalento.taco.model.Sede;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class MagazzinoDAO implements DAOInterface<Magazzino>{

    private static MagazzinoDAO instance;

    public static MagazzinoDAO getInstance() {
        if(instance == null)
            instance = new MagazzinoDAO();
        return instance;
    }

    private MagazzinoDAO(){}

    //RESTITUISCE TUTTI I PRODOTTI DI UN MAGAZZINO
    public Map<Prodotto,Integer> getInventario(int idMag) {
        String uberQuery = "SELECT produttori.nome,prodotti.*,prod_mag.quantita FROM prodotti,prod_mag,produttori,magazzini WHERE prod_mag.id_magazzino = " + idMag + " AND prod_mag.id_magazzino = magazzini.id AND prodotti.id = prod_mag.id_prodotto AND produttori.id_prodotto = prodotti.id";
        ArrayList<String[]> result = DBConnection.getInstance().queryDB(uberQuery);
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> inventario = new LinkedHashMap<>();

        while(i.hasNext()) {
            String[] riga = i.next();
            Prodotto prodotto = new Prodotto.Builder(Integer.parseInt(riga[1]), riga[2], Double.parseDouble(riga[5]), Produttore.parseProduttore(riga[0])).categoria(Categoria.parseCategoria(riga[3])).descrizione(riga[4]).build();
            int quantita = Integer.parseInt(riga[6]);
            if(quantita == 0)
                continue;
            inventario.put(prodotto,quantita);
        }
        return inventario;
    }

    //RESTITUISCE LA QUANTITA DI UN PRODOTTO IN UN MAGAZZINO
    public int getQuantitaProdotto(Magazzino magazzino, Prodotto prodotto) {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT quantita FROM prod_mag WHERE id_magazzino = " + magazzino.getID() + " AND id_prodotto = " + prodotto.getID());
        Iterator<String[]> i = result.iterator();
        int quantita = 0;
        if(i.hasNext()) {
            String[] riga = i.next();
            quantita = Integer.parseInt(riga[0]);
        }
        return quantita;
    }

    //RESTITUISCE UN SET DI MAGAZZINI IN CUI E' PRESENTE UN PRODOTTO ORDINATI IN ORDINE DISCENDENTE
    //Non è necessario lanciare eccezzioni in quanto viene restituita una lista vuota se
    //non ci sono corrispondenze.
    public Set<Magazzino> cercaProdotto(Prodotto prodotto){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT m.*,pm.quantita FROM magazzini m JOIN prod_mag pm ON m.id = pm.id_magazzino JOIN prodotti p ON p.id = pm.id_prodotto WHERE p.id = " + prodotto.getID() + " GROUP BY quantita DESC");
        Iterator<String[]> i = result.iterator();
        Set<Magazzino> magazzini = new LinkedHashSet<>();

        while(i.hasNext()){
            String[] riga = i.next();
            //SE LA QUANTITA' E' 0, SALTA IL MAGAZZINO
            if(Integer.parseInt(riga[3]) == 0)
                continue;
            int idMag = Integer.parseInt(riga[0]);
            Magazzino magazzino = new Magazzino(idMag, riga[1], Sede.parseSede(riga[2]), getInventario(idMag));
            magazzini.add(magazzino);
        }
        return magazzini;
    }

    public Magazzino getMagazzino(Sede sede) throws NoQueryMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM magazzini WHERE nome_sede = \"" + sede.nome() + "\"");
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()) {
            String[] riga = i.next();
            int idMag = Integer.parseInt(riga[0]);
            Magazzino magazzino = new Magazzino(idMag, riga[1], Sede.parseSede(riga[2]), getInventario(idMag));
            return magazzino;
        }
        else {
            throw new NoQueryMatchException(this);
        }
    }
    @Override public Magazzino getByID(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM magazzini WHERE id = " + id);
        Iterator<String[]> i = result.iterator();
        Magazzino magazzino;
        if(i.hasNext()) {
            String[] riga = i.next();
            Map<Prodotto, Integer> inventario = getInventario(id);
            magazzino = new Magazzino(id, riga[1], Sede.parseSede(riga[2]), inventario);
            return magazzino;
        }
        else {
            throw new NoIDMatchException(this);
        }
    }
    
    public Magazzino getMagazzino(int id){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM magazzini WHERE id = " + id);
        Iterator<String[]> i = result.iterator();
        String[] riga = i.next();
        Magazzino mag = new Magazzino(Integer.parseInt(riga[0]), riga[1], Sede.parseSede(riga[2]), null);
        return mag;
    }
    
    @Override public void create(Magazzino magazzino){
        DBConnection.getInstance().updateDB("INSERT INTO magazzini(nome,nome_sede) VALUES('" + magazzino.getNome() + "','" + magazzino.getSede() + "')");
    }
    
    @Override public void update(Magazzino magazzino){
        //Da scrivere;
    }
    
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM magazzini WHERE id = " + obj.getID());
    }
}
