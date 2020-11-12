package jajabor.in.app.ui.Men;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenViewModel extends ViewModel {
    private MutableLiveData<Integer>MenSize = new MutableLiveData<>();

    public LiveData<Integer> getMenSize() {
        return MenSize;
    }

    public void setMenSize(Integer menSize) {
        MenSize.setValue(menSize);
    }
}
