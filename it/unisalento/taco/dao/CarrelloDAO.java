/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
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

    //RESTITUISCE SOLO LA LISTA DEI PRODOTTI NEL CARRELLO DEL DIPENDENTE
    
    public Map<Prodotto,Integer> getListaProdotti(int idDip) throws NoIDMatchException{
        
        String uberQuery = "SELECT produttori.nome,prodotti.*,carrelli.quantita FROM prodotti,carrelli,produttori,dipendenti WHERE carrelli.id_prodotto = prodotti.id AND dipendenti.id_utente = " + idDip + " AND produttori.id_prodotto = prodotti.id";
        ArrayList<String[]> result = DBConnection.getInstance().queryDB(uberQuery);
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> listaProdotti = new LinkedHashMap<>();
        //Se il contenuto della query è nullo, lo è anche il carrello
        //Non si restituisce null ma un carrello vuoto.
        while(i.hasNext()) {
            String[] riga = i.next();
            try{
                int idProdotto = Integer.parseInt(riga[1]);
                Prodotto prodotto = ProdottoDAO.getInstance().getByID(idProdotto);
                int quantita = Integer.parseInt(riga[6]);
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
    
    //RESTITUISCE IL CARRELLO
    public Carrello getCarrello(Dipendente dipendente) throws NoIDMatchException{
        try{
            Carrello carrello = new Carrello(dipendente, getListaProdotti(dipendente.getID()));
            return carrello;
        }
        catch (NoIDMatchException e){
            throw e;
        }
    }
    
    //L'id che accetta come parametro è quello del dipendente,
    //infatti, per ogni dipendente c'è un solo ed unico carrello.
    //Non ha senso per questa funzione lanciare un'eccezione 
    //Perché se il dipendente esiste, esiste anche il suo carrello.
    @Override public Carrello getByID(int idDip) throws NoIDMatchException{
        try {
            Dipendente dipendente = DipendenteDAO.getInstance().getByID(idDip);
            Carrello carrello = new Carrello(dipendente, getListaProdotti(idDip));
            return carrello;
        }
        catch (NoIDMatchException e){
            throw e;
        }
    }
    
    public void addProdotto(Carrello carrello, Prodotto prodotto, int quantita){
        DBConnection.getInstance().updateDB("INSERT INTO carrelli VALUES(" + carrello.getDipendente().getID() + "," + prodotto.getID() + "," + quantita + ")");
    }
    
    public void updateQuantita(Carrello carrello, Prodotto prodotto, int quantita){
        DBConnection.getInstance().updateDB("UPDATE carrelli SET quantita = " + quantita + " WHERE id_dipendente = " + carrello.getDipendente().getID() + " AND id_prodotto = " + prodotto.getID());
    }
    
    public void deleteProdotto(Carrello carrello, Prodotto prodotto){
        DBConnection.getInstance().updateDB("DELETE FROM carrelli WHERE id_dipendente = " + carrello.getDipendente().getID() + " AND id_prodotto = " + prodotto.getID());
    }
    
    //QUESTA QUERY E' DA UTILIZZARE SOLO AI FINI DI TEST
    @Override public void create(Carrello carrello) {
        for(Map.Entry<Prodotto,Integer> val : carrello.getListaProdotti().entrySet()) {
            DBConnection.getInstance().updateDB("INSERT INTO carrelli VALUES(" + carrello.getDipendente().getID() + "," + val.getKey().getID() + "," + val.getValue() + ")");
        }
    }
    
    //NON USARE MAI QUESTO METODO
    @Override public void update(Carrello carrello) {
        for(Map.Entry<Prodotto,Integer> val : carrello.getListaProdotti().entrySet()) {
            //DBConnection.getInstance().updateDB("UPDATE carrelli SET id_dipendente = " + carrello.getDipendente().getID() + ", id_prodotto = " + val.getKey().getID() + ", quantita = " + val.getValue());
        }
    }
    
    //ELIMINA TUTTO IL CARRELLO
    @Override public void delete(IdentificabileID dipendente){
        if(dipendente instanceof Dipendente)
            DBConnection.getInstance().updateDB("DELETE FROM carrelli WHERE id_dipendente = " + dipendente.getID());
    }
    
    
}

