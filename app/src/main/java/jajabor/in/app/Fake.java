package jajabor.in.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vlonjatg.progressactivity.ProgressRelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jajabor.in.app.ui.Order.OrderStatusAdapter;


public class Fake extends Fragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View root = inflater.inflate(R.layout.fragment_fake, container, false);
//        return root;
//    }

    FirebaseFirestore db;
    EventListener<QuerySnapshot> mListener;
    EventListener<DocumentSnapshot> mEventListener,mEventListener1;
    List<String> ids;
    LinearLayoutManager layoutManager;
    List<String>status;
    String Delivery,email;
    List<String>products;
    String phone,uid;
    List<String>price;
    String piclist;
    List<String>temp1,temp;
    RecyclerView recyclerView;
    OrderStatusAdapter mAdapter;
    Boolean first= true;
    FirebaseUser mUser;
    ProgressRelativeLayout mEmptyView;
    @Override
    public void onStart() {
        super.onStart();
        if (mUser != null) {
            Log.d("IDis1", FirebaseAuth.getInstance().getCurrentUser().getUid());
            db.collection("Order").whereEqualTo("UID", FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(mListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    ids.clear();
                    for(QueryDocumentSnapshot queryDocumentSnapshot1:queryDocumentSnapshots){
                        ids.add(queryDocumentSnapshot1.getId());
                        Log.d("Retrieved1",queryDocumentSnapshot1.getId());
                        getothers(queryDocumentSnapshot1);
                    }
                    mAdapter.notifyDataSetChanged();
                    layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                }
            });
            if(ids.isEmpty()){
                mEmptyView.showEmpty(R.drawable.ic_box,"No items in the cart","Add items to the cart");
            }else {
                mEmptyView.showContent();
            }
        }else{
            Log.d("IDis1","Non inside");
        }

    }

    private void gettheorders(int intValue) {

    }

    private void getothers(QueryDocumentSnapshot queryDocumentSnapshot1) {
        if (mUser != null) {
            db.document("Order/"+queryDocumentSnapshot1.getId()).addSnapshotListener(mEventListener = new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot.exists()) {
//                        Log.d("TAG ME", documentSnapshot.toString());
                        price.clear();
                        products.clear();
                        status.clear();
                        price.add(documentSnapshot.getLong("Payment").toString());
                        status.add(documentSnapshot.getString("Status"));
                        products.add(documentSnapshot.get("Products").toString());
                        Log.d("Retrieved2",documentSnapshot.getLong("Payment").toString());
                        Log.d("TAG", "Got Inside");
                        Map<String, Object> map = documentSnapshot.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
//                                Log.d("TAG", "Got Inside2"+entry.getValue());
                            if (entry.getKey().equals("Picture")) {
                                Log.d("TAG", entry.getValue().toString().substring(entry.getValue().toString().indexOf("[")+1,entry.getValue().toString().indexOf("]"))+" pic");
                                piclist = entry.getValue().toString().substring(entry.getValue().toString().indexOf("[")+1,entry.getValue().toString().indexOf("]"));

//                                    mAdapter.notifyDataSetChanged();
                            }
                        }

                        Log.d("Retrieved3",documentSnapshot.getString("Status"));
                        Log.d("Retrieved4",documentSnapshot.get("Products").toString());
//                        mAdapter.notifyDataSetChanged();
//                        layoutManager = new LinearLayoutManager(getContext());
//                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                        recyclerView.setLayoutManager(layoutManager);
//                        recyclerView.setAdapter( mAdapter = new OrderStatusAdapter(ids,status,phone,Delivery,price,products,piclist,getContext()));
                        layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        mEmptyView.showContent();
                    } else {
//                        mEmptyView.showEmpty(R.drawable.ic_box,"No items in the cart","Add items to the cart");
                    }
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
            db.document("UserProfile/"+uid).addSnapshotListener(mEventListener1 = new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.getString("Phone") != null){
                        phone= documentSnapshot.getString("Phone");
                        Log.d("ASA2",documentSnapshot.getString("Phone"));
                    }
                    if (documentSnapshot.getString("Address") != null) {
                        Delivery=documentSnapshot.getString("Address");
                    }


//                    try {
//                        piclist = (List<String>) documentSnapshot.get("Picture");
//                        Log.d("MYPIC",piclist.get(0));
//                    } catch (Exception ex) {
//                        Log.d("MYPIC",ex.getMessage());
//                    }
                    try {
                        gettheorders(documentSnapshot.getLong("No. Order").intValue());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Log.d("ASA",Delivery+"");
                    if (first) {
                        setadapter1(Delivery);
                    }
                }
            });
        }
    }

    private void setadapter1(String delivery) {
//        recyclerView.setAdapter( mAdapter = new OrderStatusAdapter(ids,status,phone,delivery,price,products,piclist,getContext()));
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("ASA4",piclist+"");
        first = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUser != null) {
            if (Delivery!=null) {
                setadapter1(Delivery);
            } else {
                db.document("UserProfile/"+uid).addSnapshotListener(mEventListener1 = new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot.get("Phone") != null){
                            phone= documentSnapshot.getString("Phone");
                            Log.d("ASA2",documentSnapshot.getString("Phone"));
                        }
                        if (documentSnapshot.getString("Address") != null) {
                            Delivery=documentSnapshot.getString("Address");
                        }
                        if(documentSnapshot.getString("Email") != null){
                            email = documentSnapshot.getString("Email");
                            Log.d("ASDqqqq",uid+""+email);
                        }

                        try {
                            gettheorders(documentSnapshot.getLong("No. Order").intValue());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Log.d("ASA",Delivery+"");
                        if (first) {
                            setadapter1(Delivery);
                        }
                    }
                });
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
        mEmptyView = view.findViewById(R.id.loadingLayout);
        ids = new ArrayList<>();
        products = new ArrayList<>();
        status= new ArrayList<>();
        price= new ArrayList<>();
        temp= new ArrayList<>();
        temp1= new ArrayList<>();
        recyclerView = view.findViewById(R.id.orderlist);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

//        MainActivity2 mMainActivity2 = new MainActivity2();
//        mNavigationView =getActivity().findViewById(R.id.nav_view);
//       mNavigationView.getMenu().getItem(3).setChecked(true);
        // Inflate the layout for this fragment
        if(uid == null){
            mEmptyView.showEmpty(R.drawable.ic_registro,"You haven't signed in","Please Login or Signup");
        }else {
            mEmptyView.showContent();
        }
        return view;
    }


}