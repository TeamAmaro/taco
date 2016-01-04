package it.unisalento.taco.business;

public class CapoProgettoDelegate {
    private static CapoProgettoDelegate instance;
    public static CapoProgettoDelegate getInstance(){
        if(instance == null)
            instance = new CapoProgettoDelegate();
        return instance;
    }
    
    private CapoProgettoDelegate(){}
    
}
