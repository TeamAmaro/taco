package it.unisalento.taco.model;

public enum Sede {
	
	SEDE_A("Sede A"),
	SEDE_B("Sede B"),
	SEDE_C("Sede C"),
	SEDE_D("Sede D");
	
	private final String nome;
	
	private Sede(String nome) {
		this.nome = nome;
	}
	
	public String nome(){
		return nome;
	}
	
	@Override public String toString(){
		return nome;
	}

}
