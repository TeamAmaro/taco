package it.unisalento.taco.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Magazzino {
	private final String nome;
	private final Sede sede;
	public static int COSTO_SPEDIZIONE = 5;
	private Map<Prodotto,Integer> inventario = new LinkedHashMap<>();
	
	public Magazzino(String nome, Sede sede){
		this.nome = nome;
		this.sede = sede;
	}
	
	public String getNome(){
		return nome;
	}
	
	public Sede getSede(){
		return sede;
	}
	
	public void resocontoInventario(){
		if(inventario.isEmpty()){
			System.out.println("Non ci sono prodotti nell'inventario");
			return;
		}
		System.out.println("Il contenuto dell'inventario è");
		for (Map.Entry<Prodotto, Integer> e : inventario.entrySet())
		    System.out.println(e.getKey().getNome() + " x " + e.getValue());
	}
	
	public void aggiungiProdotto(Prodotto prodotto, int quantita){
		System.out.println("Hai aggiunto " + quantita + " " + prodotto.getNome() + " all'inventario");
		if(inventario.containsKey(prodotto))
			inventario.put(prodotto, inventario.get(prodotto) + quantita);
		else
			inventario.put(prodotto, quantita);
	}
	
	public void rimuoviProdotto(Prodotto prodotto, int quantita){
		System.out.println("Hai rimosso " + quantita + " " + prodotto.getNome() + " dall'inventario");
		if(inventario.containsKey(prodotto)){
			int prevValue = inventario.get(prodotto);
			if(prevValue - quantita <= 0)
				inventario.remove(prodotto);
			else
				inventario.put(prodotto, prevValue - quantita);
		}		
	}
}
