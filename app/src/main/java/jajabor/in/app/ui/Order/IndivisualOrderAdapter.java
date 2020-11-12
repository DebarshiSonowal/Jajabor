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

import jajabor.in.app.Helper.IndivisualOrderObject;
import jajabor.in.app.R;

public class IndivisualOrderAdapter extends RecyclerView.Adapter<IndivisualOrderAdapter.ViewHolder> {
    LayoutInflater mLayoutInflater;
    Context mContext;
    IndivisualOrderObject mOrderObject;

    public IndivisualOrderAdapter(Context context, IndivisualOrderObject orderObject) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOrderObject = orderObject;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.indivisual_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(mOrderObject.getPicture().get(position)).into(holder.productpic);
        Log.d("ORDERDETAILS",mOrderObject.getPicture().get(position)+"");
        holder.quantity.setText(mOrderObject.getQuantity().get(position)+"");
        Log.d("ORDERDETAILS",mOrderObject.getQuantity().get(position)+"");
        holder.productSize.setText(mOrderObject.getSize().get(position));
        Log.d("ORDERDETAILS",mOrderObject.getSize().get(position)+"");
        holder.productColor.setText(mOrderObject.getColor().get(position));
        Log.d("ORDERDETAILS",mOrderObject.getColor().get(position)+"");
        holder.productID.setText(mOrderObject.getId().get(position));
        Log.d("ORDERDETAILS",mOrderObject.getId().get(position)+"");
    }

    @Override
    public int getItemCount() {
        return mOrderObject.getId().size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productpic;
        TextView productID,productColor,productSize,quantity;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productID = itemView.findViewById(R.id.IDproduct);
            productColor = itemView.findViewById(R.id.nameProduct);
            productSize = itemView.findViewById(R.id.priceProduct);
            quantity = itemView.findViewById(R.id.productTag);
            productpic = itemView.findViewById(R.id.productImage);
        }


    }

}
