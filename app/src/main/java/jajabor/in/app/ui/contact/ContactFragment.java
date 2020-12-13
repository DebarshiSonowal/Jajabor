package jajabor.in.app.ui.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.nikartm.button.FitButton;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;

import br.com.bloder.magic.view.MagicButton;
import info.hoang8f.widget.FButton;
import jajabor.in.app.R;

public class ContactFragment extends Fragment {
    private MagicButton callbtn,mailbtn,locbtn,fbtn,instabtn,tbtn;
    private Intent intentAiguilleur;
    private  View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contactus, container, false);

        callbtn = view.findViewById(R.id.call_btn);
        mailbtn = view.findViewById(R.id.gmail_btn);
        locbtn = view.findViewById(R.id.gmap_btn);
        fbtn = view.findViewById(R.id.facebook_btn);
        tbtn = view.findViewById(R.id.twitter_btn);
        instabtn = view.findViewById(R.id.insta_btn);

        callbtn.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number2 = Uri.parse( "tel:"+"+918486558323");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number2);
                startActivity(callIntent);
            }
        });
       mailbtn.setMagicButtonClickListener(new View.OnClickListener() {
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
       locbtn.setMagicButtonClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Uri gmmIntentUri = Uri.parse("geo:26.4626776,90.5568552?q=Jajabor,Borpara, Bongaigaon");
               Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
               mapIntent.setPackage("com.google.android.apps.maps");
               startActivity(mapIntent);
           }
       });
        fbtn.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/1735926303287804"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/jajabor.in")));
                }
            }
        });
     instabtn.setMagicButtonClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String scheme = "http://instagram.com/_u/jajabor.in";
             String path = "https://www.instagram.com/jajabor.in";
             String nomPackageInfo ="com.instagram.android";
             try {
                 getActivity().getPackageManager().getPackageInfo(nomPackageInfo, 0);
                 intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
             } catch (Exception e) {
                 intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
             }
             getActivity().startActivity(intentAiguilleur);
         }
     });
      tbtn.setMagicButtonClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              try {
                  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "@jajaborshopping")));
              }catch (Exception e) {
                  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + "@jajaborshopping")));
              }
          }
      });
        return view;
    }


}