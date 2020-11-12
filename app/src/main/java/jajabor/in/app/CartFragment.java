package jajabor.in.app;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
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

import jajabor.in.app.Helper.Contract;
import jajabor.in.app.Helper.DatabaseHelper;
import jajabor.in.app.ui.Cart.CartAdapter;
import jajabor.in.app.ui.Cart.CartProductAdapter;

public class CartFragment extends Fragment {
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
        return root;
    }
}