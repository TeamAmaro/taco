package it.unisalento.taco.model;

import it.unisalento.taco.dao.OrdineDAO;
import java.util.Date;
import java.util.Map;

public class Ordine {

    private final Dipendente dipendente;
    private final Progetto progetto;
    private final Magazzino magazzino;
    private final Map<Prodotto,Integer> listaProdotti;
    private final long data;
    private boolean spedito;
    private final double totale;
    private final double spesaSpedizione;

    public Ordine(Dipendente dipendente, Progetto progetto, Magazzino magazzino, Long data, Map<Prodotto,Integer> listaProdotti, double totale, double spesaSpedizione){
        this.dipendente = dipendente;
        this.progetto = progetto;
        this.magazzino = magazzino;
        this.data = data;
        this.listaProdotti = listaProdotti;
        this.totale = totale;
        this.spesaSpedizione = spesaSpedizione;
    }
    
    public Ordine(Dipendente dipendente, Progetto progetto, Magazzino magazzino, long data, Map<Prodotto,Integer> listaProdotti) {
        this.dipendente = dipendente;
        this.progetto = progetto;
        this.magazzino = magazzino;
        this.data = data;
        this.listaProdotti = listaProdotti;
        spedito = false;
        totale = calcolaTotale();
        spesaSpedizione = calcolaSpesaSpedizione();
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
    
    public double getTotale(){
        return totale;
    }
    
    public double getSpesaSpedizione(){
        return spesaSpedizione;
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
        hash = 31 * hash + (int) (data ^ (data >>> 32));
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet()){
            hash = 31 * hash + val.getKey().getID();
            hash = 31 * hash + val.getValue();
        }
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
    
    private double calcolaTotale(){
        double totale = 0;
        //Preleva le informazioni dell'ordine
        //Scorri i prodotti e calcola il totale
        for(Map.Entry<Prodotto, Integer> val : listaProdotti.entrySet()){
            double prezzo = val.getKey().getPrezzo();
            int quantita = val.getValue();
            totale += prezzo * quantita;
        }
        
        return totale;
    }
    
    private double calcolaSpesaSpedizione(){
        int spesaSpedizione = 0;
        
        //Calcolo le spese di spedizione
        //Controllo la sede del magazzino e quella del dipendente
        if(magazzino.getSede() == dipendente.getSede()){
            //Se sono uguali, la spesa di spedizione è una tantum (1€)
            spesaSpedizione = 1;
        }
        else{
            int quantitaProdotti = 0;

            for(Integer val : listaProdotti.values())
                quantitaProdotti += val;

            //Se il magazzino è esterno, la spesa di spedizione è (2€) 
            //più una percentuale su quanti prodotti sono spediti

            spesaSpedizione = 2;
            spesaSpedizione += (quantitaProdotti / 10);

            //La spesa di spedizione raggiunge un massimo di 10€ oltre un certo
            //numero di articoli ordinati

            if(spesaSpedizione > 10)
                spesaSpedizione = 10;
        }
        return spesaSpedizione;
    }
    
    public static void addOrdine(Ordine ordine){
        OrdineDAO.getInstance().create(ordine);
    }
}
