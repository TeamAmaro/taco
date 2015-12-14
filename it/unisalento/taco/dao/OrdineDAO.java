package it.unisalento.taco.dao;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Progetto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
}
