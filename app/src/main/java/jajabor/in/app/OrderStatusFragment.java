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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class OrderStatusFragment extends Fragment {
FirebaseFirestore db;
EventListener<QuerySnapshot> mListener;
    EventListener<DocumentSnapshot> mEventListener,mEventListener1;
    List<String> ids;
    LinearLayoutManager layoutManager;
    List<String>status;
    String Delivery;
    List<String>products;
    String phone,uid;
    List<String>price;
    List<String>temp1,temp;
    RecyclerView recyclerView;
    OrderStatusAdapter mAdapter;
    Boolean first= true;
    FirebaseUser mUser;
    @Override
    public void onStart() {
        super.onStart();
        if (mUser != null) {
            db.collection("Order").whereEqualTo("Email", FirebaseAuth.getInstance().getCurrentUser().getEmail()).addSnapshotListener(mListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot1:queryDocumentSnapshots){
                        ids.add(queryDocumentSnapshot1.getId());
                        Log.d("Retrieved1",queryDocumentSnapshot1.getId());
                       getothers(queryDocumentSnapshot1);
                    }
                        mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void gettheorders(int intValue) {

    }

    private void getothers(QueryDocumentSnapshot queryDocumentSnapshot1) {
        if (mUser != null) {
            db.document("Order/"+queryDocumentSnapshot1.getId()).addSnapshotListener(mEventListener = new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    price.add(documentSnapshot.getLong("Payment").toString());
                    Log.d("Retrieved2",documentSnapshot.getLong("Payment").toString());
                    status.add(documentSnapshot.getString("Status"));
                    Log.d("Retrieved3",documentSnapshot.getString("Status"));
                    products.add(documentSnapshot.get("Products").toString());
                    Log.d("Retrieved4",documentSnapshot.get("Products").toString());
                    mAdapter.notifyDataSetChanged();
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
                    if(documentSnapshot.get("Phone") != null){
                        phone= documentSnapshot.getLong("Phone").toString();
                        Log.d("ASA2",documentSnapshot.getLong("Phone").toString());
                    }
                    if (documentSnapshot.getString("Address") != null) {
                        Delivery=documentSnapshot.getString("Address");
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

    private void setadapter1(String delivery) {
        recyclerView.setAdapter( mAdapter = new OrderStatusAdapter(ids,status,phone,delivery,price,products,getContext()));
        Log.d("ASA4",delivery+"");
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
                            phone= documentSnapshot.getLong("Phone").toString();
                            Log.d("ASA2",documentSnapshot.getLong("Phone").toString());
                        }
                        if (documentSnapshot.getString("Address") != null) {
                            Delivery=documentSnapshot.getString("Address");
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
        return view;
    }
}