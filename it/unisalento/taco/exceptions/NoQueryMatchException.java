package it.unisalento.taco.exceptions;

public class NoQueryMatchException extends Exception{
    
    public NoQueryMatchException(){
        super();
    }
    
    public NoQueryMatchException(String message){
        super(message);
    }

}
