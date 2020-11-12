package jajabor.in.app.Adapters;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class AdapterSlider2 extends SliderAdapter {
    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-05.jpg?alt=media&token=c8c61c11-24aa-46e9-97c9-01d008bf4c2c");
                break;
            case 1:
                viewHolder.bindImageSlide("https://firebasestorage.googleapis.com/v0/b/jajabor-android.appspot.com/o/banners%20for%20app-04.jpg?alt=media&token=36040564-0e81-4f22-9b37-aeb31dfeefa3");
                break;
        }
    }
}
