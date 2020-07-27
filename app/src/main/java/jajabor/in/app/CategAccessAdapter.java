package jajabor.in.app;

import android.app.Activity;
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

public class CategAccessAdapter extends BaseAdapter {
    List<String> url;
    ImageView product;
    Context mContext;
    Activity mActivity;
    private LayoutInflater mLayoutInflater;
    public CategAccessAdapter(List<String> url, Context context,Activity mActivity) {
        this.mActivity = mActivity;
        this.url = url;
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
        convertView= mLayoutInflater.inflate(R.layout.accessorydemo, null);
        product = convertView.findViewById(R.id.accesspic);
        Glide.with(mActivity).load(url.get(position)).into(product);
//        nameview.setText(name.get(position));
        return convertView;
    }
}
