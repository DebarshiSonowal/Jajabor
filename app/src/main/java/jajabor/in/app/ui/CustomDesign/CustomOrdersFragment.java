package jajabor.in.app.ui.CustomDesign;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skydoves.elasticviews.ElasticButton;
import com.vlonjatg.progressactivity.ProgressRelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import jajabor.in.app.Helper.OrderObject;
import jajabor.in.app.R;
import jajabor.in.app.ui.Order.CustomOrderStatusAdapter;
import jajabor.in.app.ui.Order.OrderDetailsViewModel;
import jajabor.in.app.ui.Order.OrderStatusAdapter;
import jajabor.in.app.ui.Order.OrderStatusElement;
import jajabor.in.app.ui.Order.OrderViewModel;

public class CustomOrdersFragment extends Fragment implements CustomOrderStatusAdapter.OnItemSelectedListener {

    FirebaseFirestore db;
    EventListener<QuerySnapshot> mListener;
    EventListener<DocumentSnapshot> mEventListener1;
    List<String> ids;
    AlertDialog mDialog;
    LinearLayoutManager layoutManager;
    List<String>status,products,price,temp1,temp,image;
    String Delivery,phone,uid,id,piclist;
    RecyclerView recyclerView;
    CustomOrderStatusAdapter mAdapter;
    OrderViewModel mModel;
    private List<List<String>>colour,size;
    private List<List<Double>> quantity;
    FirebaseUser mUser;
    ProgressRelativeLayout mEmptyView;
    OrderObject mOrderStatusElement;
    ShimmerFrameLayout mShimmerFrameLayout;
    OrderDetailsViewModel mOrderDetailsViewModel;
    Context mContext;
    CustomOrderStatusAdapter.OnItemSelectedListener Listener;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mEmptyView = null;
        recyclerView = null;
        mModel =null;
        layoutManager = null;
        mEventListener1 = null;
        mListener = null;
        db=null;
        mAdapter =null;
        Listener=null;
        mShimmerFrameLayout=null;
        mOrderStatusElement =null;
        mOrderDetailsViewModel=null;
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mContext = getContext();
        if (mUser != null) {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        if (mUser != null) {
            db.document("UserProfile/" + uid).addSnapshotListener(mEventListener1 = new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot.getString("Phone") != null) {
                        phone = documentSnapshot.getString("Phone");
                        Log.d("ASA2", documentSnapshot.getString("Phone"));
                    }
                    if (documentSnapshot.getString("Address") != null) {
                        Delivery = documentSnapshot.getString("Address");
                    }
                }
            });
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(OrderViewModel.class);
        mOrderDetailsViewModel = new ViewModelProvider(getActivity()).get(OrderDetailsViewModel.class);
        mModel.getOrderlist().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer > 0){
                    Log.d("HESOYAM",integer+"");
                    mShimmerFrameLayout.stopShimmer();
                    mShimmerFrameLayout.setVisibility(View.GONE);
                }else {
                    mShimmerFrameLayout.setVisibility(View.VISIBLE);
                    mShimmerFrameLayout.startShimmer();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_orders, container, false);
        mEmptyView = view.findViewById(R.id.loadingLayout1);
        ids = new ArrayList<>();
        products = new ArrayList<>();
        status= new ArrayList<>();
        price= new ArrayList<>();
        temp= new ArrayList<>();
        colour= new ArrayList<>();
        size = new ArrayList<>();
        quantity = new ArrayList<>();
        temp1= new ArrayList<>();
        image = new ArrayList<>();
        Listener =  this;
        mContext = getContext();
        recyclerView = view.findViewById(R.id.customorderlist);
        mShimmerFrameLayout = view.findViewById(R.id.shimmerLayoutorder1);
        if(uid == null){
            mEmptyView.showEmpty(R.drawable.ic_registro,"You haven't signed in","Please Login or Signup");
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            mEmptyView.showContent();
            mDialog =  new SpotsDialog.Builder()
                    .setContext(mContext)
                    .setTheme(R.style.Custom)
                    .setMessage("Please Wait")
                    .setCancelable(false)
                    .build();
            mDialog.show();
            mShimmerFrameLayout.showShimmer(true);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mUser != null) {
            db = FirebaseFirestore.getInstance();
            db.collection("Special Order").whereEqualTo("UID", FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(mListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot1:queryDocumentSnapshots){
                        id = queryDocumentSnapshot1.getId();
                        Log.d("RetrievedIDs",queryDocumentSnapshot1.getId());
                        DocumentReference docRef = db.collection("Special Order").document(queryDocumentSnapshot1.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        ids.add(queryDocumentSnapshot1.getId());
                                        Log.d("TAG ME", "DocumentSnapshot data: " + document.getData());
                                        price.add(document.getLong("Payment").toString());
                                        colour.add((List<String>) document.get("Colour"));
                                        size.add((List<String>) document.get("Size"));
                                        quantity.add((List<Double>) document.get("Quantity"));
                                        Log.d("TAG ME", "PRICE: " + document.getLong("Payment"));
                                        status.add(document.getString("Status"));
                                        Log.d("TAG ME", "STATUS: " + document.getString("Status"));
                                        products.add(document.get("Products").toString());
                                        Log.d("TAG ME", "Products: " + document.get("Products").toString());
                                        Map<String, Object> map = document.getData();
                                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                                            if (entry.getKey().equals("Picture")) {
                                                Log.d("TAG", entry.getValue().toString().substring(entry.getValue().toString().indexOf("[")+1,entry.getValue().toString().indexOf("]"))+" pic");
                                                piclist = entry.getValue().toString().substring(entry.getValue().toString().indexOf("[")+1,entry.getValue().toString().indexOf("]"));
                                                image.add(piclist);
                                            }
                                        }
                                        if (ids.size() == price.size()) {
                                            mOrderStatusElement = new OrderObject(image,ids, colour.get(0), price, status);
                                            mAdapter = new CustomOrderStatusAdapter(mContext, mOrderStatusElement);
                                            recyclerView.setAdapter(mAdapter);
                                            mModel.setOrderlist(mAdapter.getItemCount());
                                            mAdapter.setOnItemSelectedListener(Listener);
                                            layoutManager = new LinearLayoutManager(mContext);
                                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                            recyclerView.setLayoutManager(layoutManager);
                                            try {
                                                mDialog.dismiss();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    mEmptyView.showContent();
                }
            });
            if(ids.isEmpty()){
                mEmptyView.showEmpty(R.drawable.ic_box,"No items in the cart","Add items to the cart");
                try {
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                mEmptyView.showContent();
            }

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        ids.clear();
        price.clear();
        status.clear();
        products.clear();
        image.clear();

    }

    @Override
    public void onItemSelected(int position) {
        Log.d("IHAVECLICKER","NOT ADAPTER");
        ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(quantity.get(position).toString().substring(quantity.get(position).toString().indexOf("[")+1,quantity.get(position).toString().indexOf("]")).split(",")));
        mOrderDetailsViewModel.setColor(colour.get(0).get(0));
        mOrderDetailsViewModel.setPic(image.get(position));
        mOrderDetailsViewModel.setId(ids.get(position));
        mOrderDetailsViewModel.setPrice(price.get(position));
        mOrderDetailsViewModel.setQuantity(Integer.parseInt(elephantList.get(0)));
        mOrderDetailsViewModel.setSize(size.get(0).get(0));
        Log.d("IHAVECLICKER",mOrderDetailsViewModel.getColor().getValue()+"");
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_CustomOrderDetailsFragment);
    }
}