package jajabor.in.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    List<String> url;
    List<String>name;
    List<String>price;
    ImageView product;
    TextView nameview,priceview;
    private Context context ;
    private LayoutInflater mLayoutInflater;

    public GridAdapter(List<String> url, List<String> name, List<String> price, Context context) {
        this.url = url;
        this.name = name;
        this.price = price;
        mLayoutInflater = LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= mLayoutInflater.inflate(R.layout.basicproductview, null);
        product = convertView.findViewById(R.id.productpic);
        nameview = convertView.findViewById(R.id.name);
        priceview = convertView.findViewById(R.id.price);
        Picasso.get().load(url.get(position)).into(product);
        nameview.setText(name.get(position));
        priceview.setText(price.get(position));
        return convertView;
    }
}
