package jajabor.in.app.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jajabor.in.app.AdapterSlider;
import jajabor.in.app.BusStation;
import jajabor.in.app.CategoryAdapter;
import jajabor.in.app.GridAdapter;
import jajabor.in.app.PicassoImageLoadingService;
import jajabor.in.app.ProductAdapter;
import jajabor.in.app.R;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {
Slider slider;
List<String>url;
    List<String>url1;
List<String>name;
    List<String>name1;
List<String>price;
    List<String>tag;
    List<String>shrdesc;
List<Integer>PID;
List<Integer>Valentines;
    Networkk work;
ValueEventListener mValueEventListener;
ImageView mImageView,mImageView2,offer;
    CategoryAdapter mGridAdapter;
DatabaseReference mFirebaseDatabase;
ProductAdapter mAdapter,mAdapter1,mAdapter2;
GridView mGridView;
    @Override
    public void onStart() {
        super.onStart();
   work = new Networkk();
    work.execute();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFirebaseDatabase.removeEventListener(mValueEventListener);
        mValueEventListener = null;
        mGridView = null;
        mImageView = null;
        mImageView2 = null;
        offer = null;
        slider = null;
        mAdapter = null;
        mAdapter1 = null;
        mAdapter2 = null;
        mGridAdapter = null;
        work.cancel(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        url = new ArrayList<>();
        price = new ArrayList<>();
        name = new ArrayList<>();
        url1 = new ArrayList<>();
        name1 = new ArrayList<>();
        tag = new ArrayList<>();
        shrdesc = new ArrayList<>();
        PID = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mGridView = root.findViewById(R.id.TOPWEAR);
        mImageView = root.findViewById(R.id.imageView3);
        mImageView2 = root.findViewById(R.id.imageView6);
        offer = root.findViewById(R.id.imageView15);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-02.jpg?alt=media&token=f59a2969-34d4-4654-88b6-87a0ce2040e6").resize(1240,1240).into(mImageView);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-01.jpg?alt=media&token=aa67bdfd-4ea8-4637-8b2c-6f7457691c8c").resize(1240,1240).into(mImageView2);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-03.jpg?alt=media&token=36d7a59b-493a-4458-afac-7aad0c4801df").resize(1240,1240).into(offer);
        //        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Axomiya-mur-prothom-porichoy-Unisex-womens-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Akolxoriya-Unisex-Womens-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Sangeetei-mur-jibonMusic-is-my-Life-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/01/Dusto-Lora-524x658.png"
        

//        name.add("Axomiya Mur Prothom Porichoy Assamese Tshirt");
//        name.add("Akolxoriya Assamese Tshirt");
//        name.add("Sangeetei mur Jibon Assamese Tshirt");
//        name.add("Dusto Lora Assamese Tshirt");


//        price.add("₹299.00");
//        price.add("₹299.00");
//        price.add("₹299.00");
//        price.add("₹299.00");
        url1.add("https://images.bewakoof.com/uploads/grid/app/Men-s-Colorblock-1591361054.png");url1.add("https://images.bewakoof.com/uploads/grid/app/Men-s-Colorblock-1591361054.png");url1.add("https://images.bewakoof.com/uploads/grid/app/Men-s-Colorblock-1591361054.png");
        url1.add("https://images.bewakoof.com/uploads/grid/app/Women-s-34th-Sleeves--1--1591788297.png");url1.add("https://images.bewakoof.com/uploads/grid/app/Women-s-Crop-Tops--1--1591788298.png");url1.add("https://images.bewakoof.com/uploads/grid/app/Women-s-Crop-Tops--1--1591788298.png");
        name1.add("Mens Colorblock");   name1.add("Mens Colorblock");   name1.add("Men-s-Colorblock");
        name1.add("Womens 34th Sleeves");   name1.add("Men-s-Colorblock");   name1.add("Men-s-Colorblock");


        Slider.init(new PicassoImageLoadingService(getContext()));
        slider = root.findViewById(R.id.banner_slider1);
        slider.setAdapter(new AdapterSlider());
        slider.setSelectedSlide(1);
        mAdapter = new ProductAdapter(url,name,price,shrdesc,PID,getContext(),getActivity());
        mAdapter1 =  new ProductAdapter(url,name,price,shrdesc,PID,getContext(),getActivity());
        mAdapter2 =  new ProductAdapter(url,name,price,shrdesc,PID,getContext(),getActivity());
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
        mGridAdapter = new CategoryAdapter(url1,name1,getContext());
        mGridView.setAdapter(mGridAdapter);

        return root;
    }
    class Networkk extends AsyncTask<String,Integer,String >{

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
        }

        @Override
        protected String doInBackground(String... strings) {
            mFirebaseDatabase.addValueEventListener(mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            if(dataSnapshot1.getKey().equals("Images")){
                                url.add(dataSnapshot1.getValue().toString());
                                Log.d("Value1",dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().equals("Name")){
                                name.add(dataSnapshot1.getValue().toString());
                                Log.d("Value2",dataSnapshot1.getValue().toString());
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
                                Log.d("Value5",dataSnapshot1.getValue().toString());
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

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}