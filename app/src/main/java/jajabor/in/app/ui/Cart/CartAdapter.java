package jajabor.in.app.ui.Cart;

import android.content.Context;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<String> url;
    List<String>name;
    List<String>size;
    List<Long>price;
    List<String>colour;
    List<Long>quantity;
    private Context context ;
    private LayoutInflater mLayoutInflater;
    public CartAdapter(List<String> url, List<String> name, List<String> size, List<Long> price, List<String> colour, List<Long> quantity, Context context) {
        this.url = url;
        this.name = name;
        this.size = size;
        this.price = price;
        this.colour = colour;
        this.quantity = quantity;
       mLayoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = mLayoutInflater.inflate(R.layout.cartitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(url.get(position)).into(holder.product);
        holder.nameview.setText(name.get(position));
        if (quantity.get(position)!= null) {
            holder.quantityview.setText(String.valueOf(quantity.get(position)));
        }
        holder.colourview.setText(colour.get(position));
        holder.sizeview.setText(size.get(position));
        if (quantity.get(position) != null) {
            holder.priceview.setText(String.valueOf(price.get(position)));
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return url.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView product;
        TextView nameview,priceview,sizeview,colourview,quantityview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.cartPic);
            nameview = itemView.findViewById(R.id.cartName);
            priceview = itemView.findViewById(R.id.cartPrice);
            sizeview = itemView.findViewById(R.id.cartsize1);
            colourview = itemView.findViewById(R.id.cartColor);
            quantityview = itemView.findViewById(R.id.cartQuantity);

        }
    }
    public void delete(int pos){
        url.remove(pos);
        price.remove(pos);
        name.remove(pos);
        size.remove(pos);
        colour.remove(pos);
        quantity.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,1);
    }
}
