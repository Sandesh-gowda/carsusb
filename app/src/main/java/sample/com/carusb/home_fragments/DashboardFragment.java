package sample.com.carusb.home_fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sample.com.carusb.R;
import sample.com.carusb.adapters.DashboardAdapter;
import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.DashBoard;
import sample.com.carusb.model.Image;
import sample.com.carusb.model.ImageList;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.End_Points;

/**
 * Created by Dell on 4/1/2016.
 */
public class DashboardFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> arrayList;

    private ProgressDialog dialog;
    private DashboardAdapter adapter;

    private TextView cmts, cits, cmts1, cits1;
    private String responseData;
    private Gson gson = new Gson();


    public DashboardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        initRecyclerView();

        cmts = (TextView) view.findViewById(R.id.current_month_total_stock);
        cits = (TextView) view.findViewById(R.id.current_inventory_total_stock);
        cmts1 = (TextView) view.findViewById(R.id.current_month_total_sold);
        cits1 = (TextView) view.findViewById(R.id.current_inventory_total_sold);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Retrieving...Please wait...");

        cars();
    }

    private void cars() {
        if (MyApplication.getInstance().IsInternetConnected()) {
            makeImageListCall();
            makeDashBoardCall();
        } else
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

    }

    private void makeDashBoardCall() {
        dialog.show();
        final User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                End_Points.DASHBOARD_URL,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        Log.d("DashboardFragment", "onResponse : " + response);
                        setDashBoardData(response);
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                Log.d("HubFragment", "onErrorResponse : 103");
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
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(jsonObjRequest);

    }

    private void setDashBoardData(String response) {
        DashBoard dashBoard = gson.fromJson(response, DashBoard.class);
        if (dashBoard != null) {
            cmts.setText(dashBoard.getCurrentMonthTotalStock());
            cits.setText(dashBoard.getCurrentInventoryTotalStock());
            cmts1.setText(dashBoard.getCurrentMonthTotalSold());
            cits1.setText(dashBoard.getCurrentInventoryTotalSoldCars());
        }
    }

    private void makeImageListCall() {
        final User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                End_Points.DASHBOARD_IMAGES,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        responseData = response;
                        setImageData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                Log.d("HubFragment", "onErrorResponse : 103");
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
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(jsonObjRequest);

    }

    private void setImageData(String response) {
        ImageList imageList = gson.fromJson(response, ImageList.class);
        ArrayList<Image> cars = imageList.getCars();

        adapter = new DashboardAdapter(getActivity(), cars);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView)
                view.findViewById(R.id.dashboard_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));// Here 2 is no. of columns to be displayed

    }

}
