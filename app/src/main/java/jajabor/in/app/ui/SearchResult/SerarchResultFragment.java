package jajabor.in.app.ui.SearchResult;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jajabor.in.app.Adapters.GridAdapter;
import jajabor.in.app.R;
import jajabor.in.app.ui.Activity.MainActivity2;

public class SerarchResultFragment extends Fragment {
    DatabaseReference db;
    List<String>name,shrdesc,url,price,tag,categ;
    String querry;
    List<String>name1,shrdesc1,url1,price1,tag1,categ1;
    List<Integer>PID,PID1,no,no1;
    GridView mGridView;
    GridAdapter mGridAdapter;
    MainActivity2 mMainActivity2;
    private SharedViewModel viewModel;
    public static SerarchResultFragment newInstance(String text) {
        SerarchResultFragment fragment = new SerarchResultFragment();
        Bundle args = new Bundle();
        args.putString("Querry", text);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {
               querry = charSequence.toString();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_serarch_result, container, false);
        mMainActivity2 = new MainActivity2();
//        if (getArguments() != null) {
//            querry = getArguments().getString("Querry");
//        }


        name = new ArrayList<>();
        shrdesc = new ArrayList<>();
        url = new ArrayList<>();
        price = new ArrayList<>();
        tag = new ArrayList<>();
        categ = new ArrayList<>();
        PID = new ArrayList<>();
        no = new ArrayList<>();
        name1= new ArrayList<>();
        shrdesc1= new ArrayList<>();
        url1= new ArrayList<>();
        price1= new ArrayList<>();
        tag1= new ArrayList<>();
        categ1= new ArrayList<>();
        PID1= new ArrayList<>();
        no1= new ArrayList<>();
        querry = "sdad";
        mGridView = root.findViewById(R.id.searchresult);
        db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if(dataSnapshot1.getKey().equals("Name")){
//                            if(!dataSnapshot1.getValue().equals(querry)){
//                                break;
//                            }
                            name.add(dataSnapshot1.getValue().toString());
                            Log.d("Value2",dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("Images")){
                            url.add(dataSnapshot1.getValue().toString());
                            Log.d("Value1",dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("Price")){
                            price.add(""+dataSnapshot1.getValue().toString());
                            Log.d("Value3",dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("Tags")){
                            tag.add(""+dataSnapshot1.getValue().toString());
                            Log.d("Value4",dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("Short description")){
                            shrdesc.add(""+dataSnapshot1.getValue().toString());
                            Log.d("Value5",dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("ID")){
                            PID.add( Integer.parseInt(dataSnapshot1.getValue().toString()) );
                            Log.d("Value6",dataSnapshot1.getValue().toString());
                        }
                        if(dataSnapshot1.getKey().equals("Categories")){
                            categ.add(dataSnapshot1.getValue().toString());
                            Log.d("Value7",dataSnapshot1.getValue().toString());
                        }
                    }
                }
                filter(querry);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mGridAdapter = new GridAdapter(url1,name1,price1,shrdesc1,PID1,getContext(),getActivity());
        mGridView.setAdapter(mGridAdapter);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
    private void filter(String querry) {
        for(int i=0;i<name.size();i++){
            if(name.get(i).contains(querry)){
                no.add(i);
            }
        }
        putData();

    }

    private void putData() {
        for(int k=0;k<no.size();k++){
            name1.add(name.get(no.get(k)));
            shrdesc1.add(shrdesc.get(no.get(k)));
            tag1.add(tag.get(no.get(k)));
            url1.add(url.get(no.get(k)));
            price1.add(price.get(no.get(k)));
            PID1.add(PID.get(no.get(k)));
            categ1.add(categ.get(no.get(k)));
//            if (h == 0) {
//                for(int l=0;l<categ.size();l++){
//
//                }
//                h++;
//            }
        }
        mGridAdapter.notifyDataSetChanged();

    }

}