package it.unisalento.taco.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Ordine {

	private final Dipendente dipendente;
	private final Progetto progetto;
	private final Magazzino magazzino;
	private final Set<Prodotto> listaProdotti;
	private final Date data;
	private boolean spedito;
	
	public Ordine(Dipendente dipendente, Progetto progetto, Magazzino magazzino, Set<Prodotto> listaProdotti, Date data) {
		this.dipendente = dipendente;
                this.progetto = progetto;
                this.magazzino = magazzino;
                this.listaProdotti = listaProdotti;
                this.data = data;
	}
	
	//public void stampaDistinta();
	private String getListaProdottiAsString(){
		StringBuilder stringProdotti = new StringBuilder();
		for(Prodotto val : listaProdotti)
			stringProdotti.append(val.toString()).append(", ");
                int last = stringProdotti.lastIndexOf(",");
                stringProdotti.delete(last, last + 1);
		return stringProdotti.toString();
	
        }
        
        @Override public String toString(){
            StringBuilder ordineString = new StringBuilder();
            ordineString.append("Dipendente: ").append(dipendente).append(", Progetto: ").append(progetto).
                    append(", Magazzino: ").append(magazzino).append(", Lista Prodotti: ").append(getListaProdottiAsString()).
                    append(", Eseguito in data: ").append(data);
            return ordineString.toString();
        }
	
}
