package jajabor.in.app.ui.Order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import jajabor.in.app.R;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder> {
    List<String>orderid;
    List<String>orderstatus;
   String pno;
    String deliveryad;
    List<String>price;
    List<String> picture;
    List<String>products;
    Context context;
    OnItemSelectedClickListener mListener;
    OrderStatusElement orderStatusElement;
    private LayoutInflater mLayoutInflater;
    public interface OnItemSelectedClickListener {
        void onItemSelectedClick(int position);
    }
    public void OnItemSelectedClickListener(OnItemSelectedClickListener listener) {
        mListener = listener;
    }
    public OrderStatusAdapter(OrderStatusElement orderStatusElement, String pno, String deliveryad) {
        mLayoutInflater = LayoutInflater.from(orderStatusElement.getContext());
        this.orderStatusElement = orderStatusElement;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.orderstausview,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("NUMBERSYSTEM1",orderStatusElement.orderid.size()+"");
        Log.d("NUMBERSYSTEM2",orderStatusElement.price.size()+"");
        Log.d("NUMBERSYSTEM3",orderStatusElement.picture.size()+"");
        Log.d("NUMBERSYSTEM4",orderStatusElement.products.size()+"");
//        if(orderid.size() == price.size() && orderid.size() == orderstatus.size() && orderid.size() == picture.size() && orderid.size() == products.size())
//        {
            holder.id.setText(orderStatusElement.orderid.get(position));
//        try {
            holder.price.setText(orderStatusElement.price.get(position));
//        } catch (Exception e) {
//            Log.d("problems3",e.getMessage());
//            e.printStackTrace();
//        }
//        try {
            holder.status.setText(orderStatusElement.orderstatus.get(position));
//        } catch (Exception e) {
//            Log.d("problems5",e.getMessage());
//            e.printStackTrace();
//        }
//        try {
//            Glide.with(context).load(picture).into(holder.pic);
            Picasso.get().load(orderStatusElement.picture.get(position)).into(holder.pic);
//        } catch (Exception e) {
//            Log.d("MYPIC",e.toString()+"");
//        }
//        try {
            holder.products.setText(orderStatusElement.products.get(position));
//        } catch (Exception e) {
//            Log.d("problems6",e.getMessage());
//            e.printStackTrace();
//        }
        }
//    }

    @Override
    public int getItemCount() {
        return orderStatusElement.orderid.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
TextView id,no,status,delivery,price,products;
RoundedImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.orderidview);
//            no = itemView.findViewById(R.id.orderphonenoview);
            status = itemView.findViewById(R.id.orderstatusview);
//            delivery = itemView.findViewById(R.id.orderdeliveryview);
            price = itemView.findViewById(R.id.orderpriceview);
            products = itemView.findViewById(R.id.orderproducts);
            pic = itemView.findViewById(R.id.pic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("IHAVECLICKER","ADAPTER");
                    if (mListener != null) {
                        Log.d("IHAVECLICKER","ADAPTER1");
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Log.d("IHAVECLICKER","ADAPTER2");
                            mListener.onItemSelectedClick(position);
                        }
                    }
                }
            });
        }
    }
}
