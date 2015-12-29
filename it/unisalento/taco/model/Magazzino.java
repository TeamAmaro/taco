package it.unisalento.taco.model;

import it.unisalento.taco.dao.MagazzinoDAO;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import java.util.Map;
import java.util.Set;

public class Magazzino  implements IdentificabileID {
    private final int id;
    private final String nome;
    private final Sede sede;
    public static int COSTO_SPEDIZIONE = 5;
    private Map<Prodotto,Integer> inventario;

    public Magazzino(int id, String nome, Sede sede, Map<Prodotto,Integer> inventario){
        this.id = id;
        this.nome = nome;
        this.sede = sede;
        this.inventario = inventario;
    }
    
    public Magazzino(String nome, Sede sede){
        //ID FITTIZIO
        id = 0;
        this.nome = nome;
        this.sede = sede;
    }

    public String getNome(){
        return nome;
    }

    public Sede getSede(){
        return sede;
    }

    @Override public int getID(){
        return id;
    }
    
    public Map<Prodotto,Integer> getInventario(){
        return inventario;
    }
    
    public void setInventario(Map<Prodotto,Integer> inventario){
        this.inventario = inventario;
    }
    
    public void removeFromInventario(Map<Prodotto,Integer> listaProdotti){
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet()){
            removeProdotto(val.getKey(),val.getValue());
        }
    }

    public void addProdotto(Prodotto prodotto, int quantita){
        System.out.println("Hai aggiunto " + quantita + " " + prodotto.getNome() + " all'inventario");
        if(inventario.containsKey(prodotto)){
            inventario.put(prodotto, inventario.get(prodotto) + quantita);
            MagazzinoDAO.getInstance().updateQuantita(this, prodotto, inventario.get(prodotto) + quantita);
        }
        else {
            inventario.put(prodotto, quantita);
            MagazzinoDAO.getInstance().addProdotto(this, prodotto, quantita);
        }
    }

    public void removeProdotto(Prodotto prodotto, int quantita){
        if(inventario.containsKey(prodotto)){
            int prevValue = inventario.get(prodotto);
            if(prevValue - quantita <= 0){
                inventario.remove(prodotto);
                MagazzinoDAO.getInstance().deleteProdotto(this, prodotto);
            }
            else {
                inventario.put(prodotto, prevValue - quantita);
                MagazzinoDAO.getInstance().updateQuantita(this, prodotto, prevValue - quantita);
            }
            System.out.println("Hai rimosso " + quantita + " " + prodotto.getNome() + " dall'inventario");
        }
    }

    public int cercaProdotto(Prodotto prodotto, int quantita){
        for (Map.Entry<Prodotto, Integer> e : inventario.entrySet())
                if(e.getKey() == prodotto)
                        return e.getValue();
            //Se non trova corrispondenze, ovvero il prodotto non è contenuto nell'inventario
        return 0;
    }
    
    @Override public String toString(){
        StringBuilder stringMag = new StringBuilder();
        stringMag.append("ID : ").append(id).append(", Nome: ").append(nome).append(", Sede: ").append(sede);
        stringMag.append("\nInventario:\n");
        if(inventario.isEmpty()){
            stringMag.append("Non ci sono prodotti nell'inventario");
        }
        else
            for (Map.Entry<Prodotto, Integer> e : inventario.entrySet())
            stringMag.append(e.getKey().getNome() + " x " + e.getValue());
        return stringMag.toString();
    }
    
    public static Magazzino getMagazzino(Sede sede) throws NoQueryMatchException{
        return MagazzinoDAO.getInstance().getMagazzino(sede);
    }
    
    public static int getQuantita(Magazzino magazzino, Prodotto prodotto){
        return MagazzinoDAO.getInstance().getQuantitaProdotto(magazzino, prodotto);
    }
    
    public static Set<Magazzino> cercaProdotto(Prodotto prodotto){
        return MagazzinoDAO.getInstance().cercaProdotto(prodotto);
    }
    
    public void addNewToDB(Magazzino mag){
        MagazzinoDAO.getInstance().create(mag);
    }
    
}
