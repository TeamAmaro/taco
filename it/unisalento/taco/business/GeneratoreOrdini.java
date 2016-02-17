package it.unisalento.taco.business;

import it.unisalento.taco.exceptions.NoIDMatchException;
import it.unisalento.taco.exceptions.NoProgettoException;
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

public class GeneratoreOrdini {
    
    private static GeneratoreOrdini instance;
    
    public static GeneratoreOrdini getInstance(){
        if(instance == null)
            instance = new GeneratoreOrdini();
        
        return instance;
    }
    
    private GeneratoreOrdini(){}
    
    public Set<Ordine> generaOrdini(Dipendente dipendente) throws NoIDMatchException, NoProgettoException, NoQueryMatchException{
        Set<Ordine> listaOrdini = new LinkedHashSet<>();
            Map<Prodotto,Integer> listaProdotti = Carrello.getCarrello(dipendente).getListaProdotti();
            Magazzino magDip = Magazzino.getMagazzino(dipendente.getSede());
            
            Map<Magazzino, Map<Prodotto,Integer>> magPerProdQuant = new LinkedHashMap<>();
            Map<Prodotto,Integer> prodQuantMagDip = new LinkedHashMap<>();
            
            for(Map.Entry<Prodotto,Integer> prodQuantEntry : listaProdotti.entrySet()){
                
                Prodotto prodRichiesto = prodQuantEntry.getKey();
                int quantMagDip = magDip.getInventario().get(prodRichiesto);
                int quantRichiesta = prodQuantEntry.getValue();
                                
                if(quantMagDip < quantRichiesta){
                                        
                    quantRichiesta -= quantMagDip;
                    if(quantMagDip != 0)
                        if(magPerProdQuant.containsKey(magDip))
                            magPerProdQuant.get(magDip).put(prodRichiesto, quantMagDip);
                        else {
                            prodQuantMagDip.put(prodRichiesto, quantMagDip);
                            magPerProdQuant.put(magDip, prodQuantMagDip);
                        }
                    
                    //Chiedi agli altri di soddisfare la richiesta
                    Set<Magazzino> listaMag = Magazzino.cercaProdotto(prodRichiesto);
                    listaMag.remove(magDip);
                                        
                    for(Magazzino magExt : listaMag){
                        int quantMagExt = magExt.getInventario().get(prodRichiesto);
                        
                        if(quantMagExt <= 0)
                            continue;
                        
                        if(quantMagExt < quantRichiesta){
                            quantRichiesta -= quantMagExt;
                            
                            if(magPerProdQuant.containsKey(magExt))
                                magPerProdQuant.get(magExt).put(prodRichiesto, quantMagExt);
                            else {
                                Map<Prodotto,Integer> prodQuantMagExt = new LinkedHashMap<>();
                                prodQuantMagExt.put(prodRichiesto, quantMagExt);
                                magPerProdQuant.put(magExt, prodQuantMagExt);
                            }
                        }else {
                            if(magPerProdQuant.containsKey(magExt))
                                magPerProdQuant.get(magExt).put(prodRichiesto, quantRichiesta);
                            else {
                                Map<Prodotto,Integer> prodQuantMagExt = new LinkedHashMap<>();
                                prodQuantMagExt.put(prodRichiesto, quantRichiesta);
                                magPerProdQuant.put(magExt, prodQuantMagExt);
                            }
                            break;
                        }
			if(quantRichiesta <= 0)
                            break;
                    }
                } else {
                    //Soddisfa la richiesta e passa al prossimo prodotto
                    if(magPerProdQuant.containsKey(magDip))
                        magPerProdQuant.get(magDip).put(prodRichiesto, quantRichiesta);
                    else {
                        prodQuantMagDip.put(prodRichiesto, quantRichiesta);
                        magPerProdQuant.put(magDip, prodQuantMagDip);
                    }
                }
            }
            Date date = new Date();
            long unixTime = date.getTime();
            for(Map.Entry<Magazzino,Map<Prodotto,Integer>> mag : magPerProdQuant.entrySet()){
                Ordine ordine = new Ordine(dipendente, Progetto.getProgetto(dipendente), mag.getKey(), unixTime, mag.getValue());
                listaOrdini.add(ordine);
            }
            return listaOrdini;
        
    }
}
