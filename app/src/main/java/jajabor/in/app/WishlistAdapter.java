package jajabor.in.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

class WishlistAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Cursor mCursor;
    Activity mActivity;
    ImageView product;
    LikeButton star;
    TextView nameview,priceview;
    public WishlistAdapter(Context context, Cursor cursor, Activity mActivity) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mCursor = cursor;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return  mCursor.getCount();
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
        if (!mCursor.moveToPosition(position)) {
            return convertView;
        }
        convertView= mLayoutInflater.inflate(R.layout.basicproductview, null);
        product = convertView.findViewById(R.id.productpic);
        nameview = convertView.findViewById(R.id.name);
        priceview = convertView.findViewById(R.id.price);
        star = convertView.findViewById(R.id.star_button);
        Glide.with(mActivity).load(mCursor.getString(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_PIC))).into(product);
        priceview.setText(""+ mCursor.getInt(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_PRICE)));
        nameview.setText(mCursor.getString(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_NAME)));
        convertView.setTag( mCursor.getInt(mCursor.getColumnIndex(Contract2.CartItem2._ID)));
        star.setLiked(true);
        star.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,ProductView.class);
                i.putExtra("name",mCursor.getString(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_NAME)));
                i.putExtra("url",mCursor.getString(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_PIC)));
                i.putExtra("price",mCursor.getString(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_PRICE)));
                i.putExtra("desc",mCursor.getString(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_DESC)));
                i.putExtra("position",position);
                i.putExtra("pid",mCursor.getInt(mCursor.getColumnIndex(Contract2.CartItem2.COLUMN_PID)));
                mActivity.startActivity(i);
            }
        });
        return convertView;
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
    public LikeButton getbtn(){
        return star;
    }
}
