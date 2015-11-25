
public class Prodotto {
	
	int id;
	String nome;
	String descrizione;
	double prezzo;
	int maxQuantitaOrdine = 0; //default è zero e indica nessun limite

	String produttore(int id){
		
		String nomeProduttore = "Sconosciuto"; //default
		
		if(id == 1)
			nomeProduttore = "SaporiPreziosi";
		
		return nomeProduttore;
	}
	
	void disponibilita(){}
	void generaReclamo(){}

}
