/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.model.Categoria;
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
    
    @Override
    public Prodotto getById(int id) throws NoIDMatchException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT produttori.nome,prodotti.* FROM prodotti JOIN produttori ON id_prodotto = id WHERE id = " + id);
        Iterator<String[]> i = result.iterator();

        if(i.hasNext()) {
            String[] riga = i.next();
            Produttore produttore = Produttore.parseProduttore(riga[0]);
            Prodotto prodotto = new Prodotto.Builder(id, riga[2], Double.parseDouble(riga[5]), produttore).descrizione(riga[4]).categoria(parseCategoria(riga[2])).immagine(riga[6]).annoDiProduzione(Integer.parseInt(riga[7])).build();
            Set<Fornitore> listaFornitori = getFornitore(prodotto);
            prodotto.setListaFornitori(listaFornitori);
            
            return prodotto;
        }
        else {
            throw new NoIDMatchException(this, id);
        }
    }
    
    public Set<Fornitore> getFornitore(Prodotto prodotto){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM fornitori WHERE id_prodotto = " + prodotto.getId());
        Iterator<String[]> i = result.iterator();
        Set<Fornitore> listaFornitori = new LinkedHashSet<>();
        while(i.hasNext()){
            String[] riga = i.next();
            listaFornitori.add(Fornitore.parseFornitore(riga[0]));
        }
        return listaFornitori;
    }
    
    public void addProduttore(Produttore produttore, Prodotto prodotto){
        DBConnection.getInstance().updateDB("INSERT INTO produttori(nome,id_prodotto) VALUES(nome = '" + produttore.nome() + "', id_prodotto = " + prodotto.getId() + ")");
    }
    
    public void addFornitore(Fornitore fornitore, Prodotto prodotto){
        DBConnection.getInstance().updateDB("INSERT INTO fornitori(nome, id_prodotto) VALUES(nome = '" + fornitore.nome() + "', id_prodotto = " + prodotto.getId() + ")");
    }
    
    public void addImmagine(Prodotto prodotto, String immagine){
        DBConnection.getInstance().updateDB("INSERT INTO prodotti(immagine) VALUES('" + immagine + "')");
    }
    
    public String getImmagine(Prodotto prodotto){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT immagine FROM prodotti WHERE id = " + prodotto.getId());
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            return riga[0];
        }
        else
            return "thumbnail.jpg";
    }
    
    @Override public void create(Prodotto prodotto){
        DBConnection.getInstance().updateDB("INSERT INTO prodotti(nome,categoria,descrizione,prezzo) VALUES('" + prodotto.getNome() + "','" + prodotto.getCategoria() + "','" + prodotto.getDescrizione() + "'," + prodotto.getPrezzo() + ")");
    }
    
    @Override public void update(Prodotto prodotto){
        DBConnection.getInstance().updateDB("UPDATE prodotti SET nome = " + prodotto.getNome() + ", categoria = " + prodotto.getCategoria() + ", descrizione = " + prodotto.getDescrizione() + ", prezzo = " + prodotto.getPrezzo() + " WHERE id = " + prodotto.getId());
    }
    
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM prodotti WHERE id = " + obj.getId());
    }

    public Set<Prodotto> cerca(String ricerca) throws NoIDMatchException {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id FROM prodotti WHERE nome LIKE '%" + ricerca + "%'");
        Iterator<String[]> i = result.iterator();
        Set<Prodotto> listaProdotti = new LinkedHashSet<>();
        int j = 0;
        while(i.hasNext() && j < 100){
            String[] riga = i.next();
            listaProdotti.add(getById(Integer.parseInt(riga[0])));
            j++;
        }
        return listaProdotti;
    }
    
    public Set<Prodotto> cerca(Categoria categoria) throws NoIDMatchException {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT id FROM prodotti WHERE categoria = \"" + categoria + "\"");
        Iterator<String[]> i = result.iterator();
        Set<Prodotto> listaProdotti = new LinkedHashSet<>();
        while(i.hasNext()){
            String[] riga = i.next();
            try{
                listaProdotti.add(getById(Integer.parseInt(riga[0])));
            }catch(NoIDMatchException e){
                throw e;
            }
        }
        return listaProdotti;
    }
    
}
