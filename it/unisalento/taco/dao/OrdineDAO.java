package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;
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
        //Inizializzo la lista degli ordini, se non ci sono corrispondenze restituisce una lista vuota
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        //DA IMPLEMENTARE
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM ordini WHERE id_progetto = " + progetto.getID());
        Iterator<String[]> i = result.iterator();
        while(i.hasNext()) {
            String[] riga = i.next();
            int idDipendente = Integer.parseInt(riga[1]);
            //DA IMPLEMENTARE
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
    
}
