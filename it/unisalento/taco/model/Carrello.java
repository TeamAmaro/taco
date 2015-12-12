package it.unisalento.taco.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Carrello {

	private double prezzoTotale = 0;
	private Map<Prodotto,Integer> listaProdotti = new LinkedHashMap<>();
        
        public Carrello(Map<Prodotto,Integer> listaProdotti){
            this.listaProdotti = listaProdotti;
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
		for (Map.Entry<Prodotto, Integer> e : listaProdotti.entrySet()){
			prezzoTotale += e.getKey().getPrezzo() * e.getValue();
			System.out.print(".");
		}
		
		System.out.println("\nIl prezzo � " + prezzoTotale);
	}
	static class GeneratoreOrdini{
		
		private List<Ordine> listaOrdini = new ArrayList<>();
	
		public void generaOrdini(){

			
			/*
			 * Controlla elenco prodotti;  mappaProdotti
			 * Controlla dipendente;	Client.DipendenteIstance()
			 * Controlla progetto;	Client.DipendenteIstance().getProgetto();
			 * Controlla i magazzini per i prodotti;	MagazzinoDelegate.cercaProdotto(prodotto) (foreach prodotto)
			 * Crea una lista di ordini, raggruppando i prodotti per magazzino; Dividi la mappa in elenco di magazzini e prodotti
			 * Aggiungi eventuali spese di spedizione;	calcolaTotale per ordine
			 * Crea un file da stampare con la distinta;   documentoDAO stampa ricevuta
			 * Associa gli ordini al progetto
			 */
			
			
		}
	}
}
