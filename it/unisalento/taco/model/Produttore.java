package it.unisalento.taco.model;

public enum Produttore {
	
	PRODUTTORE_A("SunSang"),
	PRODUTTORE_B("Kyoto"),
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
