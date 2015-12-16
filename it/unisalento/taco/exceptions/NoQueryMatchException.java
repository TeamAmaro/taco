package it.unisalento.taco.exceptions;

public class NoQueryMatchException extends Exception{
    public NoQueryMatchException(Object obj){
        super(obj.getClass().getSimpleName() + " : Nessuna corrispondenza nel database (1)");
    }
}
