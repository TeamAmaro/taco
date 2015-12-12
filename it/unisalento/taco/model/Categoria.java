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

        public static Categoria parseCategoria(String categoria){
            if(categoria.equals(CATEGORIA_A.nome))
                return CATEGORIA_A;
            else if(categoria.equals(CATEGORIA_B.nome))
                return CATEGORIA_B;
            else if(categoria.equals(CATEGORIA_C.nome))
                return CATEGORIA_C;
            else if(categoria.equals(CATEGORIA_D.nome))
                return CATEGORIA_D;
            else
                return CATEGORIA_0;
        }
}
