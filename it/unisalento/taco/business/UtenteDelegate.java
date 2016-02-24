package it.unisalento.taco.business;

import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoSuchUserException;
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
    
    public Utente login(String email, String password) throws NoSuchUserException, NoIDMatchException, NoSuchAlgorithmException, UnsupportedEncodingException{

        //Codifica la password con algoritmo MD5
        password = new Password(password).hash();

        //Cerca l'utente nel database. Ci sono corrispondenze?
        //Trova ID dell'utente nel database
        int idUtente = Utente.login(email, password);
        //Se l'ID è stato trovato, istanzia il client con il modello corrispondente
        Utente client = Utente.getByID(idUtente);
        return client;

    }
    
    public void updatePassword(Utente utente, String psw) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        Utente.setPassword(utente, psw);
    }


}