package jajabor.in.app.Adapters;

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

import jajabor.in.app.R;

public class CategBannerAdapter extends RecyclerView.Adapter<CategBannerAdapter.ViewHolder> {
    List<String>url;
    List<String>name;
    Context mContext;
    LayoutInflater mLayoutInflater;
    public OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public CategBannerAdapter(List<String> url, List<String> name, Context context) {
        this.url = url;
        this.name = name;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.demobanner,parent,false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(url.get(position)).into(holder.productpic);
        holder.nameview.setText(name.get(position));

    }
public List<String> getname(){
        return name;
    }
    @Override
    public int getItemCount() {
        return name.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
    ImageView productpic;
    TextView nameview;
        public ViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            productpic = itemView.findViewById(R.id.categnamepic);
            nameview = itemView.findViewById(R.id.categnamebanner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
