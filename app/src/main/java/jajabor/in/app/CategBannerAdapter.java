package jajabor.in.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CategBannerAdapter extends RecyclerView.Adapter<CategBannerAdapter.ViewHolder> {
    List<String>url;
    List<String>name;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public CategBannerAdapter(List<String> url, List<String> name, Context context) {
        this.url = url;
        this.name = name;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.demobanner,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(url.get(position)).into(holder.productpic);
        holder.nameview.setText(name.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView productpic;
    TextView nameview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productpic = itemView.findViewById(R.id.categnamepic);
            nameview = itemView.findViewById(R.id.categnamebanner);

        }
    }
}
