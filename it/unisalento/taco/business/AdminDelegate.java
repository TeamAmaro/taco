/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.business;

import it.unisalento.taco.dao.ProgettoDAO;
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

public class AdminDelegate {

    private static AdminDelegate instance;
    
    public static AdminDelegate getInstance(){
        if(instance == null)
            instance = new AdminDelegate();
        return instance;
    }
    
    private AdminDelegate(){};
    
    public void creaDipendente(String nome, String cognome, String email, String psw, String nomeSede){
        //CREA L'OGGETTO
        Dipendente dip = new Dipendente(nome, cognome, email, Sede.parseSede(nomeSede));
        //CREA NEL DATABASE UNA NUOVA RIGA CON NOME,COGNOME,EMAIL
        dip.addNewToDB(dip);
        //CREA LA PASSWORD CRIPTATA E LA IMPOSTA
        dip.setPassword(dip, psw);
        //AGGIUNGE IL DIPENDENTE ALLA TABELLA DIPENDENTI DEL DB
        dip.addToDB(dip);
    }
    
    public void creaMagazziniere(String nome, String cognome, String email, String psw){
        //CREA L'OGGETTO SENZA MAGAZZINO
        Magazziniere mag = new Magazziniere(nome, cognome, email);
        //CREA NEL DATABASE UNA NUOVA RIGA CON NOME,COGNOME,EMAIL
        mag.addNewToDB(mag);
        //CREA LA PASSWORD CRIPTATA E LA IMPOSTA
        mag.setPassword(mag, psw);
        //AGGIUNGE IL MAGAZZINIERE ALLA TABELLA MAGAZZINIERI DEL DB CON MAGAZZINO NULL
        mag.addToDB(mag);
        //RICORDARSI POI DI USARE setMagazzino()
    }
    
    public void creaMagazzino(String nome, String nomeSede){
        Magazzino mag = new Magazzino(nome, Sede.parseSede(nomeSede));
        mag.addNewToDB(mag);
    }
    
    public void setMagazzino(Magazziniere magazziniere, Magazzino magazzino){
        magazziniere.setMagazzino(magazzino);
    }
    
    public void creaCapoProgetto(String nome, String cognome, String email, String psw){
        CapoProgetto capo = new CapoProgetto(nome,cognome,email);
        capo.addNewToDB(capo);
        capo.addToDB(capo);
    }
    
    public void creaProgetto(String nome, CapoProgetto capo, double budget){
        Progetto progetto = new Progetto(nome, capo, budget);
        progetto.addToDB(progetto);
    }
    
    public void creaProdotto(String nome, double prezzo, Produttore produttore, String descrizione, Categoria categoria){
        Prodotto prodotto = new Prodotto.Builder(0, nome, prezzo, produttore).descrizione(descrizione).categoria(categoria).build();
        prodotto.addToDB(prodotto);
    }
    
    public void addProduttore(Produttore produttore, Prodotto prodotto){
        prodotto.addProduttore(produttore, prodotto);
    }
    
    public void addFornitore(Fornitore fornitore, Prodotto prodotto){
        prodotto.addFornitore(fornitore, prodotto);
    }
    
}
