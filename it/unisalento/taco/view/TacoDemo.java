package it.unisalento.taco.view;

import it.unisalento.taco.controller.DipendenteController;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Progetto;
import it.unisalento.taco.model.Sede;

public class TacoDemo {
	

	public static void main(String[] args){
		Progetto progetto1 = new Progetto("Furto di stato", 5000.8);
		Dipendente dipendente1 = new Dipendente("Luke", "May Netty","luca@conoscenza.it", progetto1, Sede.SEDE_A);
		DipendenteView view1 = new DipendenteView();
		DipendenteController controller1 = new DipendenteController(dipendente1, view1);
		controller1.updateView();
		
		
	}
}
