package it.unisalento.taco.model;

import it.unisalento.taco.dao.OrdineDAO;
import it.unisalento.taco.exceptions.NoIDMatchException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class Ordine {

    private final Dipendente dipendente;
    private final Progetto progetto;
    private final Magazzino magazzino;
    private final Map<Prodotto,Integer> listaProdotti;
    private final long data;
    private boolean spedito;
    private double totale;
    private double spesaSpedizione;

    private final int codice;
    
    public Ordine(Dipendente dipendente, Progetto progetto, Magazzino magazzino, Long data, Map<Prodotto,Integer> listaProdotti, double totale, double spesaSpedizione){
        this.dipendente = dipendente;
        this.progetto = progetto;
        this.magazzino = magazzino;
        this.data = data;
        this.listaProdotti = listaProdotti;
        this.totale = totale;
        this.spesaSpedizione = spesaSpedizione;
        codice = hashCode();
    }
    
    public Ordine(Dipendente dipendente, Progetto progetto, Magazzino magazzino, long data, Map<Prodotto,Integer> listaProdotti) {
        this.dipendente = dipendente;
        this.progetto = progetto;
        this.magazzino = magazzino;
        this.data = data;
        this.listaProdotti = listaProdotti;
        spedito = false;
        calcolaTotale();
        calcolaSpesaSpedizione();
        codice = hashCode();
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
    
    public int getCodice(){
        return codice;
    }
            
    public Date getReadableData(){
        return new Date(data);
    }

    public void setSpedito(boolean spedito){
        this.spedito = spedito;
        OrdineDAO.getInstance().update(this);
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

    public String getFormatTotale(){
        NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
        formatoEuro.setMinimumFractionDigits( 2 );
        formatoEuro.setMaximumFractionDigits( 2 );
        formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
        return formatoEuro.format(totale);
    }
    
    public String getFormatSpesaSpedizione(){
        NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
        formatoEuro.setMinimumFractionDigits( 2 );
        formatoEuro.setMaximumFractionDigits( 2 );
        formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
        return formatoEuro.format(spesaSpedizione);
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
    
    public String getListaQuantitaProdotti(){
        StringBuilder stringProdotti = new StringBuilder();
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet())
                stringProdotti.append(val.getValue()).append("\n");
        return stringProdotti.toString();
    }
    
    public String getListaPrezziProdotti(){
        StringBuilder stringProdotti = new StringBuilder();
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet())
                stringProdotti.append(val.getKey().getPrezzo()).append("€").append("\n");
        return stringProdotti.toString();
    }
    
    public String getListaNomiProdotti(){
        StringBuilder stringProdotti = new StringBuilder();
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet())
                stringProdotti.append(val.getKey().getNome()).append("\n");
        return stringProdotti.toString();
    }
    
    private String getListaProdottiAsString(){
        StringBuilder stringProdotti = new StringBuilder();
        for(Map.Entry<Prodotto,Integer> val : listaProdotti.entrySet())
                stringProdotti.append(val.getKey().getNome()).append(" x ").append(val.getValue()).append("\n");
        return stringProdotti.toString();
    }
        
    @Override public String toString(){
        StringBuilder ordineString = new StringBuilder();
        ordineString.append("Dipendente: ").append(dipendente).append(", Progetto: ").append(progetto.getNome()).
                append(", Magazzino: ").append(magazzino.getNome()).append(", \nLista Prodotti: ").append(getListaProdottiAsString()).
                append("Data: ").append(getReadableData());
        return ordineString.toString();
    }   
    
    private void calcolaTotale(){
        totale = 0;
        //Preleva le informazioni dell'ordine
        //Scorri i prodotti e calcola il totale
        for(Map.Entry<Prodotto, Integer> val : listaProdotti.entrySet()){
            double prezzo = val.getKey().getPrezzo();
            int quantita = val.getValue();
            totale += prezzo * quantita;
        }
    }
    
    private void calcolaSpesaSpedizione(){
        spesaSpedizione = 0;
        
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
    }
    
    public static void addOrdine(Ordine ordine){
        OrdineDAO.getInstance().create(ordine);
    }
    
    public static Set<Ordine> getListaOrdini(Progetto progetto) throws NoIDMatchException{
        return OrdineDAO.getInstance().getListaOrdini(progetto);
    }
    
    public static Set<Ordine> getListaOrdini(Magazzino magazzino) throws NoIDMatchException{
        return OrdineDAO.getInstance().getListaOrdini(magazzino);
    }
}
