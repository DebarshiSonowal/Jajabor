package jajabor.in.app.ui.Cart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.pd.chocobar.ChocoBar;
import com.razorpay.Checkout;
import com.razorpay.Invoice;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.skydoves.elasticviews.ElasticButton;
import com.vlonjatg.progressactivity.ProgressRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jajabor.in.app.Helper.Contract;
import jajabor.in.app.Helper.DatabaseHelper;
import jajabor.in.app.R;
import jajabor.in.app.ui.Activity.login;

public class CartActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    //Vitaly Gorbachev
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
    String ORDERid;
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
    ProgressRelativeLayout mEmptyView;
    Double mDouble;
    TextView MRP,PAYABLE,Delivery,itemno;
    String pids,totalquantity,email;
    Map<String,Object>note;
    FirebaseUser mUser;
    JSONObject options;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    DatabaseHelper databaseHelper;
    ConstraintLayout mConstraintLayout;
    LinearLayout a;
    CartViewModel mViewModel;
    RazorpayClient mRazorpayClient;
    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.getno().observe(CartActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
//                Toast.makeText(CartActivity.this,"Changexcx"+integer,Toast.LENGTH_SHORT).show();
                itemno.setText("Item"+" ("+integer+")");
                if(integer == 0){
                    mEmptyView.showEmpty(R.drawable.ic_comprar,"No items in the cart","Add items to the cart");
                }else {
                    mEmptyView.showContent();
                }
            }
        });
        if (mUser != null) {
            db.collection("UserProfile").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(mEventListener = new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.exists()){
                        if(documentSnapshot.get("Address") != null){
                            Delivery.setText(documentSnapshot.getString("Address"));
                        }
                        if(documentSnapshot.get("Email") != null){
                            email = documentSnapshot.getString("Email");
                        }
                    }
                }
            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mEventListener = null;
        documentReference = null;
        mListener = null;
        databaseReference = null;
        mAdapter = null;
        mRecyclerView = null;
        db = null;
        mViewModel = null;
        databaseHelper = null;
        mDatabase = null;
        System.gc();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_cart);
        mViewModel = ViewModelProviders.of(CartActivity.this).get(CartViewModel.class);
        mEmptyView = findViewById(R.id.progressActivity);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseHelper = new DatabaseHelper(this);
        mDatabase = databaseHelper.getWritableDatabase();
        db = FirebaseFirestore.getInstance();
        try {
            mRazorpayClient = new RazorpayClient(String.valueOf(R.string.razorpay_key), String.valueOf(R.string.razorpay_key_secret));
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
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
        MRP = findViewById(R.id.totalAmount);
        PAYABLE = findViewById(R.id.totalMRP);
        mRecyclerView = findViewById(R.id.customRecycler);
       a = findViewById(R.id.linear);
       mConstraintLayout = findViewById(R.id.refresh_layout);
        mCursor = getAllItems();
        itemno = findViewById(R.id.itemno1);
        Delivery=findViewById(R.id.deliveryAddress);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mProductAdapter = new CartProductAdapter(this,getAllItems());
        mProductAdapter.swapCursor(getAllItems());
        getMRP();
        itemno.setText("Item"+" ("+mCursor.getCount()+")");
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
        colour = getColour();
        pic = getPic();
        mViewModel.setNo( mCursor.getCount());

        if(mCursor.getCount() == 0){
            Log.d("EMpty","Inside");
//            mEmptyView.error().include(R.id.linear).show();
            mEmptyView.showEmpty(R.drawable.ic_comprar,"No items in the cart","Add items to the cart");
            setTitle("Loading");
        }else
            mEmptyView.showContent();
        try {
            Cursor cursor =  mDatabase.rawQuery("SELECT SUM(" + Contract.CartItem.COLUMN_PRICE + ") as Total FROM " + Contract.CartItem.TABLE_NAME, null);
            // Cursor cursor =  mDatabase.execSQL("SELECT SUM(" + Contract.CartItem.COLUMN_PRICE + ") as Total FROM " + Contract.CartItem.TABLE_NAME,null);
            if (cursor.moveToFirst()) {
                totalprice = cursor.getInt(cursor.getColumnIndex("Total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                mViewModel.setNo( mCursor.getCount());

            }
        }).attachToRecyclerView(mRecyclerView);

        getDataToString();
        MRP.setText(""+totalprice);
        PAYABLE.setText(""+totalprice);
        pay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (mUser != null) {
                    if (!TextUtils.isEmpty(Delivery.getText()) && !size.isEmpty() && !email.isEmpty()) {
                        size = getsizes();
                        getMRP();
                        startPayment();
                        Toast.makeText(CartActivity.this,size+""+colour,Toast.LENGTH_SHORT).show();
                        Log.d("Size",size+"");
                    } else {
                        FancyToast.makeText(CartActivity.this,"Delivery Address is not available please add an address",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                        new BottomDialog.Builder(CartActivity.this)
                                .setTitle("Welcome User!")
                                .setContent("To continue shopping please enter the required details.")
                                .setPositiveText("Enter The Details")
                                .setPositiveBackgroundColor(android.R.color.darker_gray)
                                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                                .setPositiveTextColorResource(android.R.color.black)
                                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                                .onPositive(new BottomDialog.ButtonCallback() {
                                    @Override
                                    public void onClick(BottomDialog dialog) {
                                        Log.d("BottomDialogs", "Do something!");
                                        Navigation.findNavController(CartActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_profile);
                                    }
                                }).show();
                    }
                } else {
                    ChocoBar.builder().setActivity(CartActivity.this)
                            .setText("Login or Sign up")
                            .setDuration(ChocoBar.LENGTH_SHORT)
                            .setActionText("Log in/ Sign up")
                            .setActionClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(CartActivity.this, login.class));
                                }
                            })
                            .red()
                            .show();
                }
                }


        });



    }
private List<String> getColour(){
    ArrayList<String> arrayList=new ArrayList<>();

    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor res =  db.rawQuery( "select * from "+Contract.CartItem.TABLE_NAME, null );
    res.moveToFirst();
    if (res != null)
    {
        while(res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(Contract.CartItem.COLUMN_COLOR)));
            Log.d("emailssinlisttt",arrayList.toString());
            res.moveToNext();
        }
    }
    return arrayList;
}
private List<String>getPic(){
    ArrayList<String> arrayList=new ArrayList<>();

    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor res =  db.rawQuery( "select * from "+Contract.CartItem.TABLE_NAME, null );
    res.moveToFirst();
    if (res != null)
    {
        while(res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(Contract.CartItem.COLUMN_PIC)));
            Log.d("emailssinlisttt",arrayList.toString());
            res.moveToNext();
        }
    }
    return arrayList;
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


    private void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();
        totalprice = totalprice * 100;
        mDouble = totalprice.doubleValue();
        Log.d("Payment",  mDouble+"");
        try {
            options = new JSONObject();
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

        getMRP();
        quant.clear();
        PID.clear();
        PID = getAllPid();
        quant = getquantity();
        mProductAdapter.swapCursor(getAllItems());
        getDataToString();
        size = getsizes();
//        itemno.setText("Item"+" ("+mCursor.getCount()+")");

    }

    private void getMRP() {
        Cursor cursor =  mDatabase.rawQuery("SELECT SUM(" + Contract.CartItem.COLUMN_PRICE + ") as Total FROM " + Contract.CartItem.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            totalprice = cursor.getInt(cursor.getColumnIndex("Total"));
            MRP.setText(""+totalprice);
            PAYABLE.setText(""+totalprice);
        }
        cursor.close();
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
        mDatabase.delete(Contract.CartItem.TABLE_NAME,null,null);
        mProductAdapter.swapCursor(getAllItems());
//        getMRP();
//        size = getsizes();
        try {
            generateInvoice(paymentData);
        } catch (Exception e) {
            Log.d("PAYMENT MAIN GARBAR","Invoice "+ e.getMessage());
        }
//        Invoice in = new Invoice(options);
//        InvoiceClient invoiceClient = null;
//        invoiceClient.create();
        Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
        try {
            addToPayment(s,paymentData);
        } catch (Exception e) {
            Log.d("PAYMENT MAIN GARBAR","Add to payment "+ e.getMessage());
        }
        try {
            addToOrder(paymentData);
        } catch (Exception e) {
            Log.d("PAYMENT MAIN GARBAR","ORder"+e.getMessage());
        }

    }

    private void addToPayment(String s,PaymentData paymentData) {
        note.put("Email", email);
        note.put("Payment",mDouble);
        note.put("Products",pids);
        note.put("Quantity",quant);
        note.put("Order ID",paymentData.getOrderId());
        note.put("Size",size);
        note.put("Colour",colour);
        note.put("UID",FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.collection("Payment").document(s).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CartActivity.this, "Data stored ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToOrder(PaymentData paymentData) {
        note.clear();
        note = new HashMap<>();
        note.put("Email", email);
        note.put("Payment",mDouble);
        note.put("Status","Order Placed");
        note.put("Products",pids);
        note.put("Quantity",quant);
        note.put("Size",size);
        note.put("Payment ID",paymentData.getPaymentId());
        note.put("Colour",colour);
        note.put("Picture",pic);
        note.put("UID",FirebaseAuth.getInstance().getCurrentUser().getUid());
        ORDERid = db.collection("Order").document().getId();
        db.collection("Order").document(ORDERid).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ChocoBar.builder().setActivity(CartActivity.this)
                        .setText("Order Placed")
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .green()  // in built green ChocoBar
                        .show();
            }
        });
    }

    private void generateInvoice(PaymentData paymentData) {
        JSONObject lineItem = new JSONObject();
        try {
            lineItem.put("amount", mDouble);
            lineItem.put("name", paymentData.getOrderId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        JSONArray lineItems = new JSONArray();
        lineItems.put(lineItem);
        JSONObject request = new JSONObject();
        try {
            request.put("line_items", lineItems);
            request.put("date", simpleDateFormat.format(date));
            request.put("currency", "INR");
            request.put("sms_notify", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Invoice invoice = mRazorpayClient.Invoices.create(request);
        } catch (RazorpayException e) {
            Log.d("PAYMENT MAIN GARBAR","Invoice gene"+ e.getMessage());
        }
        getInvoice(paymentData);
    }

    private void getInvoice(PaymentData paymentData ) {
        Invoice invoice1 = null;
        try {
            invoice1 = mRazorpayClient.Invoices.fetch(paymentData.getOrderId());
        } catch (RazorpayException e) {
            Log.d("PDF",e.toString());
        }
        Log.d("PDF",invoice1.toString()+"");
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        mDouble=0.0;
        totalprice=0;
        Log.e("Payment",  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        ChocoBar.builder().setActivity(CartActivity.this)
                .setText("Failed")
                .setDuration(ChocoBar.LENGTH_INDEFINITE)
                .setActionText(android.R.string.ok)
                .red()   // in built red ChocoBar
                .show();
        Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
    }
}