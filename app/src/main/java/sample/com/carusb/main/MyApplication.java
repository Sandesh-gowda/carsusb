package sample.com.carusb.main;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import sample.com.carusb.internet_detector.CheckInternetConnection;
import sample.com.carusb.preference_manager.MyPreferenceManager;

/**
 * Created by SONU on 05/04/16.
 */
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();
    private static MyApplication mInstance;
    private MyPreferenceManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public boolean IsInternetConnected() {
        return CheckInternetConnection.checkInternetConnection(this);

    }

    public MyPreferenceManager getPrefManager(String pref_key_value) {
        if (manager == null) {
            manager = new MyPreferenceManager(this, pref_key_value);
        }
        return manager;


    }

    private RequestQueue mRequestQueue;

    /*sumeeth code goes here*/
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}

  /*class VolleySingleton extends MyApplication {







    private RequestQueue mRequestQueue;

    private static VolleySingleton mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized VolleySingleton getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


}
*/