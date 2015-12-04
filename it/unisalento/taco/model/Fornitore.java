package it.unisalento.taco.model;

public enum Fornitore {
	
	FORNITORE_A("FornitoreA"),
	FORNITORE_B("FornitoreB"),
	FORNITORE_C("FornitoreC");
	
	private final String nome;
	
	private Fornitore(String nome){
		this.nome = nome;
	}
	
	public String nome() {
		return nome;
	}
	
}
