
public class Carrello {
	
	int[][] listaProdotti = new int[10][10]; //contiene l'id del prodotto e la quantità
	
	
	void generaOrdine(){}
	void controllaSede(){}
	
	double calcolaPrezzoTotale(){
		
		double totale = 0;
		
		for(int i = 0; i < listaProdotti.length; i++)
			totale += listaProdotti[i][i];
		
		return totale;
	}
}
