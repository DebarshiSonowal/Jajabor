package jajabor.in.app.Adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import jajabor.in.app.Helper.Contract2;
import jajabor.in.app.Helper.DatabaseHelper;
import jajabor.in.app.R;
import jajabor.in.app.ui.Activity.ProductView;

public class GridAdapter extends BaseAdapter {
    List<String> url;
    List<String>name;
    List<String>price;
    List<String>shrt;
    List<Integer>pid;
    ImageView product;
    TextView nameview,priceview;
    private LayoutInflater mLayoutInflater;
    Activity mActivity;
    LikeButton star;
    public GridAdapter(List<String> url, List<String> name, List<String> price,List<String>shrt,List<Integer>pid, Context context,Activity mActivity) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.shrt = shrt;
        this.pid = pid;
        mLayoutInflater = LayoutInflater.from(context);
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return url.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView= mLayoutInflater.inflate(R.layout.basicproductview, null);
        product = convertView.findViewById(R.id.productpic);
        nameview = convertView.findViewById(R.id.name);
        priceview = convertView.findViewById(R.id.price);
        star = convertView.findViewById(R.id.star_button);
        Glide.with(mActivity).load(url.get(position)).error(R.drawable.error_404).dontAnimate().into(product);
//        Picasso.get().load(url.get(position)).into(product);
        nameview.setText(name.get(position));
        priceview.setText("₹"+price.get(position));
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, ProductView.class);
                i.putExtra("name",name.get(position));
                i.putExtra("url",url.get(position));
                i.putExtra("price",price.get(position));
                i.putExtra("desc",shrt.get(position));
                i.putExtra("position",position);
                i.putExtra("pid",pid.get(position));
                mActivity.startActivity(i);
            }
        });
        star.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                DatabaseHelper databaseHelper = new DatabaseHelper(mActivity);
                SQLiteDatabase  mDatabase =  databaseHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(Contract2.CartItem2.COLUMN_NAME,name.get(position));
                cv.put(Contract2.CartItem2.COLUMN_PIC,url.get(position));
                cv.put(Contract2.CartItem2.COLUMN_PRICE,price.get(position));
                cv.put(Contract2.CartItem2.COLUMN_PID,pid.get(position));
                mDatabase.insert(Contract2.CartItem2.TABLE_NAME,null,cv);
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
        return convertView;
    }
}
