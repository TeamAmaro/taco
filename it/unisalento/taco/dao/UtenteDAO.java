package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.exception.NoSuchUserException;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Utente;
import it.unisalento.taco.security.Password;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class UtenteDAO implements DAOInterface<Utente>{
	
    private static UtenteDAO instance;
    public static UtenteDAO getInstance(){
        if(instance == null)
            instance = new UtenteDAO(){};
        return instance;
    }
    private UtenteDAO (){};

    public List<? super Utente> getAllUtenti() throws NoIDMatchException {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti");
        Iterator<String[]> i = result.iterator();
        List<Utente> listaUtenti = new ArrayList<>();

        while(i.hasNext()) {
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            Utente utente = getById(id);
            listaUtenti.add(utente);

        }
        return listaUtenti;
    }

    public int login(String email, String psw) throws NoSuchUserException{
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti WHERE email = '" + email + "' AND password = '" + psw +"'");
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()){
            String[] riga = i.next();
            return Integer.parseInt(riga[0]);
        }
        else {
            throw new NoSuchUserException("Login: Nessuna corrispondenza tra mail e password.");
        }
    }

    @Override public Utente getById(int id) throws NoIDMatchException{

        String tabella = "";
        for(int i = 0; i < 3; i++){
            switch(i){
                case 0: tabella = "dipendenti";
                    break;
                case 1: tabella = "capiprogetto";
                    break;
                case 2: tabella = "magazzinieri";
                    break;
            }

            ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM " + tabella + " WHERE id_utente = " + id);
            Iterator<String[]> j = result.iterator();
            if(j.hasNext())
                switch(i){
                    case 0: return DipendenteDAO.getInstance().getById(id);
                    case 1: return CapoProgettoDAO.getInstance().getById(id);
                    case 2: return MagazziniereDAO.getInstance().getById(id);
                }
        }
        throw new NoIDMatchException(this, id);
    }
    
    public int getID(Utente utente) throws NoIDMatchException{
        ArrayList<String[]> result =DBConnection.getInstance().queryDB("SELECT utenti.id FROM utenti WHERE email = " + utente.getEmail());
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()) {
            String[] riga = i.next();
            return Integer.parseInt(riga[0]);
        }
        throw new NoIDMatchException(this, utente.getId());
    }
    
    public void setPsw (Utente utente, String psw) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        try {
            psw = new Password(psw).hash();
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            throw e;
        }
        DBConnection.getInstance().updateDB("UPDATE utenti SET password = '" + psw + "' WHERE id = " + utente.getId());
    }
    
    public void updateEmail(Utente utente, String email){
        DBConnection.getInstance().updateDB("UPDATE utenti SET email = '" + email + "' WHERE id = " + utente.getId());
    }
    
    @Override public void create(Utente utente){
        if(utente.getId() == 0)
            DBConnection.getInstance().updateDB("INSERT INTO utenti(nome,cognome,email,password) VALUES('" + utente.getNome() + "','" + utente.getCognome() + "','" + utente.getEmail() + "', 'asd')");
    }
    
    @Override public void update(Utente utente){
        DBConnection.getInstance().updateDB("UPDATE utenti SET nome = " + utente.getNome() + ", cognome = " + utente.getCognome() + ", email = " + utente.getEmail() + "WHERE id = " + utente.getId());
    }
    
    @Override public void delete(IdentificabileID utente){
        DBConnection.getInstance().updateDB("DELETE FROM utenti WHERE id = " + utente.getId());
    }
}
