package it.unisalento.taco.model;

import it.unisalento.taco.dao.ProdottoDAO;
import it.unisalento.taco.exception.NoIDMatchException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class Prodotto implements IdentificabileID {
	
    private final int id;
    private final String nome;
    private final double prezzo;
    private final Produttore produttore;
    private final String immagine;
    private String descrizione;
    private Categoria categoria;
    private Set<Fornitore> listaFornitori;
    private int annoDiProduzione;

    @Override public int getId(){
        return id;
    }
    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public Produttore getProduttore() {
        return produttore;
    }

    public Set<Fornitore> getListaFornitori(){
        return listaFornitori;
    }
    
    public String getImmagine() {
        return immagine;
    }
    
    public int getAnnoDiProduzione(){
        return annoDiProduzione;
    }

    public String getListaFornitoriAsString(){
        StringBuilder stringFornitori = new StringBuilder();
        for(Fornitore val : listaFornitori)
                stringFornitori.append(val.toString()).append(", ");
        int last = stringFornitori.lastIndexOf(",");
        if(last != -1)
            stringFornitori.delete(last, last + 2);
        return stringFornitori.toString();

    }
    
    public String getFormatPrezzo(){
        NumberFormat formatoEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
        formatoEuro.setMinimumFractionDigits( 2 );
        formatoEuro.setMaximumFractionDigits( 2 );
        formatoEuro.setRoundingMode(RoundingMode.HALF_EVEN);
        return formatoEuro.format(prezzo);
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }
    
    public void setListaFornitori(Set<Fornitore> listaFornitori){
        this.listaFornitori = listaFornitori;
    }

    public void addFornitore(Fornitore fornitore){
        if (listaFornitori.contains(Fornitore.FORNITORE_0))
                listaFornitori.remove(Fornitore.FORNITORE_0);
        listaFornitori.add(fornitore);
    }
	
    public static class Builder{
        private final int id;
        private final String nome;
        private final double prezzo;
        private final Produttore produttore;
        private String immagine;
        private String descrizione;
        private Categoria categoria;
        private Set<Fornitore> listaFornitori;
        private int annoDiProduzione;

        public Builder(int id, String nome, double prezzo, Produttore produttore){
            this.id = id;
            this.nome = nome;
            this.prezzo = prezzo;
            this.produttore = produttore;
            this.immagine = "thumbnail.jpg";
            this.descrizione = "Nessuna descrizione per il prodotto";
            this.categoria = Categoria.CATEGORIA_0;
            listaFornitori = new LinkedHashSet<>();
            listaFornitori.add(Fornitore.FORNITORE_0);
        }

        public Builder descrizione(String val){
            descrizione = val;
            return this;
        }

        public Builder categoria(Categoria val){
            categoria = val;
            return this;
        }

        public Builder listaFornitori(Fornitore... args){
            listaFornitori.remove(Fornitore.FORNITORE_0);
            listaFornitori.addAll(Arrays.asList(args));
            return this;
        }
        
        public Builder immagine(String val) {
            immagine = val;
            return this;
        }
        
        public Builder annoDiProduzione(int val){
            annoDiProduzione = val;
            return this;
        }

        public Prodotto build(){
            return new Prodotto(this);
        }
    }
	
    private Prodotto(Builder build){
        id = build.id;
        nome = build.nome;
        prezzo = build.prezzo;
        descrizione = build.descrizione;
        immagine = build.immagine;
        produttore = build.produttore;
        categoria = build.categoria;
        listaFornitori = build.listaFornitori;
        annoDiProduzione = build.annoDiProduzione;
    }
    
    public static void addProdotto(Prodotto prodotto){
        ProdottoDAO.getInstance().create(prodotto);
    }
    
    public static void addProduttore(Produttore produttore, Prodotto prodotto){
        ProdottoDAO.getInstance().addProduttore(produttore, prodotto);
    }
    
    public static void addFornitore(Fornitore fornitore, Prodotto prodotto){
        ProdottoDAO.getInstance().addFornitore(fornitore, prodotto);
    }

    public static Set<Prodotto> cerca(String ricerca) throws NoIDMatchException{
        return ProdottoDAO.getInstance().cerca(ricerca);
    }
    
    public static Set<Prodotto> cerca(Categoria categoria) throws NoIDMatchException{
        return ProdottoDAO.getInstance().cerca(categoria);
    }
    
    @Override public String toString(){
        StringBuilder prodottoString = new StringBuilder();
        prodottoString.append("ID: ").append(id).append(", Nome: ").append(nome).append(", Prezzo: ").
                       append(prezzo).append(", Produttore: ").append(produttore).append(", Descrizione: ").
                       append(descrizione).append(", Categoria: ").append(categoria).append(", Anno: ").
                       append(annoDiProduzione).append(", Fornitori: ").
                       append(getListaFornitoriAsString());
        return prodottoString.toString();
    }
    
    @Override public boolean equals(Object obj){

        if(obj == null)
            return false;
        else if(getClass() != obj.getClass())
            return false;

        final Prodotto other = (Prodotto) obj;
        return other.id == id;
    }

    @Override public int hashCode() {
        //PARACULATA
        return id;
    }
    
}
