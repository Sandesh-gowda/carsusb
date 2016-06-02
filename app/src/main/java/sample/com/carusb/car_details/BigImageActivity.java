package sample.com.carusb.car_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import sample.com.carusb.R;
import sample.com.carusb.utils.ImageHelper;

/**
 * Created by user1 on 5/5/2016.
 */
public class BigImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        ImageView image = (ImageView) findViewById(R.id.big_image);
        String path = getIntent().getStringExtra("path");

        ImageHelper.loadImage(getApplicationContext(),path,image);

    }
}
