/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */
package it.unisalento.taco.dao;

import it.unisalento.taco.exception.NoIDMatchException;
import it.unisalento.taco.model.IdentificabileID;

public interface DAOInterface<T>{
    public Object getById(int id) throws NoIDMatchException;
    //public void update(IdentificabileID obj);
    public void delete(IdentificabileID objId);
    public void update(T obj);
    public void create(T objId);
}
