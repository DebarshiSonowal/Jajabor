package jajabor.in.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
//    FancyButton f1,f2,f3;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_blank, container, false);
//        f1 = root.findViewById(R.id.btn_1);
//        f2 = root.findViewById(R.id.btn_2);
//        f3 = root.findViewById(R.id.btn_3);
//        f1.setIconResource(R.drawable.ic_contact_us);
//        f2.setIconResource(R.drawable.ic_mail);
//        f3.setIconResource(R.drawable.ic_location);
//        f1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri number2 = Uri.parse( "tel:"+"+918486558323");
//                Intent callIntent = new Intent(Intent.ACTION_DIAL, number2);
//                startActivity(callIntent);
//            }
//        });
//        f2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent send = new Intent(Intent.ACTION_SENDTO);
//                String uriText = "mailto:" + Uri.encode("jajabor.in@gmail.com") +
//                        "?subject=" + Uri.encode("") +
//                        "&body=" + Uri.encode("");
//                Uri uri = Uri.parse(uriText);
//                send.setData(uri);
//                startActivity(Intent.createChooser(send, "Send mail..."));
//            }
//        });
//        f3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri gmmIntentUri = Uri.parse("geo:26.4626776,90.5568552?q=Jajabor,Borpara, Bongaigaon");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
//            }
//        });

//        <?xml version="1.0" encoding="utf-8"?>
//<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:app="http://schemas.android.com/apk/res-auto"
//        xmlns:tools="http://schemas.android.com/tools"
//
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:background="#FFFFFF"
//        android:elevation="4dp"
//        tools:context=".ui.contact.ContactFragment">
//
//    <com.skydoves.elasticviews.ElasticImageView
//        android:id="@+id/callview"
//        android:layout_width="@dimen/_49sdp"
//        android:layout_height="@dimen/_49sdp"
//        android:layout_marginBottom="8dp"
//        android:scaleType="fitCenter"
//        android:tint="#00C93D3D"
//        app:imageView_duration="300"
//        app:imageView_scale="0.7"
//        app:layout_constraintBottom_toTopOf="@+id/guideline"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintHorizontal_bias="0.498"
//        app:layout_constraintStart_toStartOf="parent"
//        app:srcCompat="@drawable/ic_contact_us" />
//
//    <androidx.constraintlayout.widget.Guideline
//        android:id="@+id/guideline"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:orientation="horizontal"
//        app:layout_constraintGuide_percent="0.44" />
//
//    <com.skydoves.elasticviews.ElasticButton
//        android:id="@+id/callbtn"
//        android:layout_width="@dimen/_180sdp"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="12dp"
//        android:background="#E6E6E6"
//        android:text="+918486558323"
//        android:textColor="@android:color/widget_edittext_dark"
//        android:textSize="20sp"
//        android:textStyle="bold"
//        app:button_cornerRadius="10dp"
//        app:button_duration="250"
//        app:button_scale="0.87"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintHorizontal_bias="0.504"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="@+id/guideline" />
//
//    <com.skydoves.elasticviews.ElasticImageView
//        android:id="@+id/mailview"
//        android:layout_width="@dimen/_49sdp"
//        android:layout_height="@dimen/_49sdp"
//        android:layout_marginBottom="12dp"
//        android:scaleType="fitCenter"
//        android:tint="#00C93D3D"
//        app:imageView_duration="300"
//        app:imageView_scale="0.7"
//        app:layout_constraintBottom_toTopOf="@+id/guideline2"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintHorizontal_bias="0.501"
//        app:layout_constraintStart_toStartOf="parent"
//        app:srcCompat="@drawable/ic_mail" />
//
//    <androidx.constraintlayout.widget.Guideline
//        android:id="@+id/guideline2"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:orientation="horizontal"
//        app:layout_constraintGuide_percent="0.67" />
//
//    <com.skydoves.elasticviews.ElasticButton
//        android:id="@+id/mailbtn"
//        android:layout_width="@dimen/_180sdp"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="16dp"
//        android:background="#E6E6E6"
//        android:text="jajabor.in@gmail.com"
//        android:textColor="@android:color/widget_edittext_dark"
//        android:textSize="20sp"
//        android:textStyle="bold"
//        app:button_cornerRadius="10dp"
//        app:button_duration="250"
//        app:button_scale="0.87"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="@+id/guideline2" />
//
//    <com.skydoves.elasticviews.ElasticImageView
//        android:id="@+id/locview"
//        android:layout_width="@dimen/_49sdp"
//        android:layout_height="@dimen/_49sdp"
//        android:scaleType="fitCenter"
//        android:tint="#00C93D3D"
//        app:imageView_duration="300"
//        app:imageView_scale="0.7"
//        app:layout_constraintBottom_toTopOf="@+id/guideline3"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintHorizontal_bias="0.933"
//        app:layout_constraintStart_toStartOf="parent"
//        app:srcCompat="@drawable/ic_location" />
//
//    <androidx.constraintlayout.widget.Guideline
//        android:id="@+id/guideline3"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:orientation="horizontal"
//        app:layout_constraintGuide_percent="0.81" />
//
//    <com.skydoves.elasticviews.ElasticButton
//        android:id="@+id/locbtn"
//        android:layout_width="@dimen/_280sdp"
//        android:layout_height="@dimen/_50sdp"
//        android:layout_marginTop="36dp"
//        android:background="#E6E6E6"
//        android:text="Barpara, Near Essar Oil Depot. Bongaigaon."
//        android:textColor="@android:color/widget_edittext_dark"
//        android:textSize="20sp"
//        android:textStyle="bold"
//        app:button_cornerRadius="10dp"
//        app:button_duration="250"
//        app:button_scale="0.87"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintHorizontal_bias="0.489"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="@+id/guideline3" />
//
//    <TextView
//        android:id="@+id/textView5"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="4dp"
//        android:gravity="center"
//        android:text="Contact Us"
//        android:textColor="#020202"
//        android:textSize="@dimen/_30sdp"
//        android:textStyle="bold"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintHorizontal_bias="0.497"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="parent" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>

        return root;
    }
}