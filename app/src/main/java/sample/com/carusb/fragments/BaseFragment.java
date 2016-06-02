package sample.com.carusb.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sample.com.carusb.adapters.StockAdapter;
import sample.com.carusb.home_fragments.MyStockFragment;
import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.Stock;
import sample.com.carusb.model.StockList;

/**
 * Created by kalyan_pvs on 14/5/16.
 */
public class BaseFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected ProgressBar progressBar;
    protected TextView empty_stock;

    protected Gson gson = new Gson();
    protected StockAdapter adapter;
    protected String responseData;
    protected ArrayList<Stock> carsList;


    protected void makeCall(String url, final HashMap<String, String> params) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("BaseFragment", "onResponse : " + response);
                        responseData = response;
                        setData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                noStock();
                Log.d("BaseFragment", "onErrorResponse : 61");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

        };
        RequestQueue requestQueue = MyApplication.getInstance().getRequestQueue();
        requestQueue.cancelAll(url);
        jsonObjRequest.setTag(url);
        requestQueue.add(jsonObjRequest);
    }


    protected void setData(String response) {
        try {
            if (isAdded()) {
                StockList stockList = gson.fromJson(response, StockList.class);
                carsList = stockList.getCars();
                if (carsList == null || carsList.size() == 0) {
                    noStock();
                } else {
                    havingSomeStock();
                    boolean isMyStock = false;
                    if (this instanceof MyStockFragment) {
                        isMyStock = true;
                    }
                    adapter = new StockAdapter(getActivity(), carsList, isMyStock);
                    recyclerView.setAdapter(adapter);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            noStock();
        }
    }


    protected void noStock() {
        empty_stock.setVisibility(View.VISIBLE);
        empty_stock.setText("Currently you don\'t have any Stock.");
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    protected void havingSomeStock() {
        empty_stock.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void onFragmentVisible() {

    }
}
