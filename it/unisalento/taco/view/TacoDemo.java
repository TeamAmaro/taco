package it.unisalento.taco.view;

import it.unisalento.taco.model.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unisalento.taco.dao.*;
import it.unisalento.taco.dbconnections.*;

public class TacoDemo {

	public static void main(String[] args) throws Exception{
		
            Utente x = UtenteDAO.getInstance().getLogin(1);
            
            System.out.println(x);
		
	}
}
