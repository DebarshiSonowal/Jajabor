package jajabor.in.app.ui.Order;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.skydoves.elasticviews.ElasticButton;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jajabor.in.app.Helper.IndivisualOrderObject;
import jajabor.in.app.R;

public class OrderDetailsFragment extends Fragment {

    private OrderDetailsViewModel mViewModel;
    private TextView mIDs,orderID,price,address;
    private ImageView picture;
//    private String[] ;
    private List<String>picList,idList,sizeList,ids,colorList;
    private List<Long>quantityList;
    private String color,size,id,pic,destination,uid,orderStatus;
    private Integer quantity;
    private CollectionReference noteref,ref;
    private FirebaseFirestore db ;
    private TimelineView mTimelineView;
    private DatabaseReference mDocumentReference;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private IndivisualOrderAdapter mAdapter;
    private IndivisualOrderObject mIndivisual;
    private ListView myListView;
    ElasticButton help;
    enum status {
        PLACED,PACKED,SENT
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.order_details_fragment, container, false);
        mIDs = root.findViewById(R.id.productIDs);
        orderID = root.findViewById(R.id.orderID);
        price = root.findViewById(R.id.totalPrices);
        address = root.findViewById(R.id.address);
        mRecyclerView = root.findViewById(R.id.indiRecycler);
        picList =new ArrayList<>();
        idList=new ArrayList<>();
        ids = new ArrayList<>();
        sizeList=new ArrayList<>();
        quantityList=new ArrayList<>();
        help = root.findViewById(R.id.helpbtn);
        colorList = new ArrayList<>();
        mDocumentReference = FirebaseDatabase.getInstance().getReference();
//        mTimelineView =  root.findViewById(R.id.timeline);
        db = FirebaseFirestore.getInstance();
        noteref = db.collection("UserProfile");
        ref = db.collection("Order");
       myListView = root.findViewById(R.id.timeline_listView);
//        myListView.setAdapter(myAdapter);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_contactus);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                Log.d("ORDERDETAILS",s+"");
                id = s;
                orderID.setText(s);
                ref.document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()){
                            mIDs.setText(value.getString("Products"));
                            ids = Arrays.asList(value.getString("Products").split(","));
                             Log.d("ORDERDETAILS",ids+"");
                            destination = value.getString("Address");
                            uid = value.getString("UID");
                            orderStatus = value.getString("Status");
                            if(value.getString("Status").equals("Order Placed")){
                                SetTimeline(status.PLACED);
                            }else if(value.getString("Status").equals("Order Packed")){
                                SetTimeline(status.PACKED);
                            }else {
                                SetTimeline(status.SENT);
                            }
                            picList = (List<String>) value.get("Picture");
                               Log.d("ORDERDETAILS",picList+"");
                            colorList = (List<String>) value.get("Colour");
                              Log.d("ORDERDETAILS",colorList+"");
                            quantityList = (List<Long>)value.get("Quantity");
                            Log.d("ORDERDETAILS",quantityList+"");
                            sizeList = (List<String>)value.get("Size");
                            Log.d("ORDERDETAILS",sizeList+"");
                            mIndivisual = new IndivisualOrderObject(picList,ids,colorList,sizeList,quantityList);
                            mAdapter = new IndivisualOrderAdapter(getContext(),mIndivisual);
                            Log.d("ORDERDETAILS",quantityList.get(0)+"");
                            mRecyclerView.setAdapter(mAdapter);
                            mLinearLayoutManager = new LinearLayoutManager(getContext());
                            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            mRecyclerView.setLayoutManager(mLinearLayoutManager);
                        }
                        noteref.document(value.getString("UID")).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                address.setText(value.getString("Address"));
                            }
                        });
                    }
                });
            }
        });
        }

public void SetTimeline(status Status){
    ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
        if(Status == OrderDetailsFragment.status.PLACED){

            TimelineRow myRow = new TimelineRow(0);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 6);// for 6 hour
            calendar.set(Calendar.MINUTE, 0);// for 0 min
            calendar.set(Calendar.SECOND, 0);// for 0 sec
            myRow.setDate(calendar.getTime());
            myRow.setTitle("Order Received");
            myRow.setDescription("The order has been received and is being processed");
            myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
            myRow.setBellowLineSize(6);
            myRow.setImageSize(40);
            myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
            myRow.setBackgroundSize(60);
            myRow.setDateColor(Color.argb(255, 0, 0, 0));
            myRow.setTitleColor(Color.argb(255, 0, 0, 0));
            myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

// Add the new row to the list
            timelineRowsList.add(myRow);

            ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(getContext(), 0, timelineRowsList,
                    //if true, list will be sorted by date
                    false);
            myListView.setAdapter(myAdapter);
        }else if(Status == status.PACKED){

            TimelineRow myRow = new TimelineRow(0);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            myRow.setDate(calendar.getTime());
            myRow.setTitle("Order Received");
            myRow.setDescription("The order has been received and is being processed");
            myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
            myRow.setBellowLineSize(6);
            myRow.setImageSize(40);
            myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
            myRow.setBackgroundSize(60);
            myRow.setDateColor(Color.argb(255, 0, 0, 0));
            myRow.setTitleColor(Color.argb(255, 0, 0, 0));
            myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

            timelineRowsList.add(myRow);


            TimelineRow myRow1 = new TimelineRow(0);


            Date date1 = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar1.set(Calendar.HOUR_OF_DAY, 6);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            myRow1.setDate(calendar1.getTime());
            myRow1.setTitle("Order Packed");
            myRow1.setDescription("The order has been packed and is ready to be shipped");
            myRow1.setBellowLineColor(Color.argb(255, 0, 0, 0));
            myRow1.setBellowLineSize(6);
            myRow1.setImageSize(40);
            myRow1.setBackgroundColor(Color.argb(255, 0, 0, 0));
            myRow1.setBackgroundSize(60);
            myRow1.setDateColor(Color.argb(255, 0, 0, 0));
            myRow1.setTitleColor(Color.argb(255, 0, 0, 0));
            myRow1.setDescriptionColor(Color.argb(255, 0, 0, 0));

// Add the new row to the list
            timelineRowsList.add(myRow1);

            ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(getContext(), 0, timelineRowsList,
                    //if true, list will be sorted by date
                    false);
            myListView.setAdapter(myAdapter);
        }else if(Status == status.SENT){
            TimelineRow myRow = new TimelineRow(0);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            myRow.setDate(calendar.getTime());
            myRow.setTitle("Order Received");
            myRow.setDescription("The order has been received and is being processed");
            myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
            myRow.setBellowLineSize(6);
            myRow.setImageSize(40);
            myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
            myRow.setBackgroundSize(60);
            myRow.setDateColor(Color.argb(255, 0, 0, 0));
            myRow.setTitleColor(Color.argb(255, 0, 0, 0));
            myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

            timelineRowsList.add(myRow);


            TimelineRow myRow1 = new TimelineRow(0);


            Date date1 = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar1.set(Calendar.HOUR_OF_DAY, 6);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            myRow1.setDate(calendar1.getTime());
            myRow1.setTitle("Order Packed");
            myRow1.setDescription("The order has been packed and is ready to be shipped");
            myRow1.setBellowLineColor(Color.argb(255, 0, 0, 0));
            myRow1.setBellowLineSize(6);
            myRow1.setImageSize(40);
            myRow1.setBackgroundColor(Color.argb(255, 0, 0, 0));
            myRow1.setBackgroundSize(60);
            myRow1.setDateColor(Color.argb(255, 0, 0, 0));
            myRow1.setTitleColor(Color.argb(255, 0, 0, 0));
            myRow1.setDescriptionColor(Color.argb(255, 0, 0, 0));

// Add the new row to the list
            timelineRowsList.add(myRow1);

            TimelineRow myRow2 = new TimelineRow(0);
            Date date2 = new Date();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            calendar2.set(Calendar.HOUR_OF_DAY, 6);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.SECOND, 0);
            myRow2.setDate(calendar2.getTime());
            myRow2.setTitle("Order Sent");
            myRow2.setDescription("The order has been sent through courier");
            myRow2.setBellowLineColor(Color.argb(255, 0, 0, 0));
            myRow2.setBellowLineSize(6);
            myRow2.setImageSize(40);
            myRow2.setBackgroundColor(Color.argb(255, 0, 0, 0));
            myRow2.setBackgroundSize(60);
            myRow2.setDateColor(Color.argb(255, 0, 0, 0));
            myRow2.setTitleColor(Color.argb(255, 0, 0, 0));
            myRow2.setDescriptionColor(Color.argb(255, 0, 0, 0));

// Add the new row to the list
            timelineRowsList.add(myRow2);


            ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(getContext(), 0, timelineRowsList,
                    //if true, list will be sorted by date
                    false);
            myListView.setAdapter(myAdapter);
        }
}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(OrderDetailsViewModel.class);
        mViewModel.getColor().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                color = s;
//                Log.d("ORDERDETAILS",s+"");
            }
        });

        mViewModel.getPic().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                Log.d("ORDERDETAILS",s+"");
                pic = s;
            }
        });
        mViewModel.getQuantity().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
//                Log.d("ORDERDETAILS",integer+"");
                quantity = integer;
            }
        });
        mViewModel.getSize().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                Log.d("ORDERDETAILS",s+"");
                size = s;
            }
        });
        mViewModel.getPrice().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                Log.d("ORDERDETAILS",s+"");
                price.setText(s);
            }
        });

    }

}