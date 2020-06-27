package jajabor.in.app.ui.Men;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jajabor.in.app.GridAdapter;
import jajabor.in.app.R;

public class MenFragment extends Fragment {
    List<String> url;
    List<String>name;
    List<String>price;
    GridAdapter mGridAdapter;
    DatabaseReference mFirebaseDatabase;
private GridView mGridView;

    @Override
    public void onStart() {
        super.onStart();
        Background back = new Background();
        back.execute();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_men, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        url = new ArrayList<>();
        name = new ArrayList<>();
        price = new ArrayList<>();
        mGridView = root.findViewById(R.id.mangrid);
        mGridAdapter = new GridAdapter(url,name,price,getContext());
        mGridView.setAdapter(mGridAdapter);

        return root;
    }
    class Background extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            if(dataSnapshot1.getKey().equals("Images")){
                                url.add(dataSnapshot1.getValue().toString());
                                Log.d("Value",dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().equals("Name")){
                                name.add(dataSnapshot1.getValue().toString());
                                Log.d("Value",dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().equals("Price")){
                                price.add(""+dataSnapshot1.getValue().toString());
                                Log.d("Value",dataSnapshot1.getValue().toString());
                            }
                        }
                    }
                    mGridAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }
    }
}