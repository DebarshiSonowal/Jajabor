package jajabor.in.app.ui.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.pd.chocobar.ChocoBar;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.HashMap;
import java.util.Map;

import jajabor.in.app.R;
import jajabor.in.app.ui.SearchResult.SharedViewModel;
import jajabor.in.app.ui.Activity.login;


public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    DocumentReference noteref;
    FirebaseFirestore db ;
    ElasticButton save;
    ElasticImageView logoutbtn;
    SharedViewModel viewModel;
    ProfileViewModel mModel;
    EventListener<DocumentSnapshot>mEventListener;
EditText username,firstname,lastname,phone,pincode,address,mail;
String uname,fnam,lnam,add,email;
String pno;
    String pinno;
    String uid;
    FirebaseUser mUser;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mEventListener = null;
        noteref = null;
        db = null;
        firebaseAuth = null;
        System.gc();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mUser != null) {
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
                        mail.setText(documentSnapshot.getString("Email"));
                        if(documentSnapshot.get("Phone") != null){
                            try {
                                Log.d("Value phone1",documentSnapshot.getString("Phone"));
                                phone.setText(documentSnapshot.getString("Phone"));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(documentSnapshot.get("Pin") !=null){

                            try {
                                Log.d("Value pin1",documentSnapshot.getString("Pin"));
                                pincode.setText(documentSnapshot.getString("Pin"));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    }

                }
            });
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser != null) {
                    Map<String,Object>note = new HashMap<>();
                    uname = username.getText().toString();
                    note.put("Username",uname);
                    fnam = firstname.getText().toString();
                    note.put("Firstname",fnam);
                    lnam = lastname.getText().toString();
                    note.put("Lastname",lnam);
                    add = address.getText().toString();
                    note.put("Address",add);
                    email = mail.getText().toString();
                    note.put("Email",email);
                        pinno =pincode.getText().toString();
                        Log.d("Value pin",pinno);
                        pno = phone.getText().toString();
                        note.put("Pin",pinno);
                        note.put("Phone",pno);
                    db.collection("UserProfile").document(uid).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            FancyToast.makeText(getContext(),"Saved Successfully",FancyToast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ChocoBar.builder().setActivity(getActivity())
                            .setText("Login or Sign up")
                            .setDuration(ChocoBar.LENGTH_SHORT)
                            .setActionText("Log in/ Sign up")
                            .setActionClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getContext(), login.class));
                                }
                            })
                            .red()
                            .show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_profile, container, false);
      db = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
      firebaseAuth = firebaseAuth.getInstance();
        if (mUser != null) {
            uid = firebaseAuth.getCurrentUser().getUid();
        }
        save = view.findViewById(R.id.savebtn);
        mail = view.findViewById(R.id.editEmail);
        logoutbtn = view.findViewById(R.id.logoutbtn);
      noteref = db.document("UserProfile/"+uid);
      username = view.findViewById(R.id.editTextUsername);
      firstname  = view.findViewById(R.id.editTextFirstname);
      lastname = view.findViewById(R.id.editTextLastname);
      phone  = view.findViewById(R.id.editTextPhone);
      pincode = view.findViewById(R.id.editTextPinCode);
      address  = view.findViewById(R.id.editTextAddress);
        if (mUser == null) {
            logoutbtn.setImageResource(R.drawable.ic_log_in);
        }
        logoutbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (mUser != null) {
                  FirebaseAuth.getInstance().signOut();
                  viewModel.setAuth(false);
                  startActivity(new Intent(getContext(),login.class));
              } else {
                  startActivity(new Intent(getContext(),login.class));
              }
          }
      });
        return view;
    }

}
