package jajabor.in.app.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jajabor.in.app.AdapterSlider;
import jajabor.in.app.AdapterSlider2;
import jajabor.in.app.CategAccessAdapter;
import jajabor.in.app.CategBannerAdapter;
import jajabor.in.app.CategoryAdapter;
import jajabor.in.app.PicassoImageLoadingService;
import jajabor.in.app.ProductAdapter;
import jajabor.in.app.R;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {
Slider slider,slider1;
List<String>url,url1,name,name1,price,tag,shrdesc;
List<String>bihuurl,bihuname,bihuprice,categ,bihushrdesc;
List<String>bannerpic,urlaccessory;
List<String>coupleurl,couplename,coupleprice,coupletagg,coupledesc;
List<String>bannername;
List<Integer>PID,no,no1,bihuPID,couplePID;
List<Integer>Valentines;
    Networkk work;
ValueEventListener mValueEventListener;
ImageView mImageView,mImageView2,offer;
CategoryAdapter topwearadater;
CategAccessAdapter accessoriadapter;
DatabaseReference mFirebaseDatabase;
ProductAdapter mAdapter,mAdapter1,mAdapter2;
CategBannerAdapter mCategBannerAdapter;
GridView topweargrid,accessorygrid;
    @Override
    public void onStart() {
        super.onStart();
   work = new Networkk();
    work.execute();
//https://images.bewakoof.com/uploads/grid/app/bewakoof-mobille-cover-parade-1594908843.jpg
        //https://images.bewakoof.com/uploads/grid/app/collab-banner-v-2-1593079023.jpg
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFirebaseDatabase.removeEventListener(mValueEventListener);
        mValueEventListener = null;
        mCategBannerAdapter = null;
        topweargrid = null;
        mImageView = null;
        mImageView2 = null;
        offer = null;
        slider = null;
        slider1 = null;
        mAdapter = null;
        mAdapter1 = null;
        mAdapter2 = null;
        topwearadater = null;
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
        no1 = new ArrayList<>();
        tag = new ArrayList<>();
        shrdesc = new ArrayList<>();
        PID = new ArrayList<>();
        bannerpic = new ArrayList<>();
        bannername = new ArrayList<>();
        bihuname = new ArrayList<>();
        bihuprice= new ArrayList<>();
        bihuurl= new ArrayList<>();
        coupledesc = new ArrayList<>();
        couplename = new ArrayList<>();
        coupleprice = new ArrayList<>();
        coupletagg =new ArrayList<>();
        coupleurl = new ArrayList<>();
        categ = new ArrayList<>();
        bihushrdesc = new ArrayList<>();
        bihuPID = new ArrayList<>();
        couplePID = new ArrayList<>();
        urlaccessory = new ArrayList<>();
        urlaccessory.add("https://images.bewakoof.com/uploads/grid/app/bewakoof-mobille-cover-parade-1594908843.jpg");
        urlaccessory.add("https://images.bewakoof.com/uploads/grid/app/collab-banner-v-2-1593079023.jpg");
        no=new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        topweargrid = root.findViewById(R.id.TOPWEAR);
        mImageView = root.findViewById(R.id.imageView3);
        mImageView2 = root.findViewById(R.id.imageView6);
        offer = root.findViewById(R.id.imageView15);
        accessorygrid = root.findViewById(R.id.ACCESSORIES);
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-02.jpg?alt=media&token=f59a2969-34d4-4654-88b6-87a0ce2040e6").resize(1240,1240).into(mImageView);
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-01.jpg?alt=media&token=aa67bdfd-4ea8-4637-8b2c-6f7457691c8c").resize(1240,1240).into(mImageView2);
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-03.jpg?alt=media&token=36d7a59b-493a-4458-afac-7aad0c4801df").resize(1240,1240).into(offer);

        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-03.jpg?alt=media&token=36d7a59b-493a-4458-afac-7aad0c4801df").into(offer);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-02.jpg?alt=media&token=f59a2969-34d4-4654-88b6-87a0ce2040e6").into(mImageView);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-01.jpg?alt=media&token=aa67bdfd-4ea8-4637-8b2c-6f7457691c8c").into(mImageView2);
        //        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Axomiya-mur-prothom-porichoy-Unisex-womens-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Akolxoriya-Unisex-Womens-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Sangeetei-mur-jibonMusic-is-my-Life-Assamese-Tshirt-524x658.jpg");
//        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/01/Dusto-Lora-524x658.png"
        
        bannerpic.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/01/Dusto-Lora-524x658.png");
        bannerpic.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Axomiya-mur-prothom-porichoy-Unisex-womens-Assamese-Tshirt-524x658.jpg");
        bannerpic.add("https://images.bewakoof.com/t540/2-layer-premium-protective-masks-pack-of-10-navy-blue-premium-protective-mask--combo-of-10-274025-1590491487.jpg");
        bannerpic.add("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banneroffer.jpg?alt=media&token=4540fe7a-230e-4308-b490-22a5f940e7bd");
        bannername.add("Men");
        bannername.add("Women");
        bannername.add("Mask");
        bannername.add("Offer");
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
        slider1 = root.findViewById(R.id.banner_slider2);
        slider1.setAdapter(new AdapterSlider2());
        slider1.setSelectedSlide(1);
        //Adapter
        mAdapter = new ProductAdapter(coupleurl,couplename,coupleprice,coupledesc,couplePID,getContext(),getActivity());
        mAdapter1 =  new ProductAdapter(bihuurl,bihuname,bihuprice,bihushrdesc,bihuPID,getContext(),getActivity());
        mAdapter2 =  new ProductAdapter(url,name,price,shrdesc,PID,getContext(),getActivity());
        mCategBannerAdapter = new CategBannerAdapter(bannerpic,bannername,getContext());
        accessoriadapter = new CategAccessAdapter(urlaccessory,getContext(),getActivity());
        topwearadater = new CategoryAdapter(url1,name1,getContext());

        //Recyclerview
        final RecyclerView mRecyclerView= root.findViewById(R.id.couplesale);
        final RecyclerView mRecyclerView1= root.findViewById(R.id.bihusale);
        final RecyclerView mRecyclerView2= root.findViewById(R.id.allproducts);
        final RecyclerView CategBanner = root.findViewById(R.id.categorybanner);

        //Layoutmanager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext());

        //Set orientation
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView1.setLayoutManager(layoutManager1);
        mRecyclerView2.setLayoutManager(layoutManager2);
        CategBanner.setLayoutManager(layoutManager3);

        //Set adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView1.setAdapter(mAdapter1);
        mRecyclerView2.setAdapter(mAdapter2);
        CategBanner.setAdapter(mCategBannerAdapter);
        topweargrid.setAdapter(topwearadater);
        accessorygrid.setAdapter(accessoriadapter);
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
                                Log.d("Value6",dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().equals("Categories")){
                                categ.add(dataSnapshot1.getValue().toString());
                                Log.d("Value7",dataSnapshot1.getValue().toString());
                            }
                        }
                    }
                    filterbihu();
                    filtercouple();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }
    }

    private void filtercouple() {
        for(int i=0;i<tag.size();i++){
            for(String myStr: tag.get(i).split("[,]", 0)) {
                if(myStr.toLowerCase().trim().equals("couples")){
                    Log.d("Valuep",myStr.toLowerCase().trim());
                    no1.add(i);
                    Log.d("ValueQ",i+"");
                }
            }
        }
        for(int k=0;k<no1.size();k++)
        {
            coupleurl.add(url.get(no1.get(k)));
                    Log.d("ValueW",url.get(no1.get(k)));
            couplename.add(name.get(no1.get(k)));
                    Log.d("ValueE",name.get(no1.get(k)));
            coupleprice.add(price.get(no1.get(k)));
                    Log.d("ValueR",price.get(no1.get(k)));
            coupledesc.add(shrdesc.get(no1.get(k)));
            couplePID.add(PID.get(no1.get(k)));
        }
        mAdapter.notifyDataSetChanged();
        mAdapter2.notifyDataSetChanged();
        mAdapter1.notifyDataSetChanged();
    }

    private void filterbihu() {
//        Log.d("ValueQ","Start");
        for (int i=0;i<categ.size();i++){
//            Log.d("Valueasdadas",i+"");
//            Log.d("Value", Arrays.toString(categ.get(i).split("[,]", 0)));
            for(String myStr: categ.get(i).split("[,]", 0)) {
                if(myStr.toLowerCase().trim().equals("bihu special")){
//                    Log.d("Valuep",myStr.toLowerCase().trim());
                    no.add(i);
//                    Log.d("ValueQ",i+"");
                }
            }
        }
        for(int k=0;k<no.size();k++){
            Log.d("Test",  k+" - "+no.get(k)+"");
//            for(int l=0;l<no.size();l++){
//                if(k==no.get(l)){
                    bihuurl.add(url.get(no.get(k)));
//                    Log.d("ValueW",bihuurl.get(no.get(k)));
                    bihuname.add(name.get(no.get(k)));
//                    Log.d("ValueE",bihuname.get(no.get(k)));
                    bihuprice.add(price.get(no.get(k)));
//                    Log.d("ValueR",bihuprice.get(no.get(k)));
                    bihushrdesc.add(shrdesc.get(no.get(k)));
                    bihuPID.add(PID.get(no.get(k)));
//                }
//            }
        }
        mAdapter.notifyDataSetChanged();
        mAdapter2.notifyDataSetChanged();
        mAdapter1.notifyDataSetChanged();
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