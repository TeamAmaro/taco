/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.business;

public class ProgettoDelegate {

    private static ProgettoDelegate instance ;

    public static ProgettoDelegate getInstance(){
        if(instance == null)
            instance = new ProgettoDelegate();
        return instance;
    }
    private ProgettoDelegate(){};
    
    
    
}
