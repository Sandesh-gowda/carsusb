package sample.com.carusb.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import sample.com.carusb.R;
import sample.com.carusb.utils.ImageHelper;

/**
 * Created by Dell on 4/1/2016.
 */
public class ImageAdapter extends PagerAdapter {


    private ArrayList<String> galleryImages;
    private LayoutInflater inflater;
    private Context context;


    public ImageAdapter(Context context, ArrayList<String> galleryImages) {
        this.context = context;
        this.galleryImages = galleryImages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return galleryImages.size();
        //return galleryImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.gallery_image_view_layout, view, false);
        ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.gallery_image_view);
        String url = galleryImages.get(position);
        ImageHelper.loadImage(context, url, imageView);
        view.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}