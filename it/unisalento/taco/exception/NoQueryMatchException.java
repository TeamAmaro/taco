package it.unisalento.taco.exception;

public class NoQueryMatchException extends Exception{
    
    public NoQueryMatchException(){
        super();
    }
    
    public NoQueryMatchException(String message){
        super(message);
    }

}
