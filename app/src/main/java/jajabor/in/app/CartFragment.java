package jajabor.in.app;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartFragment extends Fragment implements PaymentResultListener {
    CollectionReference databaseReference;
    FirebaseFirestore db ;
    List<String>id;
    List<Integer>PID;
    List<String>name;
    List<Long>quantity;
    List<String>size;
    List<String>colour;
    List<String>pic;
    List<Long>price;
    RecyclerView mRecyclerView;
    DocumentReference documentReference;
    CartAdapter mAdapter;
    CartProductAdapter mProductAdapter;
    EventListener<QuerySnapshot> mListener;
    EventListener<DocumentSnapshot> mEventListener;
    SQLiteDatabase mDatabase;
    ElasticButton pay;
    Cursor mCursor;
    Integer totalprice,pid;
    TextView MRP,PAYABLE,item;
    String pids;
    private SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY", Locale.getDefault());
    DatabaseHelper databaseHelper;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mEventListener = null;
        documentReference = null;
        mListener = null;
        databaseReference = null;
        mAdapter = null;
        mRecyclerView = null;
        db = null;
        System.gc();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        mDatabase = databaseHelper.getWritableDatabase();
        mCursor = getAllItems();
        Cursor cursor =  mDatabase.rawQuery("SELECT SUM(" + Contract.CartItem.COLUMN_PRICE + ") as Total FROM " + Contract.CartItem.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            totalprice = cursor.getInt(cursor.getColumnIndex("Total"));
        }
//        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM ",null);
//        for(int i=0;i<mCursor.getCount();i++){
//            totalprice = totalprice + mCursor.getInt(mCursor.getColumnIndex(Contract.CartItem.COLUMN_PRICE));
//        }
        price = new ArrayList<>();
        colour= new ArrayList<>();
        size = new ArrayList<>();
        quantity= new ArrayList<>();
        name= new ArrayList<>();
        id = new ArrayList<>();
        pic = new ArrayList<>();
        pay= root.findViewById(R.id.paybtn);
        MRP = root.findViewById(R.id.totalmrp);
        PAYABLE = root.findViewById(R.id.total);
        mRecyclerView = root.findViewById(R.id.cartview);
//        mAdapter = new CartAdapter(pic,name,size,price,colour,quantity,getContext());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mProductAdapter = new CartProductAdapter(getContext(),getAllItems());
        mRecyclerView.setAdapter(mProductAdapter);
//        db = FirebaseFirestore.getInstance();
//        databaseReference = db.collection("Cart");
//        getlist();
        PID = getAllPid();
        // Inflate the layout for this fragment
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerview, RecyclerView.ViewHolder viewholder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewholder, int direction) {
                removeItem((Integer) viewholder.itemView.getTag());

            }
        }).attachToRecyclerView(mRecyclerView);
        for(int i=0;i<PID.size();i++){
            if (i == 0) {
                pids = PID.get(i).toString();
            } else {
                pids = pids+","+PID.get(i);
            }
        }
        MRP.setText(""+totalprice);
        PAYABLE.setText(""+totalprice);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startPayment();
            }
        });
        return root;
    }

    private void startPayment() {
        final Activity activity = getActivity();
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "JAJABBOR");
            options.put("description", "Order Ids: "+pids);
            options.put("image", "https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/01/logo-08.png");
            options.put("currency", "INR");
            options.put("payment_capture",true);
            options.put("amount", totalprice*100);
            options.put("created_at",sdf.format(new Date()));
            co.open(activity, options);
//            Checkout.clearUserData(getContext());
        } catch (JSONException e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    private void getlist() {
//        databaseReference.whereEqualTo("User",FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(getActivity(),mListener = new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
//                    id.add(queryDocumentSnapshot.getId());
//                    Log.d("Ids",queryDocumentSnapshot.getId());
//                    getitems(queryDocumentSnapshot.getId());
//                }
//            }
//        });
//    }

//    private void removeItem(final int tag) {
//        documentReference = db.document("Cart/"+id.get(tag));
//        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getContext(),""+id.get(tag),Toast.LENGTH_SHORT).show();
//                    Log.d("Price",price.get(0).toString());
////                id.remove(tag);
////                price.remove(tag);
////                colour.remove(tag);
////                pic.remove(tag);
////                name.remove(tag);
////                quantity.remove(tag);
////                Log.d("Length", String.valueOf(quantity.size())+quantity.get(0));
////                size.remove(tag);
////                mAdapter.notifyItemRemoved(tag);
////                mAdapter.notifyDataSetChanged();
//                mAdapter.delete(tag);
////                getlist();
//        }
//        });
//
//    }

//    private void getitems(String id) {
//        documentReference = db.document("Cart/"+id);
//        documentReference.addSnapshotListener(mEventListener = new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                    price.add(documentSnapshot.getLong("Price"));
//                Log.d("Ids1",documentSnapshot.getLong("Price")+"");
//                    colour.add(documentSnapshot.getString("Colour"));
//                Log.d("Ids2",documentSnapshot.getString("Colour")+"");
//                    pic.add(documentSnapshot.getString("Productpic"));
//                Log.d("Ids3",documentSnapshot.getString("Productpic")+"");
//                    name.add(documentSnapshot.getString("name"));
//                Log.d("Ids4",documentSnapshot.getString("name")+"");
//                    quantity.add(documentSnapshot.getLong("Quantity"));
//                Log.d("Ids5",documentSnapshot.getLong("Quantity")+"");
//                    size.add(documentSnapshot.getString("Size"));
//                Log.d("Ids6",documentSnapshot.getString("Size")+"");
//                mAdapter.notifyDataSetChanged();
//            }
//        });
//
//
//    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("Payment", " payment successfull "+ s);
        Toast.makeText(getContext(), "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("Payment",  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );

        Toast.makeText(getContext(), "Payment error please try again", Toast.LENGTH_SHORT).show();

    }
    private void removeItem(long id) {
        mDatabase.delete(Contract.CartItem.TABLE_NAME,
                Contract.CartItem._ID + "=" + id, null);
        mProductAdapter.swapCursor(getAllItems());
       getMRP();

    }

    private void getMRP() {
        Cursor cursor =  mDatabase.rawQuery("SELECT SUM(" + Contract.CartItem.COLUMN_PRICE + ") as Total FROM " + Contract.CartItem.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            totalprice = cursor.getInt(cursor.getColumnIndex("Total"));
        }
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                Contract.CartItem.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public ArrayList<Integer> getAllPid() {

        ArrayList<Integer> arrayList=new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+Contract.CartItem.TABLE_NAME, null );
        res.moveToFirst();
        if (res != null)
        {
            while(res.isAfterLast() == false){
                arrayList.add(res.getInt(res.getColumnIndex(Contract.CartItem.COLUMN_PID)));
                Log.d("emailssinlisttt",arrayList.toString());
                res.moveToNext();
            }}
        return arrayList;

    }
}