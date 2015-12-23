package it.unisalento.taco.model;

import it.unisalento.taco.dao.UtenteDAO;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoSuchUserException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public abstract class Utente implements IdentificabileID {
	
    private final int id;
    private final String nome;
    private final String cognome;
    private String email;
    
    public Utente(String nome, String cognome, String email){
        id = 0;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public Utente (int id, String nome, String cognome, String email){
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public String getNome(){
        return nome;
    }

    public String getCognome(){
        return cognome;
    }

    public String getEmail(){
        return email;
    }

    @Override public int getID(){
        return id;
    }
    
    public static int login(String email, String psw) throws NoSuchUserException{
        return UtenteDAO.getInstance().login(email,psw);
    }

    public static Utente getByID(int id) throws NoIDMatchException{
        return UtenteDAO.getInstance().getByID(id);
    }
    
    public void setPassword(Utente utente, String psw) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        UtenteDAO.getInstance().setPsw(utente, psw);
    }
    
    public static void addNewToDB(Utente utente){
        UtenteDAO.getInstance().create(utente);
    }
    
    @Override public String toString(){
        StringBuilder stringUtente = new StringBuilder();
        stringUtente.append("ID: ").append(id).append(", Nome: ").append(nome).
                     append(", Cognome: ").append(cognome).append(", Email: ").append(email);
        return stringUtente.toString();
    }
}