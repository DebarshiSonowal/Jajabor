package jajabor.in.app.ui.Order;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

public class OrderStatusElement {
    List<String> orderid,orderstatus,price,picture,products;
    Context mContext;

    public OrderStatusElement(List<String> orderid, List<String> orderstatus, List<String> price, List<String> picture, List<String> products, Context context) {
        this.orderid = orderid;
        this.orderstatus = orderstatus;
        this.price = price;
        this.picture = picture;
        this.products = products;
        mContext = context;
    }

    public List<String> getOrderid() {
        return orderid;
    }

    public void setOrderid(List<String> orderid) {
        this.orderid = orderid;
    }

    public List<String> getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(List<String> orderstatus) {
        this.orderstatus = orderstatus;
    }

    public List<String> getPrice() {
        return price;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
