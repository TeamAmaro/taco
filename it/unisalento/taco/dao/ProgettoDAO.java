package it.unisalento.taco.dao;

import java.util.ArrayList;

import it.unisalento.taco.model.Progetto;

public interface ProgettoDAO {
	
	public ArrayList getAllProgetto();
	public Progetto getProgetto(int id);
	public void updateProgetto();
	public void deleteProgetto();
	
}
