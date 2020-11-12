package jajabor.in.app.Helper;

import java.util.List;

public class AllOrderObject {
    List<String> Id,Colour,Image,Price,Desc,Categ,Size,Tag,Sku,Name,Shdesc;

    public AllOrderObject(List<String> id, List<String> colour, List<String> image, List<String> price, List<String> desc, List<String> categ, List<String> size, List<String> tag, List<String> sku, List<String> name, List<String> shdesc) {
        Id = id;
        Colour = colour;
        Image = image;
        Price = price;
        Desc = desc;
        Categ = categ;
        Size = size;
        Tag = tag;
        Sku = sku;
        Name = name;
        Shdesc = shdesc;
    }

    public List<String> getId() {
        return Id;
    }

    public void setId(List<String> id) {
        Id = id;
    }

    public List<String> getColour() {
        return Colour;
    }

    public void setColour(List<String> colour) {
        Colour = colour;
    }

    public List<String> getImage() {
        return Image;
    }

    public void setImage(List<String> image) {
        Image = image;
    }

    public List<String> getPrice() {
        return Price;
    }

    public void setPrice(List<String> price) {
        Price = price;
    }

    public List<String> getDesc() {
        return Desc;
    }

    public void setDesc(List<String> desc) {
        Desc = desc;
    }

    public List<String> getCateg() {
        return Categ;
    }

    public void setCateg(List<String> categ) {
        Categ = categ;
    }

    public List<String> getSize() {
        return Size;
    }

    public void setSize(List<String> size) {
        Size = size;
    }

    public List<String> getTag() {
        return Tag;
    }

    public void setTag(List<String> tag) {
        Tag = tag;
    }

    public List<String> getSku() {
        return Sku;
    }

    public void setSku(List<String> sku) {
        Sku = sku;
    }

    public List<String> getName() {
        return Name;
    }

    public void setName(List<String> name) {
        Name = name;
    }

    public List<String> getShdesc() {
        return Shdesc;
    }

    public void setShdesc(List<String> shdesc) {
        Shdesc = shdesc;
    }
}
