package sample.com.carusb.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Dell on 4/1/2016.
 */
public class DisplayImage_Utils {

    public static void displayUrlOverImage(Context context,String url,ImageView imageView)
    {
        /*Picasso.with(context)
                .load(url)
               // .placeholder(R.drawable.user_placeholder)
               // .error(R.drawable.user_placeholder_error)
                .into(imageView);*/


        //sumeeth used glid library

        Glide.with(context).load(url).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void displayImage(Context context, String imageUrl, ImageView userProfileImage) {
       /* Picasso.with(context)
                .load(imageUrl).fit()
                //.placeholder(R.drawable.ic_default_user)
                //.error(R.drawable.ic_default_user)
                .noFade()
                .into(userProfileImage);*/

        //glide library
        Glide.with(context).load(imageUrl).fitCenter().into(userProfileImage);
    }

    public static void displayBitmapImage(Context context, String imageUrl, ImageView userProfileImage) {


       /* Picasso.with(context)
                .load(new File(imageUrl))
                //.placeholder(R.drawable.ic_default_user)
                //.error(R.drawable.ic_default_user)
                .noFade()
                .into(userProfileImage);*/

        Glide.with(context).load(new File(imageUrl)).into(userProfileImage);

    }
}
