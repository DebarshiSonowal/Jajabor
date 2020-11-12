package jajabor.in.app.ui.SearchResult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.w3c.dom.Text;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<CharSequence> text = new MutableLiveData<>();
    private MutableLiveData<String> type = new MutableLiveData<>();
    private MutableLiveData<Boolean>Auth=new MutableLiveData<>();
    public void setText(CharSequence input) {
        text.setValue(input);
    }
    public void setType(String string){
        type.setValue(string);
    }
    public void setAuth(Boolean auth) {
        Auth.setValue(auth);
    }

    public LiveData<CharSequence> getText() {
        return text;
    }
    public  LiveData<Boolean> getAuth(){
        return Auth;
    }

    public LiveData<String> getType() {
        return type;
    }
}
