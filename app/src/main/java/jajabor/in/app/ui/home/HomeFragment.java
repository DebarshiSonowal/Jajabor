package jajabor.in.app.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jajabor.in.app.AdapterSlider;
import jajabor.in.app.PicassoImageLoadingService;
import jajabor.in.app.ProductAdapter;
import jajabor.in.app.R;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {
Slider slider;
List<String>url;
List<String>name;
List<String>price;
DatabaseReference mFirebaseDatabase;
ProductAdapter mAdapter,mAdapter1,mAdapter2;

    @Override
    public void onStart() {
        super.onStart();
    Networkk work = new Networkk();
    work.execute();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        url = new ArrayList<>();
        price = new ArrayList<>();
        name = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Axomiya-mur-prothom-porichoy-Unisex-womens-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Akolxoriya-Unisex-Womens-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Sangeetei-mur-jibonMusic-is-my-Life-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/01/Dusto-Lora-524x658.png");


//        name.add("Axomiya Mur Prothom Porichoy Assamese Tshirt");
//        name.add("Akolxoriya Assamese Tshirt");
//        name.add("Sangeetei mur Jibon Assamese Tshirt");
//        name.add("Dusto Lora Assamese Tshirt");


//        price.add("₹299.00");
//        price.add("₹299.00");
//        price.add("₹299.00");
//        price.add("₹299.00");


        Slider.init(new PicassoImageLoadingService(getContext()));
        slider = root.findViewById(R.id.banner_slider1);
        slider.setAdapter(new AdapterSlider());
        slider.setSelectedSlide(1);
        mAdapter = new ProductAdapter(url,name,price,getContext());
        mAdapter1 =  new ProductAdapter(url,name,price,getContext());
        mAdapter2 =  new ProductAdapter(url,name,price,getContext());
        final RecyclerView mRecyclerView= root.findViewById(R.id.flashsale);
        final RecyclerView mRecyclerView1= root.findViewById(R.id.bihusale);
        final RecyclerView mRecyclerView2= root.findViewById(R.id.allproducts);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView1.setLayoutManager(layoutManager1);
        mRecyclerView2.setLayoutManager(layoutManager2);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView1.setAdapter(mAdapter1);
        mRecyclerView2.setAdapter(mAdapter2);

        return root;
    }
    class Networkk extends AsyncTask<String,Integer,String >{

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
        }

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
                                price.add(dataSnapshot1.getValue().toString());
                                Log.d("Value",dataSnapshot1.getValue().toString());
                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    mAdapter1.notifyDataSetChanged();
                    mAdapter2.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }
    }
}