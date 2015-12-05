package it.unisalento.taco.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Carrello {

	private double prezzoTotale = 0;
	private Map<Prodotto,Integer> mappaProdotti = new LinkedHashMap<>();
	
	private static Carrello instance;
	public static Carrello getInstance(){
		if(instance == null)
			instance = new Carrello();
		return instance;
	}
	private Carrello(){};
	
	public void aggiungiProdotto(Prodotto prodotto, int quantita){
		System.out.println("Hai acquistato " + quantita + " " + prodotto.getNome());
		if(mappaProdotti.containsKey(prodotto))
			mappaProdotti.put(prodotto, mappaProdotti.get(prodotto) + quantita);
		else
			mappaProdotti.put(prodotto, quantita);
	}
	
	public void rimuoviProdotto(Prodotto prodotto, int quantita){
		System.out.println("Hai rimosso " + quantita + " " + prodotto.getNome());
		if(mappaProdotti.containsKey(prodotto)){
			int prevValue = mappaProdotti.get(prodotto);
			if(prevValue - quantita <= 0)
				mappaProdotti.remove(prodotto);
			else
				mappaProdotti.put(prodotto, prevValue - quantita);
		}		
	}
	
	public void stampaListaProdotti(){
		if(mappaProdotti.isEmpty()){
			System.out.println("Il carrello � vuoto");
			return;
		}
		System.out.println("Il contenuto del carrello �");
		for (Map.Entry<Prodotto, Integer> e : mappaProdotti.entrySet())
		    System.out.println(e.getKey().getNome() + " x " + e.getValue());
	}
	
	public void calcolaTotale(){
		System.out.println("Calcolo prezzo totale");
		for (Map.Entry<Prodotto, Integer> e : mappaProdotti.entrySet()){
			prezzoTotale += e.getKey().getPrezzo() * e.getValue();
			System.out.print(".");
		}
		
		System.out.println("\nIl prezzo � " + prezzoTotale);
	}
	
	public static class GeneratoreOrdini{
		
	}
}
