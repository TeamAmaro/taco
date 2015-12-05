package it.unisalento.taco.model;

import java.util.ArrayList;
import java.util.List;

public class Prodotto {
	
	private final String nome;
	private final double prezzo;
	private final Produttore produttore;
	private String descrizione;
	private Categoria categoria;
	private List<Fornitore> listaFornitori = new ArrayList<Fornitore>();
	
	public String getNome() {
		return nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public double getPrezzo() {
		return prezzo;
	}
	
	public Produttore getProduttore() {
		return produttore;
	}
	
	public static class Builder{
		private final String nome;
		private final double prezzo;
		private final Produttore produttore;
		private String descrizione;
		private Categoria categoria;
		private List<Fornitore> listaFornitori = new ArrayList<Fornitore>();
		
		public Builder(String nome, double prezzo, Produttore produttore){
			this.nome = nome;
			this.prezzo = prezzo;
			this.produttore = produttore;
			this.descrizione = " ";
			this.categoria = Categoria.CATEGORIA_0;
		}
		
		public Builder descrizione(String val){
			descrizione = val;
			return this;
		}
		
		public Builder categoria(Categoria val){
			categoria = val;
			return this;
		}
		
		public Builder listaFornitori(Fornitore... args){
			for(Fornitore val : args)
				listaFornitori.add(val);
			return this;
		}
		
		public Prodotto build(){
			return new Prodotto(this);
		}
	}
	
	private Prodotto(Builder build){
		nome = build.nome;
		prezzo = build.prezzo;
		descrizione = build.descrizione;
		produttore = build.produttore;
		categoria = build.categoria;
		listaFornitori = build.listaFornitori;
	}
	
}
