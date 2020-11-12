package jajabor.in.app.Helper;

import java.util.List;

public class OrderObject {
    List<String>OrderId,Colour,Image,Price,Status;


    public OrderObject( List<String>image,List<String> orderId, List<String> colour, List<String> price, List<String> status) {
        OrderId = orderId;
        Colour = colour;
        Price = price;
        Status = status;
        Image = image;
    }

    public List<String> getImage() {
        return Image;
    }

    public void setImage(List<String> image) {
        Image = image;
    }

    public List<String> getOrderId() {
        return OrderId;
    }

    public void setOrderId(List<String> orderId) {
        OrderId = orderId;
    }

    public List<String> getColour() {
        return Colour;
    }

    public void setColour(List<String> colour) {
        Colour = colour;
    }

    public List<String> getPrice() {
        return Price;
    }

    public void setPrice(List<String> price) {
        Price = price;
    }

    public List<String> getQuantity() {
        return Status;
    }

    public void setQuantity(List<String> quantity) {
        Status = quantity;
    }
}
