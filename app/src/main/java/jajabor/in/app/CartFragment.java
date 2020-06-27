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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements PaymentResultListener {
    CollectionReference databaseReference;
    FirebaseFirestore db ;
    List<String>id;
    List<String>name;
    List<Long>quantity;
    List<String>size;
    List<String>colour;
    List<String>pic;
    List<Long>price;
    RecyclerView mRecyclerView;
    DocumentReference documentReference;
    CartAdapter mAdapter;
    public CartFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        price = new ArrayList<>();
        colour= new ArrayList<>();
        size = new ArrayList<>();
        quantity= new ArrayList<>();
        name= new ArrayList<>();
        id = new ArrayList<>();
        pic = new ArrayList<>();
        mRecyclerView = root.findViewById(R.id.cartview);
        mAdapter = new CartAdapter(pic,name,size,price,colour,quantity,getContext());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        db = FirebaseFirestore.getInstance();
        databaseReference = db.collection("Cart");

        databaseReference.whereEqualTo("User",FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                    id.add(queryDocumentSnapshot.getId());
                    Log.d("Ids",queryDocumentSnapshot.getId());
                    getitems(queryDocumentSnapshot.getId());
                }
            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    private void getitems(String id) {
        documentReference = db.document("Cart/"+id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    price.add(documentSnapshot.getLong("Price"));
                Log.d("Ids",documentSnapshot.getLong("Price")+"");
                    colour.add(documentSnapshot.getString("Colour"));
                Log.d("Ids",documentSnapshot.getString("Colour")+"");
                    pic.add(documentSnapshot.getString("Image"));
                Log.d("Ids",documentSnapshot.getString("Image")+"");
                    name.add(documentSnapshot.getString("Name"));
                Log.d("Ids",documentSnapshot.getString("Name")+"");
                    quantity.add(documentSnapshot.getLong("Quantity"));
                Log.d("Ids",documentSnapshot.getLong("Quantity")+"");
                    size.add(documentSnapshot.getString("Size"));
                Log.d("Ids",documentSnapshot.getString("Size")+"");
                mAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}