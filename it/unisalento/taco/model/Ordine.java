package it.unisalento.taco.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Ordine {


    private final Dipendente dipendente;
    private final Progetto progetto;
    private final Magazzino magazzino;
    private final Map<Prodotto,Integer> listaProdotti;
    private final long data;
    private boolean spedito;

    public Ordine(Dipendente dipendente, Progetto progetto, Magazzino magazzino, long data, Map<Prodotto,Integer> listaProdotti) {
            this.dipendente = dipendente;
            this.progetto = progetto;
            this.magazzino = magazzino;
            this.data = data;
            this.listaProdotti = listaProdotti;
            spedito = false;
    }

    public Dipendente getDipendente(){
        return dipendente;
    }

    public Progetto getProgetto(){
        return progetto;
    }

    public Magazzino getMagazzino(){
        return magazzino;
    }

    public Map<Prodotto,Integer> getListaProdotti(){
        return listaProdotti;
    }

    public long getData(){
        return data;
    }
    
    public Date getReadableData(){
        return new Date(data);
    }

    public void spedito(boolean bool){
        spedito = bool;
    }

    public boolean isSpedito(){
        return spedito;
    }

    @Override public boolean equals(Object obj){

        if(obj == null)
            return false;
        else if(getClass() != obj.getClass())
            return false;

        final Ordine other = (Ordine) obj;
        return other.data == data && other.dipendente == dipendente &&
                other.progetto == progetto & other.listaProdotti == listaProdotti &&
                other.magazzino == magazzino;
    }

    @Override public int hashCode() {
        int hash = 1;
        hash = 31 * hash + dipendente.getID();
        hash = 31 * hash + progetto.getID();
        hash = 31 * hash + magazzino.getID();
        hash = 31 * hash + (int) (data^(data >>> 32));
        hash = 31 * hash + listaProdotti.hashCode();
        return hash;
    }
   
    private String getListaProdottiAsString(){
        StringBuilder stringProdotti = new StringBuilder();
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet())
                stringProdotti.append(val.getKey().getNome()).append(" x ").append(val.getValue()).append("\n");
        int last = stringProdotti.lastIndexOf(",");
        if(last != -1)
            stringProdotti.delete(last, last + 2);
        return stringProdotti.toString();
    }
        
    @Override public String toString(){
        StringBuilder ordineString = new StringBuilder();
        ordineString.append("Dipendente: ").append(dipendente).append(", Progetto: ").append(progetto.getNome()).
                append(", Magazzino: ").append(magazzino.getNome()).append(", \nLista Prodotti: ").append(getListaProdottiAsString()).
                append("Data: ").append(data);
        return ordineString.toString();
    }   
}