package it.unisalento.taco.model;

import it.unisalento.taco.dao.UtenteDAO;
import it.unisalento.taco.exceptions.NoSuchUserException;

public abstract class Utente {
	
    protected final int id;
    protected final String nome;
    protected final String cognome;
    protected String email;

    public Utente(int id, String nome, String cognome, String email){
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

    public int getID(){
        return id;
    }
    
    public static int getID(String email, String psw) throws NoSuchUserException{
        return UtenteDAO.getInstance().getID(email,psw);
    }

    public static Utente getLogin(int id){
        return UtenteDAO.getInstance().getLogin(id);
    }
    @Override public String toString(){
        StringBuilder stringUtente = new StringBuilder();
        stringUtente.append("ID: ").append(id).append(", Nome: ").append(nome).
                     append(", Cognome: ").append(cognome).append(", Email: ").append(email);
        return stringUtente.toString();
    }
}