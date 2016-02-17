/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.exception;

public class NoIDMatchException extends Exception{
    public NoIDMatchException(Object obj, int id){
        super("(#1) Nessuna corrispondenza di " + obj.getClass().getSimpleName() + " nel database per ID (" + id + ")");
    }
}
