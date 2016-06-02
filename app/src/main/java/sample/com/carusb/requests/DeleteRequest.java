package sample.com.carusb.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by kalyan_pvs on 14/5/16.
 */
public class DeleteRequest extends StringRequest {
    public DeleteRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
