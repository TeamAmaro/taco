/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.exceptions;

public class NoIDMatchException extends Exception{
    public NoIDMatchException(){
        super();
    }
    
    public NoIDMatchException(String message){
        super(message);
    }
}
