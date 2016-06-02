package sample.com.carusb.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import sample.com.carusb.R;
import sample.com.carusb.holders.Dashboard_ViewHolder;
import sample.com.carusb.model.Image;
import sample.com.carusb.model.dashboard_car;
import sample.com.carusb.utils.ImageHelper;

/**
 * Created by Dell on 4/1/2016.
 */
public class DashboardAdapter extends
        RecyclerView.Adapter<Dashboard_ViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<Image> arrayList;
    private Context context;

    public DashboardAdapter(Context context,
                            ArrayList<Image> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    public DashboardAdapter(FragmentActivity activity, ArrayList<dashboard_car> arrayList, boolean b) {


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
        // return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(Dashboard_ViewHolder holder, int position) {
        Image image = arrayList.get(position);
        ImageHelper.loadImage(context, image.getImage(), holder.dashboard_vehicle_image);
    }

    @Override
    public Dashboard_ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(context);

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.dashboard_custom_view, viewGroup, false);
        return new Dashboard_ViewHolder(mainGroup);

    }

}