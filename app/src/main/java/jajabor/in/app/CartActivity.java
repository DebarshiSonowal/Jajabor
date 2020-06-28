package jajabor.in.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    CollectionReference databaseReference;
    FirebaseFirestore db ;
    List<String> id;
    List<Integer>PID;
    List<Integer>quant;
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
    Integer totalprice;
    Double mDouble;
    TextView MRP,PAYABLE,Delivery;
    String pids,totalquantity;
    Map<String,Object>note;
    private SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY", Locale.getDefault());
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        databaseHelper = new DatabaseHelper(this);
        mDatabase = databaseHelper.getWritableDatabase();
        db = FirebaseFirestore.getInstance();
        price = new ArrayList<>();
        colour= new ArrayList<>();
        size = new ArrayList<>();
        quantity= new ArrayList<>();
        name= new ArrayList<>();
        id = new ArrayList<>();
        note = new HashMap<>();
        pic = new ArrayList<>();
        quant = new ArrayList<>();
        pay= findViewById(R.id.paybtn);
        MRP = findViewById(R.id.totalmrp);
        PAYABLE = findViewById(R.id.total);
        mRecyclerView = findViewById(R.id.cartview);
        mCursor = getAllItems();
        Delivery=findViewById(R.id.delivery);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mProductAdapter = new CartProductAdapter(this,getAllItems());
        mProductAdapter.swapCursor(getAllItems());
        getMRP();
        PID = getAllPid();
        quant = getquantity();
        getDataToString();
        size = getsizes();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mProductAdapter);
        PID = getAllPid();
        quant = getquantity();
        Checkout.preload(getApplicationContext());
        size = getsizes();
        Cursor cursor =  mDatabase.rawQuery("SELECT SUM(" + Contract.CartItem.COLUMN_PRICE + ") as Total FROM " + Contract.CartItem.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            totalprice = cursor.getInt(cursor.getColumnIndex("Total"));
        }

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

        getDataToString();
        MRP.setText(""+totalprice);
        PAYABLE.setText(""+totalprice);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Delivery.getText())) {
                    startPayment();
                } else {
                    FancyToast.makeText(CartActivity.this,"Delivery Address is not available please add an address",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }
        });



    }

    private List<String> getsizes() {
        ArrayList<String> arrayList=new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+Contract.CartItem.TABLE_NAME, null );
        res.moveToFirst();
        if (res != null)
        {
            while(res.isAfterLast() == false){
                arrayList.add(res.getString(res.getColumnIndex(Contract.CartItem.COLUMN_SIZE)));
                Log.d("emailssinlisttt",arrayList.toString());
                res.moveToNext();
            }
        }
        return arrayList;
    }

    private void getDataToString() {
        for(int i=0;i<PID.size();i++){
            if (i == 0) {
                pids = PID.get(i).toString();
                totalquantity = quant.get(i).toString();
            } else {
                pids = pids+","+PID.get(i);
                totalquantity = totalquantity+","+quant.get(i).toString();
            }
        }
    }

    private List<Integer> getquantity() {
        ArrayList<Integer> arrayList=new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+Contract.CartItem.TABLE_NAME, null );
        res.moveToFirst();
        if (res != null)
        {
            while(res.isAfterLast() == false){
                arrayList.add(res.getInt(res.getColumnIndex(Contract.CartItem.COLUMN_QUANTITY)));
                Log.d("emailssinlisttt",arrayList.toString());
                res.moveToNext();
            }}
        return arrayList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventListener = null;
        documentReference = null;
        mListener = null;
        databaseReference = null;
        mAdapter = null;
        mRecyclerView = null;
        db = null;
        System.gc();
    }
    private void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();
        totalprice = totalprice * 100;
        mDouble = totalprice.doubleValue();
        Log.d("Payment",  mDouble+"");
        try {
            JSONObject options = new JSONObject();
            options.put("name", "JAJABBOR");
            options.put("description", "Order Ids: "+pids);
            options.put("image", "https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/01/logo-08.png");
            options.put("currency", "INR");
            options.put("payment_capture",true);
            options.put("amount", mDouble);
//            options.put("created_at",sdf.format(new Date()));
            co.open(activity, options);
            Checkout.clearUserData(this);
//            Checkout.clearUserData(getContext());
        } catch (JSONException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void removeItem(long id) {
        mDatabase.delete(Contract.CartItem.TABLE_NAME,
                Contract.CartItem._ID + "=" + id, null);
        mProductAdapter.swapCursor(getAllItems());
        getMRP();
        quant.clear();
        PID.clear();
        PID = getAllPid();
        quant = getquantity();
        getDataToString();
        size = getsizes();
    }

    private void getMRP() {
        Cursor cursor =  mDatabase.rawQuery("SELECT SUM(" + Contract.CartItem.COLUMN_PRICE + ") as Total FROM " + Contract.CartItem.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            totalprice = cursor.getInt(cursor.getColumnIndex("Total"));
            MRP.setText(""+totalprice);
            PAYABLE.setText(""+totalprice);
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

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.d("Payment", " payment successfull "+ s);
        Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
        note.put("Email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        note.put("Payment",mDouble);
        note.put("Products",pids);
        note.put("Quantity",quant);
        note.put("Size",size);
        note.put("Colour",colour);
        db.collection("Payment").document(s).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CartActivity.this, "Data stored ", Toast.LENGTH_SHORT).show();
            }
        });
        mDatabase.delete(Contract.CartItem.TABLE_NAME,null,null);
        mProductAdapter.swapCursor(getAllItems());
        getMRP();
        size = getsizes();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.e("Payment",  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );

        Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
    }
}