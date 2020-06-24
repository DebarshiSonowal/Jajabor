package jajabor.in.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    DocumentReference noteref;
    FirebaseFirestore db ;
EditText username,firstname,lastname,phone,pincode,address;
String uname,fnam,lnam,add;
int pno,pinno;
    @Override
    public void onStart() {
        super.onStart();
        noteref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getContext(), "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", e.toString());
                    return;
                }
                if(documentSnapshot.exists()){
                    username.setText(documentSnapshot.getString("Username"));
                    firstname.setText(documentSnapshot.getString("Firstname"));
                    lastname.setText(documentSnapshot.getString("Lastname"));
                    if(documentSnapshot.getString("Address") != null){
                        address.setText(documentSnapshot.getString("Address"));
                    }
                    if(documentSnapshot.getLong("Phone") != null){
                        phone.setText(documentSnapshot.getLong("Phone").intValue());
                    }
                    if(documentSnapshot.getLong("Pin") !=null){
                        pincode.setText(documentSnapshot.getLong("Pin").intValue());
                    }

                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_profile, container, false);
      db = FirebaseFirestore.getInstance();
      firebaseAuth = firebaseAuth.getInstance();
      String uid = firebaseAuth.getCurrentUser().getUid();
      noteref = db.document("UserProfile/"+uid);
      username = view.findViewById(R.id.editTextUsername);
      firstname  = view.findViewById(R.id.editTextFirstname);
      lastname = view.findViewById(R.id.editTextLastname);
      phone  = view.findViewById(R.id.editTextPhone);
      pincode = view.findViewById(R.id.editTextPinCode);
      address  = view.findViewById(R.id.editTextAddress);
        return view;
    }
}
