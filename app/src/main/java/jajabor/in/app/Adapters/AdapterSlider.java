package jajabor.in.app.Adapters;

import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class AdapterSlider extends SliderAdapter {

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/valentines-day-banner-04-scaled.jpg");
                break;
            case 1:
                viewHolder.bindImageSlide("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/valentines-day-banner-02-scaled.jpg");
                break;
            case 2:
                viewHolder.bindImageSlide("https://d2jnb1er72blne.cloudfront.net/wp-content/uploads/2020/02/valentines-day-banner-03-scaled.jpg");
                break;
        }
    }
}
