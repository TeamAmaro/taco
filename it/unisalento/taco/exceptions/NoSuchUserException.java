package it.unisalento.taco.exceptions;

public class NoSuchUserException extends Exception{
    
    public NoSuchUserException(){
        super();
    }
    
    public NoSuchUserException(String message){
        super(message);
    }
}
