/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import static it.unisalento.taco.model.Categoria.parseCategoria;
import it.unisalento.taco.model.Fornitore;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProdottoDAO implements DAOInterface<Prodotto>{
    
    private static ProdottoDAO instance;
    
    public static ProdottoDAO getInstance() {
        if(instance == null)
            instance = new ProdottoDAO();
        return instance;
    }
    
    private ProdottoDAO(){}
    
    @Override public Prodotto getByID(int id) throws NoIDMatchException{
        //FUN FACT: QUESTA NUOVA QUERY E' PIU' ELEGANTE DI QUELLA DI PRIMA DEL 67%
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT produttori.nome,prodotti.* FROM prodotti JOIN produttori ON id_prodotto = id WHERE id = " + id);
        Iterator<String[]> i = result.iterator();

        if(i.hasNext()) {
            String[] riga = i.next();
            Produttore produttore = Produttore.parseProduttore(riga[0]);
            Prodotto prodotto = new Prodotto.Builder(id, riga[2], Double.parseDouble(riga[5]), produttore).descrizione(riga[4]).categoria(parseCategoria(riga[2])).build();
            Set<Fornitore> listaFornitori = getFornitore(prodotto);
            prodotto.setListaFornitori(listaFornitori);
            return prodotto;
        }
        else {
            throw new NoIDMatchException(this);
        }
    }
    
    public Set<Fornitore> getFornitore(Prodotto prodotto){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM fornitori WHERE id_prodotto = " + prodotto.getID());
        Iterator<String[]> i = result.iterator();
        Set<Fornitore> listaFornitori = new LinkedHashSet<>();
        while(i.hasNext()){
            String[] riga = i.next();
            listaFornitori.add(Fornitore.parseFornitore(riga[0]));
        }
        
        return listaFornitori;
    }
    
    public void addProduttore(Produttore produttore, Prodotto prodotto){
        DBConnection.getInstance().updateDB("INSERT INTO produttori(nome,id_prodotto) VALUES(nome = '" + produttore.nome() + "', id_prodotto = " + prodotto.getID() + ")");
    }
    
    public void addFornitore(Fornitore fornitore, Prodotto prodotto){
        DBConnection.getInstance().updateDB("INSERT INTO fornitori(nome, id_prodotto) VALUES(nome = '" + fornitore.nome() + "', id_prodotto = " + prodotto.getID() + ")");
    }
    
    @Override public void create(Prodotto prodotto){
        DBConnection.getInstance().updateDB("INSERT INTO prodotti(nome,categoria,descrizione,prezzo) VALUES('" + prodotto.getNome() + "','" + prodotto.getCategoria() + "','" + prodotto.getDescrizione() + "'," + prodotto.getPrezzo() + ")");
    }
    
    @Override public void update(Prodotto prodotto){
        DBConnection.getInstance().updateDB("UPDATE prodotti SET nome = " + prodotto.getNome() + ", categoria = " + prodotto.getCategoria() + ", descrizione = " + prodotto.getDescrizione() + ", prezzo = " + prodotto.getPrezzo() + " WHERE id = " + prodotto.getID());
    }
    
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM prodotti WHERE id = " + obj.getID());
    }
    
}
