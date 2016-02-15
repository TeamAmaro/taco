package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoSuchUserException;
import it.unisalento.taco.model.Utente;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import it.unisalento.taco.security.Password;

public class UtenteDelegate {
    private static UtenteDelegate instance;
    
    public static UtenteDelegate getInstance() {
        if(instance == null)
            instance = new UtenteDelegate();
        return instance;
    }
    
    private UtenteDelegate(){}
    
    public Utente login(String email, String password) throws NoSuchUserException, NoIDMatchException{

        //Codifica la password con algoritmo MD5
        try{
            password = new Password(password).hash();
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            System.err.println(e.getMessage());
        }
        //Cerca l'utente nel database. Ci sono corrispondenze?
        try{
            //Trova ID dell'utente nel database
            int idUtente = Utente.login(email, password);
            //Se l'ID è stato trovato, istanzia il client con il modello corrispondente
            Utente client = Utente.getByID(idUtente);
            return client;
        }
        catch(NoSuchUserException | NoIDMatchException e){
            throw e;
        }
    }
    
    public void updatePassword(Utente utente, String psw) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        Utente.setPassword(utente, psw);
    }


}