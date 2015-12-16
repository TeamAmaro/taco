package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.NoSuchUserException;
import it.unisalento.taco.model.Utente;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtenteDelegate {
    private static UtenteDelegate instance;
    
    public static UtenteDelegate getInstance() {
        if(instance == null)
            instance = new UtenteDelegate();
        return instance;
    }
    
    private UtenteDelegate(){}
    
    public Utente login(String email, String password){

        class Password {
            private String password;

            private Password(String password){
                this.password = password;
            }

            private String hash() throws NoSuchAlgorithmException, UnsupportedEncodingException{
                MessageDigest digest = MessageDigest.getInstance("MD5");
                byte[] bytes = password.getBytes("UTF-8");
                digest.update(bytes);
                byte[] hash = digest.digest(bytes);
                BigInteger big = new BigInteger(1,hash);
                password = big.toString(16);
                return password;
            }
        }
        //Codifica la password con algoritmo MD5
        try{
            password = new Password(password).hash();
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
        //Cerca l'utente nel database. Ci sono corrispondenze?
        try{
            //Trova ID dell'utente nel database
            int idUtente = Utente.getID(email, password);
            //Se l'ID è stato trovato, istanzia il client con il modello corrispondente
            Utente client = Utente.getLogin(idUtente);
            return client;
        }
        catch(NoSuchUserException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
}