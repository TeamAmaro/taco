package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import it.unisalento.taco.dbconnections.*;
import it.unisalento.taco.dao.*;
import java.util.Map;
import it.unisalento.taco.business.GeneratoreOrdini;

public class TacoDemo {

	public static void main(String[] args){
		//new LoginWindow();
                Dipendente guga = DipendenteDAO.getInstance().getDipendente(1);
                Map<Magazzino,Map<Prodotto,Integer>> mappa = GeneratoreOrdini.getInstance().magazzinoPerProdotto(guga);
                GeneratoreOrdini.getInstance().generaOrdini(guga, mappa);
                Ordine dao = OrdineDAO.getInstance().getOrdine(-1820136442);
                System.out.println("CODICE INSERITO: -1820136442 / CODICE HASH OTTENUTO : " + dao.hashCode());
                System.out.println("Se sono uguali il sistema funziona");
	}
}
