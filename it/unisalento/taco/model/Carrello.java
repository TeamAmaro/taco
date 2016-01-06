package it.unisalento.taco.model;

import it.unisalento.taco.dao.CarrelloDAO;
import it.unisalento.taco.exceptions.NoIDMatchException;
import java.util.Map;

public class Carrello implements IdentificabileID{

    private double totale;
    private Map<Prodotto,Integer> listaProdotti;
    
    private final Dipendente dipendente;

    public Carrello(Dipendente dipendente, Map<Prodotto,Integer> listaProdotti){
        this.listaProdotti = listaProdotti;
        this.dipendente = dipendente;
        this.totale = calcolaTotale();
    }
    
    @Override
    public int getID(){
        return dipendente.getID();
    }

    public Dipendente getDipendente(){
        return dipendente;
    }
    
    public void setListaProdotti(Map<Prodotto,Integer> listaProdotti){
        this.listaProdotti = listaProdotti;
    }
    
    public void setTotale(){
        this.totale = calcolaTotale();
    }
    
    public double getTotale(){
        return totale;
    }
    
    public void removeListaProdotti(Map<Prodotto,Integer> listaProdotti){
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet()){
            removeProdotto(val.getKey(),val.getValue());
        }
        CarrelloDAO.getInstance().delete(this.getDipendente());
    }
    
    public Map<Prodotto, Integer> getListaProdotti(){
        return listaProdotti;
    }

    public void addProdotto(Prodotto prodotto, int quantita){
        if(listaProdotti.containsKey(prodotto)) {
            listaProdotti.put(prodotto, listaProdotti.get(prodotto) + quantita);
            CarrelloDAO.getInstance().updateQuantita(this, prodotto, listaProdotti.get(prodotto) + quantita);
        }
        else {
            listaProdotti.put(prodotto, quantita);
            CarrelloDAO.getInstance().addProdotto(this, prodotto, quantita);
        }
        setTotale();
    }

    public void removeProdotto(Prodotto prodotto, int quantita){
        if(listaProdotti.containsKey(prodotto)){
            int prevValue = listaProdotti.get(prodotto);
            if(prevValue - quantita <= 0){
                listaProdotti.remove(prodotto);
                CarrelloDAO.getInstance().deleteProdotto(this, prodotto);
            }
            else {
                listaProdotti.put(prodotto, prevValue - quantita);
                CarrelloDAO.getInstance().updateQuantita(this, prodotto, (prevValue - quantita));
            }
        }
        setTotale();
    }
    
    public void removeProdotto(Prodotto prodotto){
        if(listaProdotti.containsKey(prodotto)){
                listaProdotti.remove(prodotto);
                CarrelloDAO.getInstance().deleteProdotto(this, prodotto);
        }
        setTotale();
    }

    public double calcolaTotale(){
        totale = 0;
        for(Map.Entry<Prodotto, Integer> e : listaProdotti.entrySet()){
            totale += e.getKey().getPrezzo() * e.getValue();
        }
        return totale;
    }
    
    public static Carrello getByID(int id) throws NoIDMatchException{
        return CarrelloDAO.getInstance().getByID(id);
    }
    
    public static Carrello getCarrello(Dipendente dipendente) throws NoIDMatchException{
        return CarrelloDAO.getInstance().getCarrello(dipendente);
    }
    
    
    @Override public String toString(){
        StringBuilder stringCarrello = new StringBuilder();
        if(listaProdotti.isEmpty()){
            stringCarrello.append("Il carrello è vuoto");
            return stringCarrello.toString();
        }
        stringCarrello.append("Lista Prodotti: \n");
        for (Map.Entry<Prodotto, Integer> e : listaProdotti.entrySet())
            stringCarrello.append(e.getKey().getNome()).append(" x ").append(e.getValue()).append("\n");
        
        stringCarrello.append("Totale : " + totale);
        return stringCarrello.toString();
    }

    public int numeroProdotti() {
        int n = 0;
        for(Prodotto p : listaProdotti.keySet())
            n++;
        return n;
    }
}