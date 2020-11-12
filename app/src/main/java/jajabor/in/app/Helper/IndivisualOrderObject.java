package jajabor.in.app.Helper;

import java.util.List;

public class IndivisualOrderObject {
    private List<String>picture,id,color,size;
    private List<Long>quantity;

    public IndivisualOrderObject(List<String> picture, List<String> id, List<String> color, List<String> size, List<Long> quantity) {
        this.picture = picture;
        this.id = id;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<Long> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Long> quantity) {
        this.quantity = quantity;
    }
}
