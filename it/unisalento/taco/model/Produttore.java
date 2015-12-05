package it.unisalento.taco.model;

public enum Produttore {
	
	PRODUTTORE_A("Produttore A"),
	PRODUTTORE_B("Produttore B"),
	PRODUTTORE_C("Produttore C"),
	PRODUTTORE_D("Produttore D");
	
	private final String nome;
	
	Produttore(String nome) {
		this.nome = nome;
	}
	
	public String nome(){
		return nome;
	}
	
	@Override public String toString(){
		return nome;
	}
	
}
