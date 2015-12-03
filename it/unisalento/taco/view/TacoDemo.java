package it.unisalento.taco.view;

import it.unisalento.taco.model.*;

import java.sql.ResultSet;
import it.unisalento.taco.dbconnections.*;

public class TacoDemo {

	public static void main(String[] args) throws Exception{
		
		DBConnection conn = new DBConnection();
		
		ResultSet queryResult = conn.queryDB("SELECT nome,cognome,email FROM utenti");
		queryResult.next();
		
		Dipendente dip = new Dipendente(queryResult.getString("nome"),queryResult.getString("cognome"),queryResult.getString("email"));
		
		System.out.println("Dipendente");
		System.out.println("Nome: " + dip.getNome());
		System.out.println("Cognome: " + dip.getCognome());
		System.out.println("Email: " + dip.getEmail());
		
	}
}
