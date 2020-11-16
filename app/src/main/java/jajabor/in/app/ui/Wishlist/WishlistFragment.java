package jajabor.in.app.ui.Wishlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.vlonjatg.progressactivity.ProgressRelativeLayout;

import jajabor.in.app.Helper.Contract2;
import jajabor.in.app.Helper.DatabaseHelper2;
import jajabor.in.app.R;


public class WishlistFragment extends Fragment {
    WishlistAdapter mWishlistAdapter;
    GridView mGridView;
    SQLiteDatabase mDatabase;
    Cursor mCursor;
    DatabaseHelper2 databaseHelper;
    ProgressRelativeLayout mLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGridView = null;
        mWishlistAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_wishlist, container, false);
        databaseHelper = new DatabaseHelper2(getContext());
        mLayout = root.findViewById(R.id.emptyActivity);
        mDatabase = databaseHelper.getWritableDatabase();
        mCursor = getAllItems();
        mGridView = root.findViewById(R.id.wishgrid);
        mWishlistAdapter = new WishlistAdapter(getContext(),getAllItems(),getActivity());
        mWishlistAdapter.swapCursor(getAllItems());
        if(mCursor.getCount() != 0){
            mLayout.showContent();
        }else
            mLayout.showEmpty(R.drawable.ic_wishlist,"WishList is Empty","Add Items to wishlist");

        mGridView.setAdapter(mWishlistAdapter);
        return root;
    }
    private Cursor getAllItems() {
        return mDatabase.query(
                Contract2.CartItem2.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}