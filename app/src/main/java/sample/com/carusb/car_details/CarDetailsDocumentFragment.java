package sample.com.carusb.car_details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sample.com.carusb.R;
import sample.com.carusb.model.Stock;
import sample.com.carusb.utils.ImageHelper;

@SuppressLint("ValidFragment")
public class CarDetailsDocumentFragment extends Fragment {
    private View view;
    private ImageView rc_book_image_view, insurance_image_view;
    private Stock myStock_model;


    public CarDetailsDocumentFragment() {
        //empty construct

    }


    public CarDetailsDocumentFragment(Stock myStock_model) {
        this.myStock_model = myStock_model;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car_document, container, false);
        initViews();
        showImages();
        return view;
    }

    private void initViews() {
        rc_book_image_view = (ImageView) view.findViewById(R.id.details_rc_book_image_view);
        insurance_image_view = (ImageView) view.findViewById(R.id.details_insurance_image_view);
        rc_book_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BigImageActivity.class).putExtra("path", myStock_model.getRc_image()));
            }
        });
        insurance_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BigImageActivity.class).putExtra("path", myStock_model.getInc_image()));
            }
        });
    }

    private void showImages() {
        ImageHelper.loadImage(getActivity(), myStock_model.getRc_image(), rc_book_image_view);
        ImageHelper.loadImage(getActivity(), myStock_model.getInc_image(), insurance_image_view);
    }


}
