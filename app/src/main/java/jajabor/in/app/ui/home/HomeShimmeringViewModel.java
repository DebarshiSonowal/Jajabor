package jajabor.in.app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeShimmeringViewModel extends ViewModel {
    private MutableLiveData<Integer>couplesize = new MutableLiveData<>();
    private MutableLiveData<Integer> bihusize= new MutableLiveData<>();

    public HomeShimmeringViewModel() {
    }

    private MutableLiveData<Integer> allsize= new MutableLiveData<>();

    public HomeShimmeringViewModel(MutableLiveData<Integer> couplesize, MutableLiveData<Integer> bihusize, MutableLiveData<Integer> allsize) {
        this.couplesize = couplesize;
        this.bihusize = bihusize;
        this.allsize = allsize;
    }

    public LiveData<Integer> getCouplesize() {
        return couplesize;
    }

    public void setCouplesize(Integer couplesize) {
        this.couplesize.setValue(couplesize);
    }

    public LiveData<Integer> getBihusize() {
        return bihusize;
    }

    public void setBihusize(Integer bihusize) {
        this.bihusize.setValue(bihusize);
    }

    public LiveData<Integer> getAllsize() {
        return allsize;
    }

    public void setAllsize(Integer allsize) {
        this.allsize.setValue(allsize);
    }
}
