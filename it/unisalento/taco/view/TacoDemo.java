package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import it.unisalento.taco.dbconnections.*;
import it.unisalento.taco.dao.*;
import java.util.Map;
import it.unisalento.taco.business.GeneratoreOrdini;
import it.unisalento.taco.exceptions.InsufficientFundException;
import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;

public class TacoDemo {

	public static void main(String[] args){
		//new LoginWindow();
                try{
                    Dipendente guga = DipendenteDAO.getInstance().getByID(1);
                    Map<Magazzino,Map<Prodotto,Integer>> mappa = GeneratoreOrdini.getInstance().magazzinoPerProdotto(guga);
                    System.out.println(Carrello.getByID(guga.getID()));
                    System.out.println(mappa);
                    Progetto progetto = Progetto.getProgetto(guga);
                    System.out.println(progetto);
                    GeneratoreOrdini.getInstance().generaOrdini(guga, mappa);
                    System.out.println(progetto);
                }
                catch(NoIDMatchException | InsufficientFundException | NoQueryMatchException e) {
                    System.err.println(e.getMessage());
                }
	}
}
