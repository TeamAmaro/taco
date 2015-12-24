/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.view;

import it.unisalento.taco.business.AdminDelegate;
import it.unisalento.taco.dao.MagazzinoDAO;
import it.unisalento.taco.dao.MagazziniereDAO;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.CapoProgetto;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Progetto;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Test {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoQueryMatchException{
        CapoProgetto capo = CapoProgetto.getCapoProgetto("alfonso.verde@green.ve");
        AdminDelegate.getInstance().creaProgetto("Progetto Zero", capo, 20000);
        Progetto prog = Progetto.getProgetto("Progetto Zero");
        AdminDelegate.getInstance().setCapoProgetto(prog, capo);
    }
    
    
}
