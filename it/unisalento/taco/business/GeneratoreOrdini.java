package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoQueryMatchException;
import it.unisalento.taco.model.Carrello;
import it.unisalento.taco.model.Dipendente;
import it.unisalento.taco.model.Magazzino;
import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import it.unisalento.taco.model.Progetto;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratoreOrdini {
    private static GeneratoreOrdini instance;
    
    private static GeneratoreOrdini getInstance(){
        if(instance == null)
            instance = new GeneratoreOrdini();
        
        return instance;
    }
    
    private GeneratoreOrdini(){}
    
    public Set<Ordine> generaOrdini(Dipendente dipendente){
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
        try {
            Map<Prodotto,Integer> listaProdotti = Carrello.getCarrello(dipendente).getListaProdotti();
            Magazzino magDipendente = Magazzino.getMagazzino(dipendente.getSede());
            
            Map<Magazzino, Map<Prodotto,Integer>> magPerProdQuant = new LinkedHashMap<>();
            
            
            for(Map.Entry<Prodotto,Integer> prodQuantEntry : listaProdotti.entrySet()){
                
                Prodotto prodRichiesto = prodQuantEntry.getKey();
                int quantMagDip = magDipendente.getInventario().get(prodRichiesto);
                int quantRichiesta = prodQuantEntry.getValue();
                Map<Prodotto,Integer> prodQuantMagDip = new LinkedHashMap<>();
                
                if(quantMagDip < quantRichiesta){
                    
                    quantRichiesta -= quantMagDip;
                    
                    
                    prodQuantMagDip.put(prodRichiesto, quantMagDip);
                    magPerProdQuant.put(magDipendente, prodQuantMagDip);
                        
                    //Chiedi agli altri di soddisfare la richiesta
                    
                    Set<Magazzino> listaMag = Magazzino.cercaProdotto(prodRichiesto);
                    
                    listaMag.remove(magDipendente);
                                        
                    for(Magazzino magExt : listaMag && quantRichiesta != 0){
                        
                        int quantMagExt = magExt.getInventario().get(prodRichiesto);
                        if(quantMagExt < quantRichiesta){
                            quantRichiesta -= quantMagExt;
                            Map<Prodotto,Integer> prodQuantMagExt = new LinkedHashMap<>();
                            prodQuantMagExt.put(prodRichiesto, quantMagExt);
                            magPerProdQuant.put(magExt, prodQuantMagExt);
                        }else {
                            Map<Prodotto,Integer> prodQuantMagExt = new LinkedHashMap<>();
                            prodQuantMagExt.put(prodRichiesto, quantRichiesta);
                            magPerProdQuant.put(magExt, prodQuantMagExt);
                            break;
                        }
                    }
                } else {
                    //Soddisfa la richiesta e passa al prossimo prodotto
                    prodQuantMagDip.put(prodRichiesto, quantRichiesta);
                    magPerProdQuant.put(magDipendente, prodQuantMagDip);
                }
            }
                
            Date date = new Date();
            long unixTime = date.getTime();

            for(Map.Entry<Magazzino,Map<Prodotto,Integer>> mag : magPerProd.entrySet()){
                Ordine ordine = new Ordine(dipendente, Progetto.getProgetto(dipendente), mag.getKey(), unixTime, mag.getValue());
                listaOrdini.add(ordine);
            }
        } catch (NoIDMatchException | NoQueryMatchException ex) {
            Logger.getLogger(GeneratoreOrdini.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaOrdini;
    }
}
