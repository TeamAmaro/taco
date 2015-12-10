package it.unisalento.taco.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Prodotto {
	
        private final int id;
	private final String nome;
	private final double prezzo;
	private final Produttore produttore;
	private String descrizione;
	private Categoria categoria;
	private Set<Fornitore> listaFornitori = new LinkedHashSet<Fornitore>();
	
        public int getID(){
            return id;
        }
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
	
	public Set<Fornitore> getListaFornitori(){
		return listaFornitori;
	}
	
	public String getListaFornitoriAsString(){
		StringBuilder stringFornitori = new StringBuilder();
		for(Fornitore val : listaFornitori)
			stringFornitori.append(val.toString() + " ");
		return stringFornitori.toString();
		
	}
	
	public void addFornitore(Fornitore fornitore){
		if (listaFornitori.contains(Fornitore.FORNITORE_0))
			listaFornitori.remove(Fornitore.FORNITORE_0);
		listaFornitori.add(fornitore);
	}
	
	public static class Builder{
                private final int id;
		private final String nome;
		private final double prezzo;
		private final Produttore produttore;
		private String descrizione;
		private Categoria categoria;
		private Set<Fornitore> listaFornitori = new LinkedHashSet<Fornitore>();
		
		public Builder(int id, String nome, double prezzo, Produttore produttore){
                        this.id = id;
			this.nome = nome;
			this.prezzo = prezzo;
			this.produttore = produttore;
			this.descrizione = "Nessuna descrizione";
			this.categoria = Categoria.CATEGORIA_0;
			this.listaFornitori.add(Fornitore.FORNITORE_0);
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
			listaFornitori.remove(Fornitore.FORNITORE_0);
			for(Fornitore val : args)
				listaFornitori.add(val);
			return this;
		}
		
		public Prodotto build(){
			return new Prodotto(this);
		}
	}
	
	private Prodotto(Builder build){
                id = build.id;
		nome = build.nome;
		prezzo = build.prezzo;
		descrizione = build.descrizione;
		produttore = build.produttore;
		categoria = build.categoria;
		listaFornitori = build.listaFornitori;
	}
	
	@Override public String toString(){
		StringBuilder prodottoString = new StringBuilder();
		prodottoString.append(id + " " + nome + " " + prezzo + " " + produttore + " " + descrizione + " " + categoria 
				+ " " + getListaFornitoriAsString());
		
		return prodottoString.toString();
	}
}
