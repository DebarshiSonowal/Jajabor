package jajabor.in.app;

public class Msg {
    String name,url,price,shrt;

    public Msg(String name, String url, String price,String shrt) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.shrt = shrt;
    }

    public String getUrl() {
        return url;
    }

    public String getShrt() {
        return shrt;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
