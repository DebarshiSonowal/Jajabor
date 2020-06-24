package jajabor.in.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    List<String>url;
    List<String>name;
    List<String>price;
    private Context context ;
    private LayoutInflater mLayoutInflater;

    public ProductAdapter(List<String> url, List<String> name, List<String> price, Context context) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.basicproductview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(url.get(position)).into(holder.product);
        holder.nameview.setText(name.get(position));
        holder.priceview.setText(price.get(position));
        holder.priceview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return url.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
ImageView product;
TextView nameview,priceview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.productpic);
            nameview = itemView.findViewById(R.id.name);
            priceview = itemView.findViewById(R.id.price);
        }
    }
}
