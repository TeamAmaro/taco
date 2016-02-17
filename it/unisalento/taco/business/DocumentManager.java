package it.unisalento.taco.business;

import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class DocumentManager {
    private static DocumentManager instance;
    
    public static DocumentManager getInstance(){
        
        if (instance == null)
            instance = new DocumentManager();
        
        return instance;
    }
    
    private DocumentManager(){}
    
    public void generaRicevuta(Set<Ordine> listaOrdini, File file) throws IOException, COSVisitorException{
        PDDocument document = new PDDocument();
        PDFont font = PDType1Font.HELVETICA;
        
        BufferedImage logoBuffer = ImageIO.read(new File("src/it/unisalento/taco/view/img/logo2.png"));
        PDXObjectImage logo = new PDPixelMap(document, logoBuffer);
            
        for(Ordine o : listaOrdini){
            
            int offsetY = 730;
            PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
            document.addPage(page);
            
            try(PDPageContentStream cos = new PDPageContentStream(document,page)){
                cos.drawImage(logo, 130, offsetY);
                cos.setFont(font, 12);
                offsetY-=40;

                String[] parteUno = {"Ricevuta ordine di " + o.getDipendente().getNome() + " " + o.getDipendente().getCognome(), 
                                    "CODICE: " + o.getCodice(),
                                    "DIPENDENTE: " + o.getDipendente().getNome() + " " + o.getDipendente().getCognome(),
                                    "PROGETTO: " + o.getProgetto().getNome() + " - " + o.getProgetto().getCapoProgetto().getNome() +" " + o.getProgetto().getCapoProgetto().getCognome(),
                                    "DATA: " + o.getReadableData(),
                                    "LISTA PRODOTTI"};

                String [] listaProdotti = new String[o.getListaProdotti().keySet().size()];

                int k = 0;
                for(Map.Entry<Prodotto,Integer> e : o.getListaProdotti().entrySet()){
                   listaProdotti[k++] = "\"" + e.getKey().getNome() + "\" " + e.getKey().getFormatPrezzo().replace('€', ' ') + " euro"  +  " x " + e.getValue();
                }

                double totale;
                NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
                formatoEuro.setMinimumFractionDigits( 2 );
                formatoEuro.setMaximumFractionDigits( 2 );
                formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
                totale = o.getTotale() + o.getSpesaSpedizione();

                String [] parteDue = {"MAGAZZINO: " + o.getMagazzino().getNome() + " (" + o.getMagazzino().getSede().nome() +  ")",
                                      "SUBTOTALE: " + o.getFormatTotale().replace('€', ' ') + " euro",
                                      "COSTO SPEDIZIONE: " + o.getFormatSpesaSpedizione().replace('€', ' ') + " euro",
                                      "TOTALE: " + formatoEuro.format(totale).replace('€', ' ') + " euro"};

                for (int i = 0; i < parteUno.length; i++){
                    try {
                        cos.beginText();
                        cos.moveTextPositionByAmount(50, offsetY);
                        cos.drawString(parteUno[i]);
                        cos.endText();
                        offsetY -= 20;
                    } catch (IOException ex) {
                       throw ex;
                    }
                }

                for (int i = 0; i < listaProdotti.length; i++){
                    try {
                        cos.beginText();
                        cos.moveTextPositionByAmount(50, offsetY);
                        cos.drawString(listaProdotti[i]);
                        cos.endText();
                        offsetY -= 20;
                    } catch (IOException ex) {
                       throw ex;
                    }
                }

                for(int i = 0; i < parteDue.length; i++){
                    try {
                        cos.beginText();
                        cos.moveTextPositionByAmount(50, offsetY);
                        cos.drawString(parteDue[i]);
                        cos.endText();
                        offsetY-=20;
                    } catch (IOException ex) {
                       throw ex;
                    }
                }
                cos.close();
            }  

        }
                    
        document.save(file);
        document.close();
    }
    
    
}
