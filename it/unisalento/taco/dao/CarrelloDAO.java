/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Prodotto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarrelloDAO implements DAOInterface<Carrello>{
    
    private static CarrelloDAO instance;
    
    public static CarrelloDAO getInstance() {
        if(instance == null)
            instance = new CarrelloDAO();
        return instance;
    }
    
    private CarrelloDAO(){}

    public Map<Prodotto,Integer> getListaProdotti(int idDip) throws NoIDMatchException{
        
        String uberQuery = "SELECT produttori.nome,prodotti.*,carrelli.quantita FROM prodotti,carrelli,produttori,dipendenti WHERE carrelli.id_prodotto = prodotti.id AND dipendenti.id_utente = " + idDip + " AND produttori.id_prodotto = prodotti.id";
        ArrayList<String[]> result = DBConnection.getInstance().queryDB(uberQuery);
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> listaProdotti = new LinkedHashMap<>();
        
        while(i.hasNext()) {
            String[] riga = i.next();
            try{
                int idProdotto = Integer.parseInt(riga[1]);
                Prodotto prodotto = ProdottoDAO.getInstance().getById(idProdotto);
                int quantita = Integer.parseInt(riga[7]);
                if(quantita == 0)
                    continue;
                listaProdotti.put(prodotto,quantita);
            }
            catch (NoIDMatchException e){
                throw e;
            }
        }
        
        return listaProdotti;
    }
    
    public Carrello getCarrello(Dipendente dipendente) throws NoIDMatchException{
        try{
            Carrello carrello = new Carrello(dipendente, getListaProdotti(dipendente.getId()));
            return carrello;
        }
        catch (NoIDMatchException e){
            throw e;
        }
    }

    @Override 
    public Carrello getById(int idDip) throws NoIDMatchException{
        try {
            Dipendente dipendente = DipendenteDAO.getInstance().getById(idDip);
            Carrello carrello = new Carrello(dipendente, getListaProdotti(idDip));
            return carrello;
        }
        catch (NoIDMatchException e){
            throw e;
        }
    }
    
    public void addProdotto(Carrello carrello, Prodotto prodotto, int quantita){
        DBConnection.getInstance().updateDB("INSERT INTO carrelli VALUES(" + carrello.getDipendente().getId() + "," + prodotto.getId() + "," + quantita + ")");
    }
    
    public void updateQuantita(Carrello carrello, Prodotto prodotto, int quantita){
        DBConnection.getInstance().updateDB("UPDATE carrelli SET quantita = " + quantita + " WHERE id_dipendente = " + carrello.getDipendente().getId() + " AND id_prodotto = " + prodotto.getId());
    }
    
    public void deleteProdotto(Carrello carrello, Prodotto prodotto){
        DBConnection.getInstance().updateDB("DELETE FROM carrelli WHERE id_dipendente = " + carrello.getDipendente().getId() + " AND id_prodotto = " + prodotto.getId());
    }
    
    //Solo debug, metodo pericoloso
    @Override public void create(Carrello carrello) {
        for(Map.Entry<Prodotto,Integer> val : carrello.getListaProdotti().entrySet()) {
            DBConnection.getInstance().updateDB("INSERT INTO carrelli VALUES(" + carrello.getDipendente().getId() + "," + val.getKey().getId() + "," + val.getValue() + ")");
        }
    }
    
    //Solo debug, metodo pericoloso
    @Override public void update(Carrello carrello) {
        for(Map.Entry<Prodotto,Integer> val : carrello.getListaProdotti().entrySet()) {
            //DBConnection.getInstance().updateDB("UPDATE carrelli SET id_dipendente = " + carrello.getDipendente().getID() + ", id_prodotto = " + val.getKey().getID() + ", quantita = " + val.getValue());
        }
    }
    @Override public void delete(IdentificabileID dipendente){
        if(dipendente instanceof Dipendente)
            DBConnection.getInstance().updateDB("DELETE FROM carrelli WHERE id_dipendente = " + dipendente.getId());
    }
    
    
}

