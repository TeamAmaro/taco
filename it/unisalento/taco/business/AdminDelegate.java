/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Categoria;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Fornitore;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Produttore;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;
import it.unisalento.taco.model.Utente;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class AdminDelegate {

    private static AdminDelegate instance;
    
    public static AdminDelegate getInstance(){
        if(instance == null)
            instance = new AdminDelegate();
        return instance;
    }
    
    private AdminDelegate(){};
    
    public void updateEmail(Utente utente, String email){
        Utente.updateEmail(utente, email);
    }
    
    public void creaDipendente(String nome, String cognome, String email, String psw, String nomeSede) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //CREA L'OGGETTO SENZA ID
        Dipendente dip = new Dipendente(nome, cognome, email, Sede.parseSede(nomeSede));
        //SALVA LA SEDE
        Sede sede = dip.getSede();
        //CREA NELLA TABELLA utenti UNA NUOVA RIGA CON NOME,COGNOME,EMAIL
        Dipendente.addNewToDB(dip);
        //OTTIENE IL NUOVO DIPENDENTE CON ID DAL DATABASE
        dip = Dipendente.getDipendente(dip.getEmail());
        //AGGIUNGE LA SEDE AL DIPENDENTE
        dip.setSede(sede);
        //CREA LA PASSWORD CRIPTATA E LA IMPOSTA
        Utente.setPassword(dip, psw);
        //AGGIUNGE IL DIPENDENTE ALLA TABELLA DIPENDENTI DEL DB
        Dipendente.addDipendente(dip);
    }
    
    public void creaMagazziniere(String nome, String cognome, String email, String psw) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //CREA L'OGGETTO SENZA MAGAZZINO
        Magazziniere mag = new Magazziniere(nome, cognome, email);
        //CREA NEL DATABASE UNA NUOVA RIGA CON NOME,COGNOME,EMAIL
        Magazziniere.addNewToDB(mag);
        //OTTIENE IL NUOVO MAGAZZINIERE CON ID
        mag = Magazziniere.getMagazziniere(mag.getEmail());
        //CREA LA PASSWORD CRIPTATA E LA IMPOSTA
        Utente.setPassword(mag, psw);
        //AGGIUNGE IL MAGAZZINIERE ALLA TABELLA MAGAZZINIERI DEL DB CON MAGAZZINO NULL
        Magazziniere.addMagazziniere(mag);
        //RICORDARSI POI DI USARE setMagazzino()
    }
    
    public void creaMagazzino(String nome, String nomeSede){
        Magazzino mag = new Magazzino(nome, Sede.parseSede(nomeSede));
        mag.addNewToDB(mag);
    }
    
    public void setMagazzino(Magazziniere magazziniere, Magazzino magazzino){
        Magazziniere.setMagazzino(magazziniere, magazzino);
    }
    
    public void creaCapoProgetto(String nome, String cognome, String email, String psw) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoQueryMatchException{
        CapoProgetto capo = new CapoProgetto(nome,cognome,email);
        CapoProgetto.addNewToDB(capo);
        capo = CapoProgetto.getCapoProgetto(capo.getEmail());
        Utente.setPassword(capo, psw);
        CapoProgetto.addCapoProgetto(capo);
    }
    
    public void creaProgetto(String nome, CapoProgetto capo, double budget){
        Progetto progetto = new Progetto(nome, capo, budget);
        Progetto.addProgetto(progetto);
    }
    
    public void setCapoProgetto(Progetto prog, CapoProgetto capo){
        Progetto.setCapoProgetto(prog, capo);
    }
    
    public void creaProdotto(String nome, double prezzo, Produttore produttore, String descrizione, Categoria categoria){
        Prodotto prodotto = new Prodotto.Builder(0, nome, prezzo, produttore).descrizione(descrizione).categoria(categoria).build();
        Prodotto.addProdotto(prodotto);
    }
    
    public void addProduttore(Produttore produttore, Prodotto prodotto){
        Prodotto.addProduttore(produttore, prodotto);
    }
    
    public void addFornitore(Fornitore fornitore, Prodotto prodotto){
        Prodotto.addFornitore(fornitore, prodotto);
    }
    
}
