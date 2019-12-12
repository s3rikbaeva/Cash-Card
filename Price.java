package sample;

public class Price extends PriceType implements PrePrice {
    private double  price;
    private PriceType type ;


    public Price(){
        this.price = 0.;
        this.type  = new PriceType();
    }

    public Price(double price, String type ){

        this.price = price;
        this.type  = new PriceType(type);
    }

    public double getPrice(){
        return price;
    }


    public String typeOfPrice(){
        return "Unknown";
    }
}
