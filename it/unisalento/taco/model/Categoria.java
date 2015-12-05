package it.unisalento.taco.model;

public enum Categoria {
	
	CATEGORIA_A("Cancelleria"),
	CATEGORIA_B("Elettronica"),
	CATEGORIA_C("Ricambi"),
	CATEGORIA_D("Stampanti"),
	CATEGORIA_0("Nessuna categoria");
	
	private final String nome;
	
	private Categoria(String nome) {
		this.nome = nome;
	}
	
	public String nome() {
		return nome;
	}
	
	@Override public String toString(){
		return nome;
	}

}
