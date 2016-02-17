package it.unisalento.taco.exception;

public class NoSuchUserException extends Exception{
    
    public NoSuchUserException(){
        super();
    }
    
    public NoSuchUserException(String message){
        super(message);
    }
}
