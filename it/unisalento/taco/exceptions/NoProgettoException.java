package it.unisalento.taco.exceptions;

public class NoProgettoException extends Exception{
    public NoProgettoException(int id){
        super("(#4) Non risultano progetti associati a ID (" + id + ")");
    }
}