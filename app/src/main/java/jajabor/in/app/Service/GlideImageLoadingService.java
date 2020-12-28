package jajabor.in.app.Service;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import jajabor.in.app.R;
import ss.com.bannerslider.ImageLoadingService;

public class GlideImageLoadingService implements ImageLoadingService {
    public Context context;

    public GlideImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
//        Picasso.with(context).load(url).into(imageView);
//        Picasso.get().load(url).into(imageView);
        Glide.with(context).load(url).error(R.drawable.error_404).dontAnimate().fitCenter().into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
//        Picasso.with(context).load(resource).into(imageView);
//        Picasso.get().load(resource).into(imageView);
        Glide.with(context).load(resource).error(R.drawable.error_404).dontAnimate().fitCenter().into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
//        Picasso.with(context).load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
//        Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
        Glide.with(context).load(url).fitCenter().placeholder(placeHolder).error(errorDrawable).into(imageView);
    }
}

