package it.unisalento.taco.model;

public enum Magazzino {

	MAGAZZINO_A("Magazzino A", Sede.SEDE_A),
	MAGAZZINO_B("Produttore B", Sede.SEDE_B),
	MAGAZZINO_C("Produttore C", Sede.SEDE_C),
	MAGAZZINO_D("Produttore D", Sede.SEDE_D);
	
	private final String nome;
	private final Sede sede;
	
	private Magazzino(String nome, Sede sede) {
		this.nome = nome;
		this.sede = sede;
	}
	
	public String nome(){
		return nome;
	}
	
	public String sede(){
		return sede.nome();
	}
	
}
