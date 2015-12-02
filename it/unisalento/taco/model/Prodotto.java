package it.unisalento.taco.model;

public class Prodotto {
	
	private final String nome;
	private final String descrizione;
	private final Categoria categoria;
	private double prezzo;
	private final Produttore produttore;

	public Prodotto(String nome, String descrizione, Categoria categoria, double prezzo, Produttore produttore){
		this.nome = nome;
		this.descrizione = descrizione;
		this.categoria = categoria;
		this.prezzo = prezzo;
		this.produttore = produttore;
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
	
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
}
