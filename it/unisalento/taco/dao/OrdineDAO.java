package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class OrdineDAO implements DAOInterface<Ordine>{
    private static OrdineDAO instance;
    
    public static OrdineDAO getInstance(){
        if(instance == null)
            instance = new OrdineDAO();
        return instance;
    }
    private OrdineDAO(){}
    
    public Set<Ordine> getListaOrdini(Progetto progetto) throws NoIDMatchException{
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE id_progetto = " + progetto.getID());
        Iterator<String[]> i = result.iterator();
        int codice = 0;
        while(i.hasNext()) {
            String[] riga = i.next();
            try{
                Dipendente dip = DipendenteDAO.getInstance().getByID(Integer.parseInt(riga[1]));
                Progetto prog = ProgettoDAO.getInstance().getByID(Integer.parseInt(riga[3]));
                Magazzino mag = MagazzinoDAO.getInstance().getByID(Integer.parseInt(riga[4]));
                long data = Long.parseLong(riga[6]);
                Map<Prodotto,Integer> listaProdotti = getListaProdotti(Integer.parseInt(riga[0]));
                Ordine ordine = new Ordine(dip,prog,mag,data,listaProdotti);
                listaOrdini.add(ordine);
            } 
            catch(NoIDMatchException e) {
                throw e;
            }
        }
        return listaOrdini;
    }
    
    public Set<Ordine> getListaOrdini(Progetto progetto, int offset) throws NoIDMatchException{
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE id_progetto = " + progetto.getID() + " LIMIT 10 OFFSET " + offset);
        Iterator<String[]> i = result.iterator();
        while(i.hasNext()) {
            String[] riga = i.next();
            try{
                Dipendente dip = DipendenteDAO.getInstance().getByID(Integer.parseInt(riga[1]));
                Progetto prog = ProgettoDAO.getInstance().getByID(Integer.parseInt(riga[3]));
                Magazzino mag = MagazzinoDAO.getInstance().getByID(Integer.parseInt(riga[4]));
                long data = Long.parseLong(riga[6]);
                Map<Prodotto,Integer> listaProdotti = getListaProdotti(Integer.parseInt(riga[0]));
                Ordine ordine = new Ordine(dip,prog,mag,data,listaProdotti);
                listaOrdini.add(ordine);
            } 
            catch(NoIDMatchException e) {
                throw e;
            }
        }
        return listaOrdini;
    }
    
    public Set<Ordine> getListaOrdini(Magazzino magazzino) throws NoIDMatchException{
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE id_magazzino = " + magazzino.getID() + " AND spedito = 0");
        Iterator<String[]> i = result.iterator();
        int codice = 0;
        while(i.hasNext()) {
            String[] riga = i.next();
            try{
                Dipendente dip = DipendenteDAO.getInstance().getByID(Integer.parseInt(riga[1]));
                Progetto prog = ProgettoDAO.getInstance().getByID(Integer.parseInt(riga[3]));
                Magazzino mag = MagazzinoDAO.getInstance().getByID(Integer.parseInt(riga[4]));
                long data = Long.parseLong(riga[6]);
                Map<Prodotto,Integer> listaProdotti = getListaProdotti(Integer.parseInt(riga[0]));
                Ordine ordine = new Ordine(dip,prog,mag,data,listaProdotti);
                listaOrdini.add(ordine);
            } 
            catch(NoIDMatchException e) {
                throw e;
            }
        }
        return listaOrdini;
    }
    
    public int getNumeroOrdini(Progetto progetto) throws NoIDMatchException{
        //select count(*) from ordini where id_progetto = 1;
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT COUNT(*) FROM ordini WHERE id_progetto = " + progetto.getID());
        Iterator<String[]> i = result.iterator();
        String[] riga = i.next();
        return Integer.parseInt(riga[0]);
    }
    
    @Override
    public Ordine getByID(int hashCode) throws NoIDMatchException{
        
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE codice = " + hashCode);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            try{
                Dipendente dip = DipendenteDAO.getInstance().getByID(Integer.parseInt(riga[1]));
                Progetto prog = ProgettoDAO.getInstance().getByID(Integer.parseInt(riga[3]));
                Magazzino mag = MagazzinoDAO.getInstance().getByID(Integer.parseInt(riga[4]));
                long data = Long.parseLong(riga[6]);
                Map<Prodotto,Integer> listaProd = new LinkedHashMap<>();
                result = DBConnection.getInstance().queryDB("SELECT id_prodotto, quantita FROM dettagliordini WHERE codice = " + hashCode);
                i = result.iterator();
                while(i.hasNext()){
                    riga = i.next();
                    Prodotto prod = ProdottoDAO.getInstance().getByID(Integer.parseInt(riga[0]));
                    int quantProd = Integer.parseInt(riga[1]);
                    listaProd.put(prod, quantProd);
                }
                Ordine ordine = new Ordine(dip, prog, mag, data, listaProd);
                return ordine;
            }
            catch(NoIDMatchException e) {
                throw e;
            }
        }
        else {
            throw new NoIDMatchException(this);
        }
    }
    
    public Map<Prodotto,Integer> getListaProdotti(int hashCode) {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT pr.nome,p.*,d.quantita FROM prodotti p JOIN produttori pr ON p.id = pr.id_prodotto JOIN dettagliordini d ON d.id_prodotto = p.id WHERE codice = " + hashCode);
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> listaProdotti = new LinkedHashMap<>();
        while(i.hasNext()){
            String[] riga = i.next();
            Prodotto prodotto = new Prodotto.Builder(Integer.parseInt(riga[1]), riga[2], Double.parseDouble(riga[5]), Produttore.parseProduttore(riga[0])).descrizione(riga[4]).categoria(Categoria.parseCategoria(riga[3])).build();
            int quantita = Integer.parseInt(riga[7]);
            listaProdotti.put(prodotto, quantita);
        }
        return listaProdotti;
    }
    
    @Override public void update(Ordine ordine){
        DBConnection.getInstance().updateDB("UPDATE ordini SET spedito = 1 WHERE codice = " + ordine.getCodice());
    }

    @Override public void create(Ordine ordine){
        int hashCode = ordine.hashCode();
        DBConnection.getInstance().updateDB("INSERT INTO ordini VALUES(" + hashCode + ", " + ordine.getDipendente().getID() + ", '" + ordine.getDipendente().getSede() + "', " + ordine.getProgetto().getID() + ", " + ordine.getMagazzino().getID() + ", " + 0 + ", " + ordine.getData() + ")");
        for(Map.Entry<Prodotto,Integer> val : ordine.getListaProdotti().entrySet()) {
            DBConnection.getInstance().updateDB("INSERT INTO dettagliordini VALUES(" + hashCode + ", " + val.getKey().getID() + ", " + val.getValue() + ")");
        }
    }
    
    //Solo debug, metodo pericoloso
    @Override public void delete(IdentificabileID obj){
        DBConnection.getInstance().updateDB("DELETE FROM progetti WHERE codice = " + obj.getID());
    }
    
}
