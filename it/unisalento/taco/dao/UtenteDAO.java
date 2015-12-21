package it.unisalento.taco.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dbconnections.DBConnection;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoSuchUserException;
import it.unisalento.taco.model.IdentificabileID;
import it.unisalento.taco.model.Utente;

public class UtenteDAO implements DAOInterface<Utente>{
	
    private static UtenteDAO instance;
    public static UtenteDAO getInstance(){
        if(instance == null)
            instance = new UtenteDAO(){};
        return instance;
    }
    private UtenteDAO (){};

    public List<? super Utente> getAllUtenti() {
        ArrayList<String[]> result = DBConnection.getInstance().queryDB("SELECT * FROM utenti");
        Iterator<String[]> i = result.iterator();
        List<Utente> listaUtenti = new ArrayList<>();

        while(i.hasNext()) {
            String[] riga = i.next();
            int id = Integer.parseInt(riga[0]);
            //Chiedo al database che tipo di istanza è
            try{
            Utente utente = getByID(id);
            listaUtenti.add(utente);
            }
            catch(NoIDMatchException e){
                e.printStackTrace();
            }
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

    @Override public Utente getByID(int id) throws NoIDMatchException{

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
                    case 0: return DipendenteDAO.getInstance().getByID(id);
                    case 1: return CapoProgettoDAO.getInstance().getByID(id);
                    case 2: return MagazziniereDAO.getInstance().getByID(id);
                }
        }
        throw new NoIDMatchException(this);
    }
    
    public int getID(Utente utente) throws NoIDMatchException{
        ArrayList<String[]> result =DBConnection.getInstance().queryDB("SELECT utenti.id FROM utenti WHERE email = " + utente.getEmail());
        Iterator<String[]> i = result.iterator();
        if(i.hasNext()) {
            String[] riga = i.next();
            return Integer.parseInt(riga[0]);
        }
        throw new NoIDMatchException(this);
    }
    
    public void setPsw (Utente utente, String psw){
        //Implementare
    }
    
    @Override public void create(Utente utente){
        if(utente.getID() == 0)
            DBConnection.getInstance().updateDB("INSERT INTO utenti(nome,cognome,email) VALUES('" + utente.getNome() + "','" + utente.getCognome() + "','" + utente.getEmail() + "')");
    }
    
    @Override public void update(Utente utente){
        DBConnection.getInstance().updateDB("UPDATE utenti SET nome = " + utente.getNome() + ", cognome = " + utente.getCognome() + ", email = " + utente.getEmail() + "WHERE id = " + utente.getID());
    }
    
    @Override public void delete(IdentificabileID utente){
        DBConnection.getInstance().updateDB("DELETE FROM utenti WHERE id = " + utente.getID());
    }
}
