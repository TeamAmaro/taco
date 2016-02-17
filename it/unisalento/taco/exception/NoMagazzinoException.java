package it.unisalento.taco.exception;

public class NoMagazzinoException extends Exception{
    public NoMagazzinoException(int id){
        super("(#3) Non risultano magazzini associati a ID (" + id + ")");
    }
}

