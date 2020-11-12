package jajabor.in.app.Helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jajabor.in.app.R;


public class SizeChartView extends AppCompatDialogFragment {

    ImageView chart;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_size_chart_view, null);
        chart = view.findViewById(R.id.sizechart);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/sizechart.jfif?alt=media&token=79ca379b-35dd-4668-8595-5dee3a039c3e")
                .placeholder(R.drawable.banner1)
                .into(chart);
        return builder.setCancelable(true).create();

    }
}