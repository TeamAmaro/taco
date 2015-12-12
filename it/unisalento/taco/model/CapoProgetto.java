package it.unisalento.taco.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class CapoProgetto extends Utente {

	private Set<Progetto> listaProgetti = new LinkedHashSet<>();

	public CapoProgetto(int id, String nome, String cognome, String email){
		super(id,nome,cognome,email);
	}

	public CapoProgetto(int id, String nome, String cognome, String email, Progetto... args) {
		super(id,nome,cognome,email);
                for(Progetto val : args)
                    listaProgetti.add(val);
	}

	public Set<Progetto> getProgetto() {
		return listaProgetti;
	}

	public void addProgetto(Progetto progetto) {
		listaProgetti.add(progetto);
	}

}
