package it.unisalento.taco.business;

import it.unisalento.taco.model.Ordine;
import it.unisalento.taco.model.Prodotto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
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
    
    public void generaRicevuta(Set<Ordine> listaOrdini, File file) throws IOException, COSVisitorException {	
        try {
            
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
            PDFont font = PDType1Font.HELVETICA;
            
            BufferedImage logoBuffer = ImageIO.read(new File("src/it/unisalento/taco/view/img/logo2.png"));
            PDXObjectImage logo = new PDPixelMap(document, logoBuffer);
            
            for(Ordine o : listaOrdini){
                
                int i = 730;
                
                document.addPage(page);
                
                try (PDPageContentStream cos = new PDPageContentStream(document, page)) {
                    
                    cos.drawImage(logo, 130, i);
                    cos.setFont(font, 12);
                    
                    i-=40;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("Ricevuta ordine di " + o.getDipendente().getNome() + " " + o.getDipendente().getCognome());
                    cos.endText();
                    
                    i-= 20;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("CODICE: " + o.getCodice());
                    cos.endText();
                    
                    i-= 20;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("DIPENDENTE: " + o.getDipendente().getNome() + " " + o.getDipendente().getCognome());
                    cos.endText();
                    
                    i-= 20;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("PROGETTO: " + o.getProgetto().getNome() + " - " + o.getProgetto().getCapoProgetto().getNome() +" " + o.getProgetto().getCapoProgetto().getCognome());
                    cos.endText();
                    
                    i-= 20;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("DATA: " + o.getReadableData());
                    cos.endText();
                    
                    i-= 20;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("LISTA PRODOTTI");
                    cos.endText();
                    
                    i-= 20;
                    
                    for(Map.Entry<Prodotto,Integer> e : o.getListaProdotti().entrySet()){
                        cos.beginText();
                        cos.moveTextPositionByAmount(50, i);
                        cos.drawString(e.getKey().getFormatPrezzo().replace('€', ' ') + " euro \""  + e.getKey().getNome()+ "\" x " + e.getValue());
                        cos.endText();
                        i -= 20;
                    }
                      
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("MAGAZZINO: " + o.getMagazzino().getNome() + " (" + o.getMagazzino().getSede().nome() +  ")");
                    cos.endText();
                    i-= 20;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("TOTALE PRODOTTI: " + o.getFormatTotale().replace('€', ' ') + " euro");
                    cos.endText();
                    i-= 20;
                    
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("COSTO SPEDIZIONE: " + o.getFormatSpesaSpedizione().replace('€', ' ') + " euro");
                    cos.endText();
                    i-= 20;
                    
                    double totale;
                    NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
                    formatoEuro.setMinimumFractionDigits( 2 );
                    formatoEuro.setMaximumFractionDigits( 2 );
                    formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);

                    totale = o.getTotale() + o.getSpesaSpedizione();
                                
                    cos.beginText();
                    cos.moveTextPositionByAmount(50, i);
                    cos.drawString("TOTALE: " + formatoEuro.format(totale).replace('€', ' ') + " euro");
                    cos.endText();
                }
            }
            document.save(file);
            document.close();
            
        } catch (IOException e) {
            throw e;
        }	
        
    }
}
