package sample.com.carusb.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import sample.com.carusb.R;
import sample.com.carusb.utils.Constants;

/**
 * Created by Dell on 3/25/2016.
 */
public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final ImageView iv = (ImageView) findViewById(R.id.splashimage);


        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);


        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                iv.startAnimation(an2);

                Intent i;
                if (MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser() != null)
                    i = new Intent(Splashscreen.this, MainActivity.class);
                else
                    i = new Intent(Splashscreen.this, LoginActivity.class);
                startActivity(i);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
