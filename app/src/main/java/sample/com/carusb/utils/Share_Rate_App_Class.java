package sample.com.carusb.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import sample.com.carusb.R;


/**
 * Created by SONU on 01/10/15.
 */
public class Share_Rate_App_Class {
    // Share text
    public void shareText(Context context) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");// Plain format text

        // You can add subject also
 /*
 * sharingIntent.putExtra( android.content.Intent.EXTRA_SUBJECT,
 * "Subject Here");
 */
        sharingIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_text));
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_text_choose_option)));
    }

    public void RateApp(Context context) {
// Rate App
        try {
            context
                    .startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("market://details?id="
                                            + "sample.com.carusb")));
        } catch (android.content.ActivityNotFoundException anfe) {
            context
                    .startActivity(
                            new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id="
                                            + "sample.com.carusb")));
        }

    }
}
