package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class OrdineDAO {
    private static OrdineDAO instance;
    public static OrdineDAO getInstance(){
        if(instance == null)
            instance = new OrdineDAO();
        return instance;
    }
    private OrdineDAO(){}
    
    public Set<Ordine> getListaOrdini(Progetto progetto){
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE id_progetto = " + progetto.getID());
        Iterator<String[]> i = result.iterator();
        while(i.hasNext()) {
            String[] riga = i.next();
            Dipendente dip = DipendenteDAO.getInstance().getDipendente(Integer.parseInt(riga[1]));
            Progetto prog = ProgettoDAO.getInstance().getProgetto(Integer.parseInt(riga[3]));
            Magazzino mag = MagazzinoDAO.getInstance().getMagazzino(Integer.parseInt(riga[4]));
            long data = Long.parseLong(riga[8]);
            Map<Prodotto,Integer> listaProdotti = getListaProdotti(Integer.parseInt(riga[1]));
            Ordine ordine = new Ordine(dip,prog,mag,data,listaProdotti);
            listaOrdini.add(ordine);
        }
        return listaOrdini;
    }
    
    public Ordine getOrdine(int hashCode){
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE codice = " + hashCode);
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            Dipendente dip = DipendenteDAO.getInstance().getDipendente(Integer.parseInt(riga[1]));
            Progetto prog = ProgettoDAO.getInstance().getProgetto(Integer.parseInt(riga[4]));
            Magazzino mag = MagazzinoDAO.getInstance().getMagazzino(Integer.parseInt(riga[5]));
            long data = Long.parseLong(riga[8]);
            Map<Prodotto,Integer> listaProd = new LinkedHashMap<>();
            result = DBConnection.getInstance().queryDB("SELECT id_prodotto, quantita FROM ordini WHERE codice = " + hashCode);
            i = result.iterator();
            if(i.hasNext()){
                riga = i.next();
                Prodotto prod = ProdottoDAO.getInstance().getProdotto(Integer.parseInt(riga[0]));
                int quantProd = Integer.parseInt(riga[1]);
                listaProd.put(prod, quantProd);
            }
            Ordine ordine = new Ordine(dip, prog, mag, data, listaProd);
            return ordine;
        }

        return null;
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
    
}
