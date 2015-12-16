/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.exceptions;

public class NoIDMatchException extends Exception{
    public NoIDMatchException(Object obj){
        super(obj.getClass().getSimpleName() + " : Nessuna corrispondenza nel database per ID (2)");
    }
}
