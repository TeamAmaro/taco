package it.unisalento.taco.model;

public enum Fornitore {
	
    FORNITORE_A("Fornitore A"),
    FORNITORE_B("Fornitore B"),
    FORNITORE_C("Fornitore C"),
    FORNITORE_D("Fornitore D"),
    FORNITORE_0("Sconosciuto");

    private final String nome;

    private Fornitore(String nome){
        this.nome = nome;
    }

    public String nome() {
        return nome;
    }

    @Override public String toString(){
        return nome;
    }

    public static Fornitore parseFornitore(String fornitore){
        if(fornitore.equals(FORNITORE_A.nome))
            return FORNITORE_A;
        else if(fornitore.equals(FORNITORE_B.nome))
            return FORNITORE_B;
        else if(fornitore.equals(FORNITORE_C.nome))
            return FORNITORE_C;
        else if(fornitore.equals(FORNITORE_D.nome))
            return FORNITORE_D;
        else
            return FORNITORE_0;
    }
	
}
