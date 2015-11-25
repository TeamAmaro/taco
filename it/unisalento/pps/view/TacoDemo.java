package it.unisalento.pps.view;

import it.unisalento.pps.model.*;

public class TacoDemo {
	

	public static void main(String[] args){
		CapoProgetto tizio = new CapoProgetto("Guga","Rucola","chiara@petrucci.amore");
		tizio.setProgetto("Furto", 500.32);
		
		System.out.println("Il capoprogetto � " + tizio.getNome() + " " + tizio.getCognome());
		System.out.print("Il progetto � \"" + tizio.getProgetto().getNome() + "\" con saldo iniziale di ");
		System.out.println(tizio.getProgetto().getBudget());
		
		System.out.println("Il saldo ora � " + tizio.getProgetto().getSaldo());
		
		if (tizio.getProgetto().aggiornaSaldo((double)5000.3))
		{
			System.out.println("Guga ha rubato 500 matite con successo, ma il suo saldo � " + tizio.getProgetto().getSaldo());
		}
		else
		{
			System.out.println("At least you tried. Il tuo saldo � " + tizio.getProgetto().getSaldo());
		}
		
		
	}
}
