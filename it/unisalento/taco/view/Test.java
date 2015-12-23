/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.view;

import it.unisalento.taco.business.AdminDelegate;
import it.unisalento.taco.dao.MagazzinoDAO;
import it.unisalento.taco.dao.MagazziniereDAO;
import it.unisalento.taco.model.Magazziniere;
import it.unisalento.taco.model.Magazzino;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Test {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        Magazzino magazzino = MagazzinoDAO.getInstance().getMagazzino(3);
        Magazziniere magazziniere = MagazziniereDAO.getInstance().getMagazziniere("paolino.paperino@topolino.it");
        AdminDelegate.getInstance().setMagazzino(magazziniere, magazzino);
    }
    
    
}
