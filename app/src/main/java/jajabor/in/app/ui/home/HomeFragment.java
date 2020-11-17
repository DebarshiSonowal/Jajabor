package jajabor.in.app.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jajabor.in.app.Adapters.AdapterSlider;
import jajabor.in.app.Adapters.AdapterSlider2;
import jajabor.in.app.Adapters.CategAccessAdapter;
import jajabor.in.app.Adapters.CategBannerAdapter;
import jajabor.in.app.Adapters.CategoryAdapter;
import jajabor.in.app.Service.GlideImageLoadingService;
import jajabor.in.app.Adapters.ProductAdapter;
import jajabor.in.app.R;
import jajabor.in.app.ui.SearchResult.SharedViewModel;
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
RecyclerView CategBanner,mRecyclerView2,mRecyclerView1,mRecyclerView;
    Networkk work;
    Activity mActivity;
    String sa;
    SharedViewModel viewModel;
    Context mContext;
ValueEventListener mValueEventListener;
ImageView mImageView,mImageView2,offer;
CategoryAdapter topwearadater;
CategAccessAdapter accessoriadapter;
DatabaseReference mFirebaseDatabase;
ProductAdapter mAdapter,mAdapter1,mAdapter2;
CategBannerAdapter mCategBannerAdapter;
GridView topweargrid,accessorygrid;
FirebaseFirestore db;
ShimmerFrameLayout Cshimmer,Bshimmer,Ashimmer;
HomeShimmeringViewModel mModel;
  LinearLayoutManager layoutManager;
 LinearLayoutManager layoutManager1;
LinearLayoutManager layoutManager2;
   LinearLayoutManager layoutManager3;
    GlideImageLoadingService  mLoadingService;
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
        mFirebaseDatabase = null;
        mCategBannerAdapter = null;
        topweargrid = null;
        accessorygrid =null;
        mImageView = null;
        mImageView2 = null;
        offer = null;
        slider = null;
        slider1 = null;
        mAdapter = null;
        mAdapter1 = null;
        mAdapter2 = null;
        topwearadater = null;
        layoutManager = null;
        layoutManager1 = null;
        layoutManager2 = null;
        layoutManager3 = null;
        Cshimmer =null;
        Bshimmer= null;
        Ashimmer = null;
        mModel = null;
        viewModel=null;
        work.cancel(true);
        work = null;
        mLoadingService = null;
        mContext = null;
        CategBanner=null;
        mRecyclerView2=null;
        mRecyclerView1=null;
        mRecyclerView=null;
        accessoriadapter=null;
        System.gc();
    }

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Cshimmer = root.findViewById(R.id.shimmerLayoutcouple);
        Cshimmer.showShimmer(true);
        Bshimmer = root.findViewById(R.id.shimmerLayoutbihu);
        Bshimmer.showShimmer(true);
        Ashimmer = root.findViewById(R.id.shimmerLayoutall);
        Ashimmer.showShimmer(true);
        mContext = getContext();
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
        mActivity = getActivity();
        try {
            Log.d("Provider IS",FirebaseAuth.getInstance().getCurrentUser().getIdToken(false).getResult().getSignInProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(FirebaseAuth.getInstance().getCurrentUser().getIdToken(false).getResult().getSignInProvider().equals("phone")){
                Log.d("QWERTYU","22323");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-02.jpg?alt=media&token=f59a2969-34d4-4654-88b6-87a0ce2040e6").resize(1240,1240).into(mImageView);
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-01.jpg?alt=media&token=aa67bdfd-4ea8-4637-8b2c-6f7457691c8c").resize(1240,1240).into(mImageView2);
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-03.jpg?alt=media&token=36d7a59b-493a-4458-afac-7aad0c4801df").resize(1240,1240).into(offer);

        Glide.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-03.jpg?alt=media&token=36d7a59b-493a-4458-afac-7aad0c4801df").into(offer);
        Glide.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-02.jpg?alt=media&token=f59a2969-34d4-4654-88b6-87a0ce2040e6").into(mImageView);
        Glide.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-01.jpg?alt=media&token=aa67bdfd-4ea8-4637-8b2c-6f7457691c8c").into(mImageView2);
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


        Slider.init( mLoadingService=new GlideImageLoadingService(mContext));
        slider = root.findViewById(R.id.banner_slider1);
        slider.setAdapter(new AdapterSlider());
        slider.setSelectedSlide(1);
        slider1 = root.findViewById(R.id.banner_slider2);
        slider1.setAdapter(new AdapterSlider2());
        slider1.setSelectedSlide(1);
        //Adapter
        mAdapter = new ProductAdapter(coupleurl,couplename,coupleprice,coupledesc,couplePID,mContext,mActivity);
        mAdapter1 =  new ProductAdapter(bihuurl,bihuname,bihuprice,bihushrdesc,bihuPID,mContext,mActivity);
        mAdapter2 =  new ProductAdapter(url,name,price,shrdesc,PID,mContext,mActivity);
        mCategBannerAdapter = new CategBannerAdapter(bannerpic,bannername,mActivity);
        accessoriadapter = new CategAccessAdapter(urlaccessory,mContext,mActivity);
        topwearadater = new CategoryAdapter(url1,name1,mContext);

        //Recyclerview
        mRecyclerView= root.findViewById(R.id.couplesale);
        mRecyclerView1= root.findViewById(R.id.bihusale);
        mRecyclerView2= root.findViewById(R.id.allproducts);
        CategBanner = root.findViewById(R.id.categorybanner);

        //Layoutmanager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager2 = new LinearLayoutManager(mContext);
        layoutManager3 = new LinearLayoutManager(mContext);

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

        mCategBannerAdapter.setOnItemClickListener(new CategBannerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mCategBannerAdapter.getname().get(position).equals("Men")) {
                    Toast.makeText(mContext,"Coming Soon",Toast.LENGTH_SHORT).show();
                    Log.d("Coming","MEn");
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_men);
                }else if(mCategBannerAdapter.getname().get(position).equals("Women")){
                    Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_women);
                }else
                    Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        try {
            if(sa.equals("Phone")){
                new MaterialDialog.Builder(container.getContext())
                        .title("We require an Email")
                        .cancelable(false)
                        .content("Please enter an Email")
                        .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .input("abc@gmail.com", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Map<String, Object> note = new HashMap<>();
                                note.put("Email",note);
                                // Do something
                                db = FirebaseFirestore.getInstance();
                                db.collection("UserProfile").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        viewModel.getType().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sa =s;
                Log.d("String",s);
            }
        });
        mModel = new ViewModelProvider(getActivity()).get(HomeShimmeringViewModel.class);
        mModel.getCouplesize().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 0) {
                    Cshimmer.stopShimmer();
                    Cshimmer.setVisibility(View.GONE);
                } else {
                    Cshimmer.setVisibility(View.VISIBLE);
                    Cshimmer.startShimmer();
                }


            }
        });
        mModel.getAllsize().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 0) {
                    Ashimmer.stopShimmer();
                    Ashimmer.setVisibility(View.GONE);
                } else {
                    Ashimmer.setVisibility(View.VISIBLE);
                    Ashimmer.startShimmer();
                }
            }
        });
        mModel.getBihusize().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 0) {
                    Bshimmer.stopShimmer();
                    Bshimmer.setVisibility(View.GONE);
                } else {
                    Bshimmer.setVisibility(View.VISIBLE);
                    Bshimmer.startShimmer();
                }
            }
        });
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
        mModel.setCouplesize(coupleurl.size());
        mModel.setAllsize(url.size());
        mModel.setBihusize(bihuurl.size());
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
        bihuurl.clear(); bihuname.clear();bihuprice.clear();bihushrdesc.clear();
        bihuPID.clear();coupleurl.clear();couplename.clear();coupleprice.clear();
        couplePID.clear();coupledesc.clear();url.clear();name.clear();PID.clear();
        shrdesc.clear();price.clear();tag.clear();categ.clear();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}