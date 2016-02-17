package it.unisalento.taco.exceptions;

public class NoMagazzinoException extends Exception{
    public NoMagazzinoException(int id){
        super("(#3) Non risultano magazzini associati a ID (" + id + ")");
    }
}

