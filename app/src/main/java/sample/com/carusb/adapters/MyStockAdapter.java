package sample.com.carusb.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import sample.com.carusb.R;
import sample.com.carusb.car_details.MyStockDetailsActivity;
import sample.com.carusb.helper.RecyclerView_OnClickListener;
import sample.com.carusb.holders.ViewHolder;
import sample.com.carusb.home_fragments.HomeFragment;
import sample.com.carusb.json_parser.OkHttp_Class;
import sample.com.carusb.model.MyStock_Model;

/**
 * Created by Dell on 4/1/2016.
 */
public class MyStockAdapter extends
        RecyclerView.Adapter<ViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<MyStock_Model> arrayList;
    private Context context;
    private Point p;
    private boolean isMyStock;

    public MyStockAdapter(Context context,
                          ArrayList<MyStock_Model> arrayList, boolean isMyStock) {
        this.context = context;
        this.arrayList = arrayList;
        this.isMyStock = isMyStock;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MyStock_Model model = arrayList.get(position);
        final String soldID = model.getId();
        holder.product_make.setText(model.getMake());
        holder.product_model.setText(model.getModel());
        holder.product_model_kms.setText(model.getVariant() + " | " + model.getYear());
        holder.location_state.setText(model.getDealer_name() + " | " + model.getCity());
        holder.product_estimated_price.setText("Rs." + model.getExp_price());

        holder.contact_no.setText(model.getMobile());

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

//                        Fragment addCarFragment = new HomeFragment().getFragment(1);
//                        //Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.mainViewPager + ":" + 2);
//                        if (addCarFragment != null)
//                            ((AddCarFragment) addCarFragment).setEditData(arrayList.get(position));
//
//                        else Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        new HomeFragment().setCurrentFragment(1);


                        break;
                    case R.id.delete_car:
                        new Delete(soldID).execute();
                        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.sold_car:
                        new Sold(soldID).execute();
                        Toast.makeText(context, "Congrats, Your car is sold", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.my_stock_card_view:
                        Intent in = new Intent(context, MyStockDetailsActivity.class);
                        in.putExtra("Car_Data", arrayList.get(position));
                        in.putExtra("isMyStock", isMyStock);
                        context.startActivity(in);
                        break;
                }
            }
        });

//        if (!model.getImage().equals("")){
//            ImageLoader.getInstance().displayImage(StringUtils.convertToArray(model.getImage()).get(0), holder.product_image);
//        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.my_stock_custom_view, viewGroup, false);
        return new ViewHolder(mainGroup);

    }

    private class Sold extends AsyncTask<Void, Void, JSONObject> {
        String soldID;

        public Sold(String soldID) {
            this.soldID = soldID;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {

                json = new OkHttp_Class().sold(soldID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
    }



    /*delete the car */


    private class Delete extends AsyncTask<Void, Void, JSONObject> {
        String deleteID;

        public Delete(String deleteID) {
            this.deleteID = deleteID;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {

                json = new OkHttp_Class().delete(deleteID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
    }

}
