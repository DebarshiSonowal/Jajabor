package jajabor.in.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    List<String>url;
    List<String>name;
    List<String>price;
    List<String>shrt;
    private Context context ;
    private LayoutInflater mLayoutInflater;
    Activity mActivity;

    public ProductAdapter(List<String> url, List<String> name, List<String> price,  List<String>shrt, Context context, Activity mActivity) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.shrt = shrt;
        this.context = context;
        this.mActivity = mActivity;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.basicproductview,parent,false);
        BusStation.getBus().register(context);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(url.get(position)).into(holder.product);
        holder.nameview.setText(name.get(position));
        holder.priceview.setText("₹"+price.get(position));
        holder.priceview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,ProductView.class);
                i.putExtra("name",name.get(position));
                i.putExtra("url",url.get(position));
                i.putExtra("price",price.get(position));
                i.putExtra("desc",shrt.get(position));
                i.putExtra("position",position);
                mActivity.startActivity(i);
            }
        });
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,ProductView.class);
                i.putExtra("name",name.get(position));
                i.putExtra("url",url.get(position));
                i.putExtra("price",price.get(position));
                i.putExtra("desc",shrt.get(position));
                i.putExtra("position",position);
                mActivity.startActivity(i);
            }
        });
        holder.nameview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,ProductView.class);
                i.putExtra("name",name.get(position));
                i.putExtra("url",url.get(position));
                i.putExtra("price",price.get(position));
                i.putExtra("desc",shrt.get(position));
                i.putExtra("position",position);
                mActivity.startActivity(i);
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
