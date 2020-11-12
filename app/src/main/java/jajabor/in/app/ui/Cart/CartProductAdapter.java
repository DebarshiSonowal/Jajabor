package jajabor.in.app.ui.Cart;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import jajabor.in.app.Helper.Contract;
import jajabor.in.app.Helper.Contract3;
import jajabor.in.app.R;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Cursor mCursor;
    String name,color,size,url;
    Integer price,quantity;
    public CartProductAdapter(Context context, Cursor cursor) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mCursor = cursor;
    }
    public class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView product;
        TextView nameview,priceview,sizeview,colourview,quantityview;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.cartPic);
            nameview = itemView.findViewById(R.id.cartName);
            priceview = itemView.findViewById(R.id.cartPrice);
            sizeview = itemView.findViewById(R.id.cartsize1);
            colourview = itemView.findViewById(R.id.cartColor);
            quantityview = itemView.findViewById(R.id.cartQuantity);
        }
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.cartitem,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CartViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
//        Glide.with(mContext).load( mCursor.getString(mCursor.getColumnIndex(Contract.CartItem.COLUMN_PIC))).into(holder.product);
        Picasso.get().load( mCursor.getString(mCursor.getColumnIndex(Contract3.CartItem.COLUMN_PIC))).into(holder.product);
        holder.nameview.setText( mCursor.getString(mCursor.getColumnIndex(Contract3.CartItem.COLUMN_NAME)));
        Log.d("Product", mCursor.getString(mCursor.getColumnIndex(Contract3.CartItem.COLUMN_NAME)));
        holder.quantityview.setText( mCursor.getInt(mCursor.getColumnIndex(Contract3.CartItem.COLUMN_QUANTITY))+"");
        holder.colourview.setText( mCursor.getString(mCursor.getColumnIndex(Contract3.CartItem.COLUMN_COLOR)));
        holder.sizeview.setText( mCursor.getString(mCursor.getColumnIndex(Contract3.CartItem.COLUMN_SIZE)));
        holder.priceview.setText(""+ mCursor.getInt(mCursor.getColumnIndex(Contract3.CartItem.COLUMN_PRICE)));
        holder.itemView.setTag( mCursor.getInt(mCursor.getColumnIndex(Contract3.CartItem._ID)));
    }

    @Override
    public int getItemCount() {
       return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor){
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
