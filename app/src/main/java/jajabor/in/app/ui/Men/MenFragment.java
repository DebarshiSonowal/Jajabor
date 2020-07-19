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
    List<String> url,url1;
    List<String>name,name1;
    Background back;
    List<String>price,price1;
    List<String>categ,shrdesc;
    List<Integer>no,pid;
    GridAdapter mGridAdapter;
    DatabaseReference mFirebaseDatabase;
    ValueEventListener mValueEventListener;
private GridView mGridView;

    @Override
    public void onStart() {
        super.onStart();
        back = new Background();
        back.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGridAdapter = null;
        mGridView=null;
        mFirebaseDatabase.removeEventListener(mValueEventListener);
        mFirebaseDatabase = null;
        mValueEventListener = null;
        back.cancel(true);
        System.gc();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.gc();
        View root = inflater.inflate(R.layout.fragment_men, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        url = new ArrayList<>();
        url1= new ArrayList<>();
        name1= new ArrayList<>();
        name = new ArrayList<>();
        price = new ArrayList<>();
        price1= new ArrayList<>();
        categ = new ArrayList<>();
        shrdesc =new ArrayList<>();
        pid = new ArrayList<>();
        no = new ArrayList<>();
        mGridView = root.findViewById(R.id.mangrid);
        mGridAdapter = new GridAdapter(url1,name1,price1,shrdesc,pid,getContext(),getActivity());
        mGridView.setAdapter(mGridAdapter);
        return root;
    }
     class Background extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            mFirebaseDatabase.addValueEventListener(mValueEventListener = new ValueEventListener() {
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
                            if(dataSnapshot1.getKey().equals("Categories")){
                                categ.add(dataSnapshot1.getValue().toString());
                                Log.d("Value",dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().equals("Short description")){
                                shrdesc.add(""+dataSnapshot1.getValue().toString());
                                Log.d("Value5",dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().equals("ID")){
                                pid.add( Integer.parseInt(dataSnapshot1.getValue().toString()) );
                                Log.d("Value6",dataSnapshot1.getValue().toString());
                            }
                        }
                    }
                  getExecute();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }
    }

    private void getExecute() {
        for (int i=0;i<categ.size();i++){
            Log.d("Value",categ.get(i).split("[,]", 0).toString());
            for(String myStr: categ.get(i).split("[,]", 0)) {
                if(myStr.toLowerCase().trim().equals("men")){
                    no.add(i);
                    Log.d("ValueQ",i+"");
                }
            }
        }

            for(int j=0;j<no.size();j++){

                    url1.add(url.get(no.get(j)));
//                    Log.d("ValueW",url.get(j));
                    name1.add(name.get(no.get(j)));
//                    Log.d("ValueE",name.get(j));
                    price1.add(price.get(no.get(j)));
//                    Log.d("ValueR",price.get(j));

            }

        mGridAdapter.notifyDataSetChanged();
    }
}