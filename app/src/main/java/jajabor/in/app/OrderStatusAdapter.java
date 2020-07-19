package jajabor.in.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder> {
    List<String>orderid;
    List<String>orderstatus;
   String pno;
    String deliveryad;
    Integer total;
    List<String>price;
    List<String>products;
    private Context context ;
    private LayoutInflater mLayoutInflater;

    public OrderStatusAdapter(List<String> orderid, List<String> orderstatus, String pno, String deliveryad, List<String> price, List<String>products, Context context) {
        this.orderid = orderid;
        this.orderstatus = orderstatus;
        this.pno = pno;
        this.deliveryad= deliveryad;
        this.price = price;
        this.products = products;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.orderstausview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.id.setText(orderid.get(position));
        } catch (Exception e) {
            Log.d("problems1",e.getMessage());
            e.printStackTrace();
        }
        try {
            holder.no.setText(pno);
        } catch (Exception e) {
            Log.d("problems2",e.getMessage());
            e.printStackTrace();
        }
        try {
            holder.price.setText(price.get(position));
        } catch (Exception e) {
            Log.d("problems3",e.getMessage());
            e.printStackTrace();
        }
        try {
            holder.delivery.setText(deliveryad);
            Log.d("ASA1",deliveryad);
        } catch (Exception e) {
            Log.d("problems4",e.getMessage());
            e.printStackTrace();
        }
        try {
            holder.status.setText(orderstatus.get(position));
        } catch (Exception e) {
            Log.d("problems5",e.getMessage());
            e.printStackTrace();
        }
        try {
            holder.products.setText(products.get(position));
        } catch (Exception e) {
            Log.d("problems6",e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return orderid.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
TextView id,no,status,delivery,price,products;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.orderidview);
            no = itemView.findViewById(R.id.orderphonenoview);
            status = itemView.findViewById(R.id.orderstatusview);
            delivery = itemView.findViewById(R.id.orderdeliveryview);
            price = itemView.findViewById(R.id.orderpriceview);
            products = itemView.findViewById(R.id.orderproducts);
        }
    }
}
