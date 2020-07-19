package jajabor.in.app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    List<String>url;
    List<String>name;
    List<String>price;
    List<String>shrt;
    List<Integer>pid;
    private Context context ;
    private LayoutInflater mLayoutInflater;
    Activity mActivity;

    public ProductAdapter(List<String> url, List<String> name, List<String> price,  List<String>shrt,List<Integer>pid, Context context, Activity mActivity) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.shrt = shrt;
        this.context = context;
        this.pid = pid;
        this.mActivity = mActivity;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.basicproductview,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(url.get(position)).resize(400,400).into(holder.product);
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
                i.putExtra("pid",pid.get(position));
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
                i.putExtra("pid",pid.get(position));
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
                i.putExtra("pid",pid.get(position));
                mActivity.startActivity(i);
            }
        });
        holder.wishlist.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                DatabaseHelper2 databaseHelper = new DatabaseHelper2(mActivity);
                SQLiteDatabase mDatabase =  databaseHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(Contract2.CartItem2.COLUMN_NAME,name.get(position));
                cv.put(Contract2.CartItem2.COLUMN_PIC,url.get(position));
                cv.put(Contract2.CartItem2.COLUMN_PRICE,price.get(position));
                cv.put(Contract2.CartItem2.COLUMN_PID,pid.get(position));
                cv.put(Contract2.CartItem2.COLUMN_DESC,shrt.get(position));
                mDatabase.insert(Contract2.CartItem2.TABLE_NAME,null,cv);
            }

            @Override
            public void unLiked(LikeButton likeButton) {

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
    LikeButton wishlist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.productpic);
            nameview = itemView.findViewById(R.id.name);
            priceview = itemView.findViewById(R.id.price);
            wishlist = itemView.findViewById(R.id.star_button);
        }
    }
}
