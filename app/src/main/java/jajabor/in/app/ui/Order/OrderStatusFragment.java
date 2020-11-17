package jajabor.in.app.ui.Order;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import jajabor.in.app.R;


public class OrderStatusFragment extends Fragment implements OrderStatusAdapter.OnItemSelectedClickListener {

    OrderStatusAdapter.OnItemSelectedClickListener listener;
    FirebaseFirestore db;
    OrderDetailsViewModel mViewModel;
    EventListener<QuerySnapshot> mListener;
    EventListener<DocumentSnapshot> mEventListener,mEventListener1;
    List<String> ids;
    AlertDialog mDialog;
    private List<List<Double>> quantity;
    LinearLayoutManager layoutManager;
    List<String>status,products,price,temp1,temp,image;
    String Delivery,email,phone,uid,id,piclist;
    RecyclerView recyclerView;
    OrderStatusAdapter mAdapter;
    private List<List<String>>colour,size;
    Boolean first= true;
    OrderViewModel mModel;
    FirebaseUser mUser;
    ProgressRelativeLayout mEmptyView;
    OrderStatusElement mOrderStatusElement;
    ShimmerFrameLayout mShimmerFrameLayout;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mEmptyView = null;
        mShimmerFrameLayout=null;
        mViewModel = null;
        mModel = null;
        mEventListener=null;
        mEventListener1=null;
        mOrderStatusElement=null;
        mListener=null;
        db = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

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
        mModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);
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
        mViewModel = new ViewModelProvider(getActivity()).get(OrderDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
        mEmptyView = view.findViewById(R.id.loadingLayout);
        ids = new ArrayList<>();
        products = new ArrayList<>();
        status= new ArrayList<>();
        price= new ArrayList<>();
        temp= new ArrayList<>();
        temp1= new ArrayList<>();
        colour= new ArrayList<>();
        size = new ArrayList<>();
        quantity = new ArrayList<>();
        image = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        listener = this;
        recyclerView = view.findViewById(R.id.orderlist);
        mShimmerFrameLayout = view.findViewById(R.id.shimmerLayoutorder);
//        layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
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
                    .setContext(getContext())
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
            db.collection("Order").whereEqualTo("UID", FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(mListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot1:queryDocumentSnapshots){
                        id = queryDocumentSnapshot1.getId();
                        DocumentReference docRef = db.collection("Order").document(queryDocumentSnapshot1.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d("RetrievedIDs",ids.toString());
                                        ids.add(queryDocumentSnapshot1.getId());
                                        colour.add((List<String>) document.get("Colour"));
                                        size.add((List<String>) document.get("Size"));
                                        quantity.add((List<Double>) document.get("Quantity"));
                                        Log.d("TAG ME", "DocumentSnapshot data: " + document.getData());
                                        price.add(document.getLong("Payment").toString());
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
                                            mOrderStatusElement = new OrderStatusElement(ids,status,price,image,products,getContext());
                                            mAdapter = new OrderStatusAdapter(mOrderStatusElement,phone,Delivery);
                                            recyclerView.setAdapter(mAdapter);
                                            mAdapter.OnItemSelectedClickListener(listener);
                                            mModel.setOrderlist(mAdapter.getItemCount());
                                            layoutManager = new LinearLayoutManager(getContext());
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

//                        if (ids.size() == image.size() & ids.size() == price.size() & ids.size() == products.size() ) {

//                        }
                    }
//                    mAdapter = new OrderStatusAdapter(ids,status,phone,Delivery,price,products,image,getContext());
//                    layoutManager = new LinearLayoutManager(getContext());
//                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setAdapter(mAdapter);
//                    mAdapter.notifyDataSetChanged();
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
    public void onItemSelectedClick(int position) {
        Log.d("IHAVECLICKER","NOT ADAPTER");
        ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(quantity.get(position).toString().substring(quantity.get(position).toString().indexOf("[")+1,quantity.get(position).toString().indexOf("]")).split(",")));
       mViewModel.setColor(colour.get(0).get(0));
        mViewModel.setPic(image.get(position));
        mViewModel.setId(ids.get(position));
        mViewModel.setPrice(price.get(position));
        mViewModel.setQuantity(Integer.parseInt(elephantList.get(0)));
        mViewModel.setSize(size.get(0).get(0));
        Log.d("IHAVECLICKER",mViewModel.getColor().getValue()+"");
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_orderDetailsFragment);
    }


}