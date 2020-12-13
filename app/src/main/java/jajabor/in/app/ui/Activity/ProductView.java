package jajabor.in.app.ui.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pd.chocobar.ChocoBar;
import com.skydoves.elasticviews.ElasticButton;

import java.util.ArrayList;
import java.util.Arrays;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import jajabor.in.app.Helper.Contract;
import jajabor.in.app.Helper.DatabaseHelper;
import jajabor.in.app.R;
import me.himanshusoni.quantityview.QuantityView;

public class ProductView extends AppCompatActivity {
TextView productname,productdesc,productprice,sizechart;
ImageView productpic;
Integer quantity,pid;
ToggleSwitch toggleSwitch,mToggleSwitch;
ArrayList<String> labels,labels2;
Long money;
ElasticButton addto;
SQLiteDatabase mDatabase;
String colour,size;
DatabaseReference databaseReference;
FirebaseFirestore db ;
ValueEventListener mListener;
QuantityView quantityView;
DatabaseHelper databaseHelper;
ImagePopup imagePopup;
    Context mContext;
    Activity mActivity;
    ContentValues cv;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db = null;
        databaseReference = null;
        mDatabase.close();
        toggleSwitch = null;
        mToggleSwitch = null;
        quantityView = null;
        databaseHelper.close();
        imagePopup = null;
        try {
            cv.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        cv =null;
        databaseHelper = null;
        mContext = null;
        mActivity=null;
        System.gc();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_product_view);
        toggleSwitch = findViewById(R.id.sizeswitch);
        mToggleSwitch =findViewById(R.id.colorswitch);
        labels = new ArrayList<>();
        labels2 = new ArrayList<>();
        mContext = this;
        mActivity = this;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("0").addValueEventListener(mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals("Color")){
                        labels2 = new ArrayList<>(Arrays.asList(dataSnapshot.getValue().toString().split(",")));
                        Log.d("PRODUCT",labels2.get(0));
                        mToggleSwitch.setLabels(labels2);

                    }
                    if(dataSnapshot.getKey().equals("Size")){
                        labels = new ArrayList<>(Arrays.asList(dataSnapshot.getValue().toString().split(",")));
                        Log.d("PRODUCT",labels.get(0));
                        toggleSwitch.setLabels(labels);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseHelper = new DatabaseHelper( mContext);
        imagePopup = new ImagePopup( mContext);
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.TRANSPARENT);  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional
        imagePopup.initiatePopupWithGlide("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/sizechart.jfif?alt=media&token=79ca379b-35dd-4668-8595-5dee3a039c3e");
        mDatabase =  databaseHelper.getWritableDatabase();
        colour = "Black";
        size = "S";
        quantity = 1;
        productname = findViewById(R.id.prductm);
        productdesc = findViewById(R.id.productdesc);
        productprice = findViewById(R.id.productprice);
        productpic = findViewById(R.id.productpic1);
        addto = findViewById(R.id.addtocart);
        sizechart = findViewById(R.id.sizecharttext);
        sizechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
//                SizeChartView sizeChartView = new SizeChartView();
////                Glide.with(ProductView.this).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/sizechart.jfif?alt=media&token=79ca379b-35dd-4668-8595-5dee3a039c3e")
////                        .into(sizeChartView.chart);
//                sizeChartView.show(getSupportFragmentManager(),"Sizechart");
            }
        });
        db = FirebaseFirestore.getInstance();
        productname.setText(getIntent().getStringExtra("name"));
        productdesc.setText(getIntent().getStringExtra("desc"));
        productprice.setText("Price: "+"₹" +getIntent().getStringExtra("price"));
        pid = getIntent().getIntExtra("pid",0);
        money =Long.parseLong( getIntent().getStringExtra("price"));
//        Picasso.get().load(getIntent().getStringExtra("url")).into(productpic);
        Glide.with( mActivity).load(getIntent().getStringExtra("url")).into(productpic);
        quantityView =findViewById(R.id.quantityview);
        quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                Toast.makeText(mContext,""+newQuantity,Toast.LENGTH_SHORT).show();
                quantity = newQuantity;
            }

            @Override
            public void onLimitReached() {

            }
        });

        addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!labels.isEmpty() && !labels2.isEmpty()) {
                    cv = new ContentValues();
                    cv.put(Contract.CartItem.COLUMN_NAME,getIntent().getStringExtra("name"));
                    Log.d("Product",getIntent().getStringExtra("name"));
                    cv.put(Contract.CartItem.COLUMN_PIC,getIntent().getStringExtra("url"));
                    cv.put(Contract.CartItem.COLUMN_PRICE,money*quantity);
                    cv.put(Contract.CartItem.COLUMN_SIZE,labels.get(toggleSwitch.getCheckedTogglePosition()).trim());
                    cv.put(Contract.CartItem.COLUMN_COLOR,labels2.get(mToggleSwitch.getCheckedTogglePosition()).trim());
                    cv.put(Contract.CartItem.COLUMN_QUANTITY,quantity);
                    cv.put(Contract.CartItem.COLUMN_PID,pid);
                    mDatabase.insert(Contract.CartItem.TABLE_NAME,null,cv);
                }
                ChocoBar.builder().setActivity(mActivity)
                        .setText("Added to cart")
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .green()  // in built green ChocoBar
                        .show();
            }


        });
    }

}