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
import it.unisalento.taco.model.Magazziniere;
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

    public Map<Prodotto,Integer> getInventario(int idMag) {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT pr.nome,p.*,pm.quantita FROM prod_mag pm JOIN magazzini m ON pm.id_magazzino = m.id JOIN prodotti p ON pm.id_prodotto = p.id JOIN produttori pr ON pr.id_prodotto = p.id WHERE m.id = " + idMag);
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> inventario = new LinkedHashMap<>();

        while(i.hasNext()) {
            String[] riga = i.next();
            Prodotto prodotto = new Prodotto.Builder(Integer.parseInt(riga[1]), riga[2], Double.parseDouble(riga[5]), Produttore.parseProduttore(riga[0])).categoria(Categoria.parseCategoria(riga[3])).descrizione(riga[4]).immagine(riga[6]).build();
            prodotto.setListaFornitori(ProdottoDAO.getInstance().getFornitore(prodotto));
            int quantita = Integer.parseInt(riga[7]);
            inventario.put(prodotto,quantita);
        }
        return inventario;
    }
    
    public int getQuantitaAll(Prodotto prodotto) {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT SUM(quantita) FROM prod_mag WHERE id_prodotto = " + prodotto.getId());
        Iterator<String[]> i = result.iterator();
        int quantita = 0;
        if(i.hasNext()) {
            String[] riga = i.next();
            quantita = Integer.parseInt(riga[0]);
        }
        return quantita;
    }
    
    public int getQuantitaProdotto(Magazzino magazzino, Prodotto prodotto) {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT quantita FROM prod_mag WHERE id_magazzino = " + magazzino.getId() + " AND id_prodotto = " + prodotto.getId());
        Iterator<String[]> i = result.iterator();
        int quantita = 0;
        if(i.hasNext()) {
            String[] riga = i.next();
            quantita = Integer.parseInt(riga[0]);
        }
        return quantita;
    }
    
    public Set<Magazzino> cercaProdotto(Prodotto prodotto){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT m.*,pm.quantita FROM magazzini m JOIN prod_mag pm ON m.id = pm.id_magazzino JOIN prodotti p ON p.id = pm.id_prodotto WHERE p.id = " + prodotto.getId() + " ORDER BY quantita DESC");
        Iterator<String[]> i = result.iterator();
        Set<Magazzino> magazzini = new LinkedHashSet<>();

        while(i.hasNext()){
            String[] riga = i.next();
            int quantita = Integer.parseInt(riga[4]);
            if(quantita == 0)
                continue;
            int idMag = Integer.parseInt(riga[0]);
            Map<Prodotto,Integer> inventario = new LinkedHashMap<>();
            inventario.put(prodotto, quantita);
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
            int idMagazziniere = Integer.parseInt(riga[3]);
            Magazzino magazzino = new Magazzino(idMag, riga[1], Sede.parseSede(riga[2]), getInventario(idMag));
            return magazzino;
        }
        else {
            throw new NoQueryMatchException(this);
        }
    }
    
    public Magazzino getMagazzino(Magazziniere magazziniere) throws NoQueryMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM magazzini WHERE id_magazziniere = " + magazziniere.getId());
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()) {
            String[] riga = i.next();
            int idMag = Integer.parseInt(riga[0]);
            Magazzino magazzino = new Magazzino(idMag, riga[1], Sede.parseSede(riga[2]), getInventario(idMag), magazziniere);
            return magazzino;
        }
        else {
            throw new NoQueryMatchException(this);
        }
    }
    
    @Override public Magazzino getById(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM magazzini WHERE id = " + id);
        Iterator<String[]> i = result.iterator();
        Magazzino magazzino;
        if(i.hasNext()) {
            String[] riga = i.next();
            int idMagazziniere = Integer.parseInt(riga[3]);
            try{
                Magazziniere magazziniere = MagazziniereDAO.getInstance().getById(idMagazziniere);
                Map<Prodotto, Integer> inventario = getInventario(id);
                magazzino = new Magazzino(id, riga[1], Sede.parseSede(riga[2]), inventario, magazziniere);
                return magazzino;
            }catch(NoIDMatchException e){
                throw e;
            }
        }
        else {
            throw new NoIDMatchException(id);
        }
    }
    
    public void updateQuantita(Magazzino magazzino, Prodotto prodotto, int quantita){
        DBConnection.getInstance().updateDB("UPDATE prod_mag SET quantita = " + quantita + " WHERE id_magazzino = " + magazzino.getId() + " AND id_prodotto = " + prodotto.getId());
    }
    
    @Override public void create(Magazzino magazzino){
        DBConnection.getInstance().updateDB("INSERT INTO magazzini(nome,nome_sede) VALUES('" + magazzino.getNome() + "','" + magazzino.getSede() + "')");
    }
    
    @Override public void update(Magazzino magazzino){
        if(magazzino.getMagazziniere() != null)
            DBConnection.getInstance().updateDB("UPDATE magazzini SET nome = " + magazzino.getNome() + ", nome_sede = " + magazzino.getSede() + ", id_magazziniere = " + magazzino.getMagazziniere().getId() + " WHERE id = " + magazzino.getId());
        else
            DBConnection.getInstance().updateDB("UPDATE magazzini SET nome = " + magazzino.getNome() + ", nome_sede = " + magazzino.getSede() + " WHERE id = " + magazzino.getId());
    }
    
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM magazzini WHERE id = " + obj.getId());
    }
}
