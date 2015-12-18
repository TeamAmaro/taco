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
        while(i.hasNext()) {
            String[] riga = i.next();
            try{
                Dipendente dip = DipendenteDAO.getInstance().getByID(Integer.parseInt(riga[1]));
                Progetto prog = ProgettoDAO.getInstance().getByID(Integer.parseInt(riga[3]));
                Magazzino mag = MagazzinoDAO.getInstance().getByID(Integer.parseInt(riga[4]));
                long data = Long.parseLong(riga[8]);
                Map<Prodotto,Integer> listaProdotti = getListaProdotti(Integer.parseInt(riga[1]));
                Ordine ordine = new Ordine(dip,prog,mag,data,listaProdotti);
                listaOrdini.add(ordine);
            } 
            catch(NoIDMatchException e) {
                throw e;
            }
        }
        return listaOrdini;
    }
    
    @Override public Ordine getByID(int hashCode) throws NoIDMatchException{
        
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE codice = " + hashCode);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            try{
                Dipendente dip = DipendenteDAO.getInstance().getByID(Integer.parseInt(riga[1]));
                Progetto prog = ProgettoDAO.getInstance().getByID(Integer.parseInt(riga[4]));
                Magazzino mag = MagazzinoDAO.getInstance().getByID(Integer.parseInt(riga[5]));
            
                long data = Long.parseLong(riga[8]);
                Map<Prodotto,Integer> listaProd = new LinkedHashMap<>();
                result = DBConnection.getInstance().queryDB("SELECT id_prodotto, quantita FROM ordini WHERE codice = " + hashCode);
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
    
    //SI SUPPONE CHE CI SIANO PRODOTTI NELL'ORDINE E CHE NON POSSA ESISTE UN ORDINE SENZA UNA LISTA DI PRODOTTI
    public Map<Prodotto,Integer> getListaProdotti(int hashCode) {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT produttori.nome,prodotti.*,quantita FROM ordini,prodotti,produttori WHERE id = produttori.id_prodotto AND id = ordini.id_prodotto AND codice = " + hashCode);
        Iterator<String[]> i = result.iterator();
        Map<Prodotto,Integer> listaProdotti = new LinkedHashMap<>();
        while(i.hasNext()){
            String[] riga = i.next();
            Prodotto prodotto = new Prodotto.Builder(Integer.parseInt(riga[1]), riga[2], Double.parseDouble(riga[5]), Produttore.parseProduttore(riga[0])).descrizione(riga[4]).categoria(Categoria.parseCategoria(riga[3])).build();
            int quantita = Integer.parseInt(riga[6]);
            listaProdotti.put(prodotto, quantita);
        }
        return listaProdotti;
    }
    
    @Override public void update(Ordine ordine){
        //Implementami pls
    }
    
    @Override public void create(Ordine ordine){
        for(Map.Entry<Prodotto,Integer> val : ordine.getListaProdotti().entrySet()) {
            DBConnection.getInstance().updateDB("INSERT INTO ordini(codice,id_dipendente,nome_sede,id_progetto,id_magazzino,id_prodotto,quantita,data) values(codice = " + ordine.hashCode() + ", id_dipendente = " + ordine.getDipendente().getID() + ", id_progetto = " + ordine.getProgetto().getID() + ", id_magazzino = " + ordine.getMagazzino().getID() + ", id_prodotto = " + val.getKey().getID() + ", quantita = " + val.getValue() + ", data = " + ordine.getData() + ")");
        }
    }
    
    //Questa funzione va chiamata passando come parametro Ordine.hashCode();
    //Ma perché mai dovremmo permettere di cancellare un ordine dal db?
    //Tecnicamente parlando, è una cosa che non dovrebbe fare neanche il superadmin 
    @Override public void delete(IdentificabileID obj){
        //DBConnection.getInstance().updateDB("DELETE FROM progetti WHERE codice = " + obj.getID());
    }
    
}
