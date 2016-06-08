package sample.com.carusb.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sample.com.carusb.R;
import sample.com.carusb.adapters.StockAdapter;
import sample.com.carusb.model.MyStock_Model;
import sample.com.carusb.model.Stock;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.End_Points;

/**
 * Created by user1 on 5/20/2016.
 */
public class AdvanceSearchResult extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<MyStock_Model> arrayList;
    private ProgressBar progressBar;
    private TextView empty_stock;
    private StockAdapter adapter;
    private String city,state,make,model,variant,mobile,reg,fuel,carprice,kms,age,trans,color;
    private String responseData;


    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = getIntent().getStringExtra("c");
         state = getIntent().getStringExtra("s");
         make = getIntent().getStringExtra("m");
         model = getIntent().getStringExtra("mo");
         variant = getIntent().getStringExtra("va");
         mobile = getIntent().getStringExtra("num");
         reg = getIntent().getStringExtra("reg");
         fuel = getIntent().getStringExtra("fu");
         carprice = getIntent().getStringExtra("carprice");
         kms = getIntent().getStringExtra("km");
         age = getIntent().getStringExtra("ag");
         trans = getIntent().getStringExtra("tr");
         color = getIntent().getStringExtra("co");




        setContentView(R.layout.my_stock_fragment);
        initRecyclerView();
        fetchDataTask();
    }


    private void fetchDataTask() {
        if (MyApplication.getInstance().IsInternetConnected()) {
            makeSearchCall(state,city,make,model,variant,mobile,reg,fuel,carprice,kms,age,trans,color);
        } else {
            noStock();
            Toast.makeText(AdvanceSearchResult.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }




    private void initRecyclerView() {
        arrayList = new ArrayList<>();

        progressBar = (ProgressBar) findViewById(R.id.my_stock_progressbar);
        empty_stock = (TextView) findViewById(R.id.empty_stock);

        recyclerView = (RecyclerView)
                findViewById(R.id.my_stock_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new GridLayoutManager(AdvanceSearchResult.this, 1));// Here 2 is no. of columns to be displayed


    }



    private void makeSearchCall(final String st,final String ci, final String mk ,final String mo,final String va, final String num,final String reg, final String fu,
                                final String carprice, final String kms, final String age , final String tra, final String co) {
        progressBar.setVisibility(View.VISIBLE);
        final User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                End_Points.ADV_SEARCH,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        responseData = response;
                        setData(response);
                        VolleyLog.d("volley", "Error: " + response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                noStock();
                Log.d("SearchFragment", "onErrorResponse : 103");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("dealer_id", user.getUser_id());
                params.put("exp_price",carprice);
                params.put("mileage",kms ); // millage == kms
                params.put("age",age);
                params.put("reg_no",reg);
               params.put("state",st);
              params.put("city",ci);
               params.put("mobile",num);
                params.put("make",mk);
                params.put("fuel",fu);
                params.put("transmission",tra);
                params.put("varient",va);
                params.put("color",co);
                params.put("model",mo);

                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(jsonObjRequest);

    }


    private void setData(String response) {
        try {
            Type listOfTestObject = new TypeToken<List<Stock>>() {
            }.getType();
            ArrayList<Stock> carsList = gson.fromJson(response, listOfTestObject);
            if (carsList == null || carsList.size() == 0) {
                noStock();
            } else {
                havingSomeStock();
                adapter = new StockAdapter(AdvanceSearchResult.this, carsList, false,false,false);
                recyclerView.setAdapter(adapter);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }



    private void noStock() {
        progressBar.setVisibility(View.GONE);
        empty_stock.setVisibility(View.VISIBLE);
        empty_stock.setText("No Cars found.");
        recyclerView.setVisibility(View.GONE);
    }

    private void havingSomeStock() {
        progressBar.setVisibility(View.GONE);
        empty_stock.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
