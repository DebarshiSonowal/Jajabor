package jajabor.in.app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lib.kingja.switchbutton.SwitchMultiButton;
import me.himanshusoni.quantityview.QuantityView;

public class ProductView extends AppCompatActivity {
TextView productname,productdesc,productprice;
ImageView productpic;
Integer quantity,pid;
Long money;
ElasticImageView addto;
SQLiteDatabase mDatabase;
String colour,size;
    DatabaseReference databaseReference;
    FirebaseFirestore db ;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        DatabaseHelper databaseHelper = new DatabaseHelper(ProductView.this);
        mDatabase =  databaseHelper.getWritableDatabase();
        colour = "Black";
        size = "S";
        quantity = 1;
        productname = findViewById(R.id.prductm);
        productdesc = findViewById(R.id.productdesc);
        productprice = findViewById(R.id.productprice);
        productpic = findViewById(R.id.productpic1);
        addto = findViewById(R.id.addtocart);
        db = FirebaseFirestore.getInstance();
        productname.setText(getIntent().getStringExtra("name"));
        productdesc.setText(getIntent().getStringExtra("desc"));
        productprice.setText("Price: "+"₹" +getIntent().getStringExtra("price"));
        pid = getIntent().getIntExtra("pid",0);
        money =Long.parseLong( getIntent().getStringExtra("price"));
        Picasso.get().load(getIntent().getStringExtra("url")).into(productpic);
        QuantityView quantityView =findViewById(R.id.quantityView_default);
        quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                Toast.makeText(ProductView.this,""+newQuantity,Toast.LENGTH_SHORT).show();
                quantity = newQuantity;
            }

            @Override
            public void onLimitReached() {

            }
        });

        SwitchMultiButton mSwitchMultiButton1 = (SwitchMultiButton) findViewById(R.id.switchmultibutton1);
        mSwitchMultiButton1.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(ProductView.this, tabText, Toast.LENGTH_SHORT).show();
                colour = tabText;
            }
        });
        SwitchMultiButton mSwitchMultiButton = (SwitchMultiButton) findViewById(R.id.switchmultibutton);
        mSwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(ProductView.this, tabText, Toast.LENGTH_SHORT).show();
                size = tabText;
            }
        });
        addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(Contract.CartItem.COLUMN_NAME,getIntent().getStringExtra("name"));
                Log.d("Product",getIntent().getStringExtra("name"));
                cv.put(Contract.CartItem.COLUMN_PIC,getIntent().getStringExtra("url"));
                cv.put(Contract.CartItem.COLUMN_PRICE,money*quantity);
                cv.put(Contract.CartItem.COLUMN_SIZE,size);
                cv.put(Contract.CartItem.COLUMN_COLOR,colour);
                cv.put(Contract.CartItem.COLUMN_QUANTITY,quantity);
                cv.put(Contract.CartItem.COLUMN_PID,pid);
                mDatabase.insert(Contract.CartItem.TABLE_NAME,null,cv);

//                mDatabase.insert(Contract.MissedCalls.TABLE_NAME,null,cv);
//                Map<String, Object> note = new HashMap<>();
//                note.put("Productpic", getIntent().getStringExtra("url"));
//                note.put("name", getIntent().getStringExtra("name"));
//                note.put("Price", money);
//                note.put("Colour", colour);
//                note.put("Size", size);
//                note.put("Quantity", quantity);
//                note.put("User",FirebaseAuth.getInstance().getCurrentUser().getUid());

//                db.collection("Cart").add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(ProductView.this, "Added to cart", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                });
            }


        });
    }

    private String getid() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}