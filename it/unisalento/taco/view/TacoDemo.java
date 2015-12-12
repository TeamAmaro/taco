package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dao.*;
import it.unisalento.taco.dbconnections.*;

public class TacoDemo {

	public static void main(String[] args) throws Exception{
		
            Magazzino magazzino = new Magazzino(1,"Magazzino del Sole", Sede.SEDE_A);
            
            System.out.println(MagazzinoDAO.getInstance().getInventario(magazzino));
		
	}
}
