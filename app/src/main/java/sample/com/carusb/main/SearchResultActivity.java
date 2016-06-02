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
 * Created by Dell on 5/2/2016.
 */
public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyStock_Model> arrayList;
    private ProgressBar progressBar;
    private TextView empty_stock;
    private StockAdapter adapter;
    private String query;
    private String responseData;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = getIntent().getStringExtra("query");
        setContentView(R.layout.my_stock_fragment);
        initRecyclerView();
        fetchDataTask();
    }

    private void fetchDataTask() {
        if (MyApplication.getInstance().IsInternetConnected()) {
            makeSearchCall(query);
        } else {
            noStock();
            Toast.makeText(SearchResultActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
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
                .setLayoutManager(new GridLayoutManager(SearchResultActivity.this, 1));// Here 2 is no. of columns to be displayed


    }

//    // populate the list view by adding data to arraylist
//    private void populateRecyclerView() {
//        adapter = new MyStockAdapter(SearchResultActivity.this, arrayList, false);
//        recyclerView.setAdapter(adapter);// set adapter on recyclerview
//        adapter.notifyDataSetChanged();// Notify the adapter
//
//    }

    private void makeSearchCall(final String message) {
        progressBar.setVisibility(View.VISIBLE);
        final User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                End_Points.SEARCH_CAR_URL,
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
                params.put("search", message);
                params.put("city", user.getUser_city());
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
                adapter = new StockAdapter(SearchResultActivity.this, carsList, false);
                recyclerView.setAdapter(adapter);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }


//    private class FetchSearchResult extends AsyncTask<Void, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            String json = null;
//            try {
//                json = new OkHttp_Class().search_car(query);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            try {
//                if (!json.equals("")) {
//                    Object jsonData = new JSONTokener(json).nextValue();
//                    if (jsonData instanceof JSONObject) {
//                        noStock();
//                    } else if (jsonData instanceof JSONArray) {
//
//
//                        for (int i = 0; i < ((JSONArray) jsonData).length(); i++) {
//
//                            JSONObject innerJson = ((JSONArray) jsonData).getJSONObject(i);
//
//                            String dealer_name = innerJson.getString("dealer_name");
//                            String make = innerJson.getString("make");
//                            String model = innerJson.getString("model");
//
//                            String varient = innerJson.getString("variant");
//                            String reg_no = innerJson.getString("reg_no");
//                            String image = innerJson.getString("image");
//
//
//                            if (image.contains("[")) {
//                                ArrayList<String> carImageArray = new ArrayList<>();
//                                if (!image.equals("")) {
//                                    JSONArray imageArray = innerJson.getJSONArray("image");
//                                    Log.e("Size", imageArray.length() + "");
//                                    for (int j = 0; j < imageArray.length(); j++) {
//                                        String carImage = imageArray.getString(j);
//                                        carImageArray.add(carImage);
//                                    }
//                                    image = StringUtils.convertToString(carImageArray);
//                                }
//                            }
//
//                            String fuel = innerJson.getString("fuel");
//                            String color = innerJson.getString("color");
//                            //    String transmission = innerJson.getString("transmission");
//                            //    String exp_date = innerJson.getString("exp_date");
//                            String city = innerJson.getString("city");
//                            String state = innerJson.getString("state");
//                            String year = innerJson.getString("age");
//                            String mileage = innerJson.getString("mileage");
//                            String owner = innerJson.getString("owner");
//                            String notes = innerJson.getString("notes");
//                            String insurance = innerJson.getString("insurance");
//                            //    String exp_date = innerJson.getString("exp_date");
//                            String exp_price = innerJson.getString("exp_price");
//                            String mobile = innerJson.getString("mobile");
//                            String rcbook_image = innerJson.getString("rc_image");
//                            String insurance_image = innerJson.getString("inc_image");
//                            //String is_buy_car = innerJson.getString("is_buy_car");
//
//                            arrayList.add(new MyStock_Model("", dealer_name, "", reg_no, make, model, year, varient, fuel, notes, color, mileage, "", owner, insurance, "", exp_price,
//                                    rcbook_image, insurance_image, "", "", image, mobile, state, city));
//
//                        }
//                        populateRecyclerView();
//                        havingSomeStock();
//                    }
//
//
//                } else
//                    noStock();
//
//            } catch (Exception e) {
//                noStock();
//                e.printStackTrace();
//                Toast.makeText(SearchResultActivity.this, "Unable to reach to server. Try again.", Toast.LENGTH_SHORT).show();
//            }
//            if (progressBar.isShown())
//                progressBar.setVisibility(View.GONE);
//        }
//    }

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
