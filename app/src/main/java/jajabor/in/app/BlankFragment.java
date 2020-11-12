package jajabor.in.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vlonjatg.progressactivity.ProgressRelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;


public class BlankFragment extends Fragment {


    ToggleSwitch toggleSwitch,mToggleSwitch;
    ArrayList<String> labels,labels2;
    DatabaseReference mDocumentReference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        toggleSwitch = view.findViewById(R.id.sizeswitch);
        mToggleSwitch =view.findViewById(R.id.colorswitch);
        mDocumentReference = FirebaseDatabase.getInstance().getReference();
        mDocumentReference.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   if(dataSnapshot.getKey().equals("Color")){
                       labels = (ArrayList<String>) Arrays.asList(dataSnapshot.getValue().toString().split(","));
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        labels = new ArrayList<>();
        labels.add("AND");
        labels.add("OR");
        labels.add("XOR");
        labels.add("NOT");
        labels.add("OFF");
        toggleSwitch.setLabels(labels);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

}
}