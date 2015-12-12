package it.unisalento.taco.model;

public enum Sede {
	
    SEDE_A("Sede A"),
    SEDE_B("Sede B"),
    SEDE_C("Sede C"),
    SEDE_D("Sede D"),
    SEDE_0("Sede sconosciuta");

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
    
     public static Sede parseSede(String sede){
            if(sede.equals(SEDE_A.nome))
                return SEDE_A;
            else if(sede.equals(SEDE_B.nome))
                return SEDE_B;
            else if(sede.equals(SEDE_C.nome))
                return SEDE_C;
            else if(sede.equals(SEDE_D.nome))
                return SEDE_D;
            else
                return SEDE_0;
        }

}
