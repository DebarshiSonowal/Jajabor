package jajabor.in.app.ui.contact;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;

import jajabor.in.app.R;

public class ContactFragment extends Fragment {
private ElasticImageView call,mail,loc;
private ElasticButton callbtn,mailbtn,locbtn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_contactus, container, false);
        call =view.findViewById(R.id.callview);
        mail =view.findViewById(R.id.mailview);
        loc =view.findViewById(R.id.locview);
        callbtn = view.findViewById(R.id.callbtn);
        mailbtn = view.findViewById(R.id.mailbtn);
        locbtn = view.findViewById(R.id.locbtn);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number2 = Uri.parse( "tel:"+"+918486558323");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number2);
                startActivity(callIntent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("jajabor.in@gmail.com") +
                        "?subject=" + Uri.encode("") +
                        "&body=" + Uri.encode("");
                Uri uri = Uri.parse(uriText);
                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:26.4626776,90.5568552?q=Jajabor,Borpara, Bongaigaon");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number2 = Uri.parse( "tel:"+"+918486558323");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number2);
              startActivity(callIntent);
            }
        });
        mailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("jajabor.in@gmail.com") +
                        "?subject=" + Uri.encode("") +
                        "&body=" + Uri.encode("");
                Uri uri = Uri.parse(uriText);
                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });
        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:26.4626776,90.5568552?q=Jajabor,Borpara, Bongaigaon");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        return view;
    }


}