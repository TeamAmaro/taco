package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dao.*;
import it.unisalento.taco.dbconnections.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import it.unisalento.taco.business.GeneratoreOrdini;

public class TacoDemo {

	public static void main(String[] args){
		//new LoginWindow();
                Dipendente guga = DipendenteDAO.getInstance().getDipendente(1);
                System.out.println(guga);
                System.out.println(guga.getCarrello());
                System.out.println(guga.getProgetto());
                Map<Magazzino,Map<Prodotto,Integer>> mappa = GeneratoreOrdini.getInstance().magazzinoPerProdotto(guga);
                System.out.println(mappa);
	}
}
