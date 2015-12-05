package it.unisalento.taco.dao;

import java.util.ArrayList;

import it.unisalento.taco.model.Dipendente;

public interface DipendenteDAO {
	
	public ArrayList getAllDipendenti();
	public Dipendente getDipendente(int id);
	public void updateDipendente(Dipendente dipendente);
	public void deleteDipendente(Dipendente dipendente);
	
}
