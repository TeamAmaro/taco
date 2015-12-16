/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */
package it.unisalento.taco.dao;

import it.unisalento.taco.exceptions.NoIDMatchException;

public interface DAOInterface {
    public Object getByID(int id) throws NoIDMatchException;
}
