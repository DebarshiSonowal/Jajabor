package jajabor.in.app.ui.Cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Integer>no = new MutableLiveData<>();

    public void setNo(int no) {
       this.no.setValue(no);
    }
    public LiveData<Integer> getno(){
        return no;
    }
}
