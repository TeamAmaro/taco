package it.unisalento.taco.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Carrello {

    private final Dipendente dipendente;
    private double prezzoTotale = 0;
    private Map<Prodotto,Integer> listaProdotti = new LinkedHashMap<>();

    public Carrello(Dipendente dipendente, Map<Prodotto,Integer> listaProdotti){
        this.dipendente = dipendente;
        this.listaProdotti = listaProdotti;
    }
    
    public Dipendente getDipendente(){
        return dipendente;
    }
    
    public Map<Prodotto, Integer> getListaProdotti(){
        return listaProdotti;
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita){
        System.out.println("Hai acquistato " + quantita + " " + prodotto.getNome());
        if(listaProdotti.containsKey(prodotto))
            listaProdotti.put(prodotto, listaProdotti.get(prodotto) + quantita);
        else
            listaProdotti.put(prodotto, quantita);
    }

    public void rimuoviProdotto(Prodotto prodotto, int quantita){
        System.out.println("Hai rimosso " + quantita + " " + prodotto.getNome());
        if(listaProdotti.containsKey(prodotto)){
            int prevValue = listaProdotti.get(prodotto);
            if(prevValue - quantita <= 0)
                listaProdotti.remove(prodotto);
            else
                listaProdotti.put(prodotto, prevValue - quantita);
        }		
    }

    public void stampaListaProdotti(){
        if(listaProdotti.isEmpty()){
            System.out.println("Il carrello è vuoto");
            return;
        }
        System.out.println("Il contenuto del carrello è");
        for (Map.Entry<Prodotto, Integer> e : listaProdotti.entrySet())
            System.out.println(e.getKey().getNome() + " x " + e.getValue());
    }

    public void calcolaTotale(){
        System.out.println("Calcolo prezzo totale");
        for(Map.Entry<Prodotto, Integer> e : listaProdotti.entrySet()){
            prezzoTotale += e.getKey().getPrezzo() * e.getValue();
        }

        System.out.println("\nIl prezzo è " + prezzoTotale);
    }
    
    @Override public String toString(){
        StringBuilder stringCarrello = new StringBuilder();
        if(listaProdotti.isEmpty()){
            stringCarrello.append("Il carrello è vuoto");
            return stringCarrello.toString();
        }
        stringCarrello.append("Il contenuto del carrello è : \n");
        for (Map.Entry<Prodotto, Integer> e : listaProdotti.entrySet())
            stringCarrello.append(e.getKey().getNome()).append(" x ").append(e.getValue()).append("\n");
        return stringCarrello.toString();
    }
}
