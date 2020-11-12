package jajabor.in.app.ui.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> Address = new MutableLiveData<>();
    private MutableLiveData<String> phone = new MutableLiveData<>();
    private MutableLiveData<String> pin = new MutableLiveData<>();
    private MutableLiveData<String> mail = new MutableLiveData<>();

    public void setAddress(String string){
        Address.setValue(string);
    }
    public void setPhone(String string){
        phone.setValue(string);
    }
    public void setPin(String string){
        pin.setValue(string);
    }
    public void setMail(String string){
        mail.setValue(string);
    }
    public LiveData<String> getAddress() {
        return Address;
    }
    public LiveData<String> getPhone() {
        return phone;
    }
    public LiveData<String> getMail() {
        return mail;
    }
    public LiveData<String> getPin() {
        return pin;
    }
}
