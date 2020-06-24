package jajabor.in.app.ui.Men;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import jajabor.in.app.GridAdapter;
import jajabor.in.app.R;

public class MenFragment extends Fragment {
    List<String> url;
    List<String>name;
    List<String>price;
    GridAdapter mGridAdapter;
private GridView mGridView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_men, container, false);
        url = new ArrayList<>();
        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Axomiya-mur-prothom-porichoy-Unisex-womens-Assamese-Tshirt-524x658.jpg");
        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Akolxoriya-Unisex-Womens-Assamese-Tshirt-524x658.jpg");
        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/Sangeetei-mur-jibonMusic-is-my-Life-Assamese-Tshirt-524x658.jpg");
        url.add("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/01/Dusto-Lora-524x658.png");

        name = new ArrayList<>();
        name.add("Axomiya Mur Prothom Porichoy Assamese Tshirt");
        name.add("Akolxoriya Assamese Tshirt");
        name.add("Sangeetei mur Jibon Assamese Tshirt");
        name.add("Dusto Lora Assamese Tshirt");

        price = new ArrayList<>();
        price.add("₹299.00");
        price.add("₹299.00");
        price.add("₹299.00");
        price.add("₹299.00");


        mGridView = root.findViewById(R.id.mangrid);
        mGridAdapter = new GridAdapter(url,name,price,getContext());
        mGridView.setAdapter(mGridAdapter);

        return root;
    }
}