package sample.com.carusb.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import sample.com.carusb.R;

/**
 * Created by Dell on 4/1/2016.
 */
public class Dashboard_ViewHolder extends RecyclerView.ViewHolder {
    // View holder for gridview recycler view as we used in listview
    public ImageView dashboard_vehicle_image;


    public Dashboard_ViewHolder(View view) {
        super(view);
        // Find all views ids

        this.dashboard_vehicle_image = (ImageView) view.findViewById(R.id.dashboard_top_vehicles_image_view);



    }
}