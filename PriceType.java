package sample;

public class PriceType  {
    public String priceType ;

    PriceType(){
        this.priceType = "";
    }

    PriceType(String pType){
        this.priceType = pType;
    }
    public String getType(){
        return priceType;
    }
}
