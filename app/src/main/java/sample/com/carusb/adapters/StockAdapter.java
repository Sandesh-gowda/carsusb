package sample.com.carusb.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sample.com.carusb.R;
import sample.com.carusb.car_details.MyStockDetailsActivity;
import sample.com.carusb.helper.RecyclerView_OnClickListener;
import sample.com.carusb.holders.ViewHolder;
import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.Stock;
import sample.com.carusb.requests.OnitemClickLIstener;
import sample.com.carusb.utils.End_Points;
import sample.com.carusb.utils.ImageHelper;

/**
 * Created by Dell on 4/1/2016.
 */
public class StockAdapter extends
        RecyclerView.Adapter<ViewHolder> {
    private final LayoutInflater mInflater;
    // recyclerview adapter
    private ArrayList<Stock> arrayList;
    private Context context;
    private boolean isMyStock;
   private boolean isSoldCar;


    private OnitemClickLIstener onItemClickListener;

    public StockAdapter(Context context,
                        ArrayList<Stock> arrayList, boolean isMyStock,boolean isSoldCar) {
        this.context = context;
        this.arrayList = arrayList;
        this.isMyStock = isMyStock;

        this.isSoldCar = isSoldCar;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.my_stock_custom_view, viewGroup, false);
        return new ViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("StockAdapter", "onBindViewHolder : " + position);
        final Stock model = arrayList.get(position);

        holder.product_make.setText(model.getMake());
        holder.product_model.setText(model.getModel());
        holder.product_model_kms.setText(model.getVarient() + " | " + model.getYear());
        holder.location_state.setText(model.getDealer_name() + " | " + model.getCity());
        holder.product_estimated_price.setText("Rs." + model.getExp_price());

        holder.contact_no.setText(model.getMobile());


        ArrayList<String> imageList = model.getImage();
        if (imageList != null && imageList.size() > 0) {
            ImageHelper.loadImage(context, imageList.get(0), holder.product_image);
        }
        if (isMyStock) {
            holder.location_state.setVisibility(View.GONE);
            holder.contact_no.setVisibility(View.GONE);
            holder.myStockEdit.setVisibility(View.VISIBLE);
        } else {
            holder.location_state.setVisibility(View.VISIBLE);
            holder.contact_no.setVisibility(View.VISIBLE);
            holder.myStockEdit.setVisibility(View.GONE);
        }

        holder.setClickListener(new RecyclerView_OnClickListener.OnClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.edit_car:
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(StockAdapter.this, view, position);
                        }
                        break;
                    case R.id.delete_car:
                        String id = model.getId();
                        showDeleateDialog(id);
//                        makeDeleteCall(id);
                        break;
                    case R.id.sold_car:
                        String id1 = model.getId();
                        showSoldDialog(id1);
//                        makeSoldCall(id1);
                        break;
                    case R.id.my_stock_card_view:
                        Intent in = new Intent(context, MyStockDetailsActivity.class);
                        in.putExtra("Car_Data", arrayList.get(position));
                        in.putExtra("isMyStock", isMyStock);
                        in.putExtra("isSoldCar",isSoldCar);
                        context.startActivity(in);
                        break;
                }
            }
        });
    }

    private void showDeleateDialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeDeleteCall(id);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showSoldDialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sold");
        builder.setMessage("Are you sure Car is sold ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeSoldCall(id);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    private void makeDeleteCall(final String id) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                End_Points.SOLD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("StockAdapter", "onResponse : " + response);
                        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(context, "failed to deleted", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("delete", "delete");
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(jsonObjRequest);
    }


    private void makeSoldCall(final String id) {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                End_Points.SOLD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("StockAdapter", "onResponse : " + response);
                        Toast.makeText(context, "Congrats, Your car is sold", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(context, "failed to sold", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("sold", "sold");
                return params;
            }

        };
        MyApplication.getInstance().getRequestQueue().add(jsonObjRequest);
    }


    public OnitemClickLIstener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnitemClickLIstener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }





}
