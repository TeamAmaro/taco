package it.unisalento.taco.model;

import it.unisalento.taco.dao.MagazzinoDAO;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoMagazzinoException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import java.util.Map;
import java.util.Set;

public class Magazzino  implements IdentificabileID {

    
   
    private final int id;
    private final String nome;
    private final Sede sede;
    private Map<Prodotto,Integer> inventario;
    private Magazziniere magazziniere;

    public Magazzino(int id, String nome, Sede sede, Map<Prodotto,Integer> inventario, Magazziniere magazziniere){
        this.id = id;
        this.nome = nome;
        this.sede = sede;
        this.inventario = inventario;
        this.magazziniere = magazziniere;
    }
    
    public Magazzino(int id, String nome, Sede sede, Map<Prodotto,Integer> inventario) {
        this.id = id;
        this.nome = nome;
        this.sede = sede;
        this.inventario = inventario;
    }

    public String getNome(){
        return nome;
    }

    public Sede getSede(){
        return sede;
    }

    @Override public int getId(){
        return id;
    }
    
    public Map<Prodotto,Integer> getInventario(){
        return inventario;
    }
    
    public void setInventario(Map<Prodotto,Integer> inventario){
        this.inventario = inventario;
    }
    
    public Magazziniere getMagazziniere(){
        return magazziniere;
    }
    
    public void setMagazziniere(Magazziniere magazziniere){
        this.magazziniere = magazziniere;
    }
    
    public void removeFromInventario(Map<Prodotto,Integer> listaProdotti){
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet()){
            removeProdotto(val.getKey(),val.getValue());
        }
    }

    public void addProdotto(Prodotto prodotto, int quantita){
        if(quantita < 0){
            return;
        }
        if(inventario.containsKey(prodotto)){
            inventario.put(prodotto, inventario.get(prodotto) + quantita);
            MagazzinoDAO.getInstance().updateQuantita(this, prodotto, inventario.get(prodotto));
        }
        else {
            inventario.put(prodotto, quantita);
            MagazzinoDAO.getInstance().updateQuantita(this, prodotto, quantita);
        }
    }

    public void removeProdotto(Prodotto prodotto, int quantita){
        if(inventario.containsKey(prodotto)){
            int prevValue = inventario.get(prodotto);
            if(prevValue - quantita <= 0){
                inventario.put(prodotto, 0);
                MagazzinoDAO.getInstance().updateQuantita(this, prodotto, 0);
            }
            else {
                inventario.put(prodotto, prevValue - quantita);
                MagazzinoDAO.getInstance().updateQuantita(this, prodotto, prevValue - quantita);
            }
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
    
    public static Magazzino getMagazzino(Sede sede) throws NoQueryMatchException, NoIDMatchException{
        return MagazzinoDAO.getInstance().getMagazzino(sede);
    }
    
    public static Magazzino getMagazzino(Magazziniere magazziniere) throws NoMagazzinoException{
        return MagazzinoDAO.getInstance().getMagazzino(magazziniere);
    }
    
    public static int getQuantita(Magazzino magazzino, Prodotto prodotto){
        return MagazzinoDAO.getInstance().getQuantitaProdotto(magazzino, prodotto);
    }
    
    public static int getQuantitaAll(Prodotto prodotto) {
        return MagazzinoDAO.getInstance().getQuantitaAll(prodotto);
    }
    
    public static Set<Magazzino> cercaProdotto(Prodotto prodotto) throws NoIDMatchException{
        return MagazzinoDAO.getInstance().cercaProdotto(prodotto);
    }
    
    public void create(Magazzino mag){
        MagazzinoDAO.getInstance().create(mag);
    }
    
    @Override public boolean equals(Object obj){

        if(obj == null)
            return false;
        else if(getClass() != obj.getClass())
            return false;

        final Magazzino other = (Magazzino) obj;
        return other.id == id;
    }

    @Override public int hashCode() {
        return id;
    }
    
}
