package jajabor.in.app.ui.Order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderDetailsViewModel extends ViewModel {
   private MutableLiveData<String> id = new MutableLiveData<>(), size = new MutableLiveData<>()
           , color = new MutableLiveData<>(), pic= new MutableLiveData<>(),price = new MutableLiveData<>();
   private MutableLiveData<Integer> quantity= new MutableLiveData<>();

    public LiveData<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id.setValue(id);
    }

    public LiveData<String> getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size.setValue(size);
    }

    public LiveData<String> getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color.setValue(color);
    }

    public LiveData<String> getPic() {
        return pic;
    }
    public void setPrice(String price) {
        this.price.setValue(price);
    }

    public LiveData<String> getPrice() {
        return price;
    }
    public void setPic(String pic) {
        this.pic.setValue(pic);
    }

    public MutableLiveData<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity.setValue(quantity);
    }
}