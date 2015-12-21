package it.unisalento.taco.view;

public enum ViewUtils {
    
    MINIMUM_WINDOW_HEIGHT(500.0),
    MINIMUM_WINDOW_WIDTH(390.0);
    
    private final double val;
    
    private ViewUtils(double val){
        this.val = val;
    }
    
    public double val(){
        return val;
    }
    
}
