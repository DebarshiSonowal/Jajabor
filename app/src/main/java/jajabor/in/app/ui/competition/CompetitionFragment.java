package jajabor.in.app.ui.competition;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jajabor.in.app.R;

public class CompetitionFragment extends Fragment {

    //Insan Budi Maulana
    WebView comp;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_comptition, container, false);
        comp = root.findViewById(R.id.compition);
        comp.loadUrl("https://jajabor.in/tshirt-design-competition/");
        comp.getSettings().setJavaScriptEnabled(true);
        comp.getSettings().setBuiltInZoomControls(true);
        comp.getSettings().setLoadsImagesAutomatically(true);
        comp.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}