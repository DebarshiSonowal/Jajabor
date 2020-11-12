package jajabor.in.app.ui.Women;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShimmeringViewModel extends ViewModel {
    private MutableLiveData<Integer>size =new MutableLiveData<>();

    public void setSize(Integer size) {
        this.size.setValue(size);
    }

    public LiveData<Integer> getSize() {
        return size;
    }
}
