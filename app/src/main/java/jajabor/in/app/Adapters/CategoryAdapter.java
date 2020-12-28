package jajabor.in.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import jajabor.in.app.R;

public class CategoryAdapter extends BaseAdapter {
    List<String> url;
    List<String>name;
    ImageView product;
    TextView nameview;
    Context mContext;
    private LayoutInflater mLayoutInflater;
    public CategoryAdapter(List<String> url, List<String> name, Context context) {
        this.url = url;
        this.name = name;
        mLayoutInflater= LayoutInflater.from(context);
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
        convertView= mLayoutInflater.inflate(R.layout.topweardemo, null);
        product = convertView.findViewById(R.id.categpic);
        nameview = convertView.findViewById(R.id.categname);
        Glide.with(parent.getContext()).load(url.get(position)).error(R.drawable.error_404).dontAnimate().fitCenter().into(product);
        nameview.setVisibility(View.GONE);
//        nameview.setText(name.get(position));
        return convertView;
    }
}
