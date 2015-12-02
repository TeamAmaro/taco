package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import it.unisalento.taco.controller.*;

public class TacoDemo {

	public static void main(String[] args){
		
		//CapoProgetto con progetto
		Progetto progetto = new Progetto("F U", 439.44);
		CapoProgetto capoprogetto1 = new CapoProgetto("John", "Cena", "john.cena@wwe.com", progetto);
		CapoProgettoView view1 = new CapoProgettoView();
		CapoProgettoController controller1 = new CapoProgettoController(capoprogetto1, view1);
		
		controller1.updateView();
		
		//CapoProgetto senza progetto
		CapoProgetto capoprogetto2 = new CapoProgetto("Luke", "Mynetti", "luke.mynetti@unimomento.it");
		CapoProgettoView view2 = new CapoProgettoView();
		CapoProgettoController controller2 = new CapoProgettoController(capoprogetto2, view2);
		
		controller2.updateView();
		
	}
}
