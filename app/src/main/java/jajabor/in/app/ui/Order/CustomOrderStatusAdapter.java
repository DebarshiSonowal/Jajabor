package jajabor.in.app.ui.Order;

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

import java.util.Arrays;
import java.util.List;

import jajabor.in.app.Helper.OrderObject;
import jajabor.in.app.R;

public class CustomOrderStatusAdapter extends RecyclerView.Adapter<CustomOrderStatusAdapter.CustomOrderStatusViewHolder> {
    LayoutInflater mLayoutInflater;
    Context mContext;
    OrderObject orderObject;
    OnItemSelectedListener mListener;

    public CustomOrderStatusAdapter(Context context, OrderObject orderObject) {
        mContext = context;
        this.orderObject = orderObject;
        mLayoutInflater = LayoutInflater.from(context);
    }
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }
    public void setOnItemSelectedListener(CustomOrderStatusAdapter.OnItemSelectedListener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public CustomOrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = mLayoutInflater.inflate(R.layout.orderobjectlayout,parent,false);
        return new CustomOrderStatusViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomOrderStatusViewHolder holder, int position) {
        if(orderObject.getImage().get(position).contains(",")){
            List<String> elephantList = Arrays.asList(orderObject.getImage().get(position).split(","));
            Picasso.get().load(elephantList.get(0)).into(holder.image);
        }else
            Picasso.get().load(orderObject.getImage().get(position)).into(holder.image);

        Log.d("MYNEW",orderObject.getImage().get(position));

        holder.color.setText(orderObject.getColour().get(0));
        holder.quantity.setText(orderObject.getQuantity().get(position));
        holder.price.setText(orderObject.getPrice().get(position));
        holder.id.setText(orderObject.getOrderId().get(position));
    }

    @Override
    public int getItemCount() {
        return orderObject.getOrderId().size();
    }

    public  class CustomOrderStatusViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView id,price,quantity,color;
        public CustomOrderStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.productImage);
            id = itemView.findViewById(R.id.IDproduct);
            price = itemView.findViewById(R.id.nameProduct);
            quantity = itemView.findViewById(R.id.productTag);
            color = itemView.findViewById(R.id.priceProduct);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("IHAVECLICKER","ADAPTER");
                    if (mListener != null) {
                        Log.d("IHAVECLICKER","ADAPTER1");
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Log.d("IHAVECLICKER","ADAPTER2");
                            mListener.onItemSelected(position);
                        }
                    }
                }
            });
        }

    }
}
