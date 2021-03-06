package it.unisalento.taco.model;

public enum Produttore {
	
    PRODUTTORE_A("Samsung"),
    PRODUTTORE_B("Kyocera"),
    PRODUTTORE_C("Toni"),
    PRODUTTORE_D("Bic"),
    PRODUTTORE_0("Produttore sconosciuto");

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

    public static Produttore parseProduttore(String nome) {
        if(nome.equals(PRODUTTORE_A.nome))
            return PRODUTTORE_A;
        else if(nome.equals(PRODUTTORE_B.nome))
            return PRODUTTORE_B;
        else if(nome.equals(PRODUTTORE_C.nome))
            return PRODUTTORE_C;
        else if(nome.equals(PRODUTTORE_D.nome))
            return PRODUTTORE_D;
        else
            return PRODUTTORE_0;
    }
}
