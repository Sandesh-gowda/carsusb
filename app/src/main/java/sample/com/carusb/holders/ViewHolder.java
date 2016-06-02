package sample.com.carusb.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sample.com.carusb.R;
import sample.com.carusb.helper.RecyclerView_OnClickListener;

/**
 * Created by Dell on 4/1/2016.
 */
public class ViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {
    // View holder for gridview recycler view as we used in listview
    public TextView product_make, product_model_kms, product_estimated_price,product_model,location_state,contact_no;
    public ImageView product_image,dropdownImage;
    public CardView cardView;
    public LinearLayout myStockEdit;
    private RecyclerView_OnClickListener.OnClickListener onClickListener;
    private Button edit,delete,sold;

    public ViewHolder(View view) {
        super(view);
        // Find all views ids

        this.product_image = (ImageView) view.findViewById(R.id.product_image);
        this.dropdownImage = (ImageView) view.findViewById(R.id.product_option);
        this.product_make = (TextView) view.findViewById(R.id.product_make);
        this.product_model_kms = (TextView) view.findViewById(R.id.product_model_kms);
        this.product_estimated_price = (TextView) view.findViewById(R.id.product_estimated_prcie);
        this.product_model = (TextView) view.findViewById(R.id.product_model);
        this.contact_no = (TextView) view.findViewById(R.id.contact_number);
        this.location_state = (TextView) view.findViewById(R.id.loction_state);
        this.cardView =(CardView)view.findViewById(R.id.my_stock_card_view);
        this.myStockEdit =(LinearLayout)view.findViewById(R.id.mystock_buttons_link);
        this.edit =(Button)view.findViewById(R.id.edit_car);
        this.delete =(Button)view.findViewById(R.id.delete_car);
        this.sold =(Button)view.findViewById(R.id.sold_car);

        this.cardView.setOnClickListener(this);
        this.edit.setOnClickListener(this);
        this.delete.setOnClickListener(this);
        this.sold.setOnClickListener(this);
       // this.grid_delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.OnItemClick(v, getAdapterPosition());

        }
    }



    // Setter for listener
    public void setClickListener(
            RecyclerView_OnClickListener.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}