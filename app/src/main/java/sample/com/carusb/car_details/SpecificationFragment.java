package sample.com.carusb.car_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sample.com.carusb.R;
import sample.com.carusb.model.MyStock_Model;
import sample.com.carusb.model.Stock;

/**
 * Created by Dell on 4/4/2016.
 */
@SuppressLint("ValidFragment")
public class SpecificationFragment extends Fragment {
    private View view;
    private TextView sl_no, reg_no, make, model, year, variant, fuel, color, kms, transmission, owners, insurance, expiry_date, expected_price, notes;
    private Stock myStock_model;

    public SpecificationFragment() {


    }


    public SpecificationFragment(Stock myStock_model) {
        this.myStock_model = myStock_model;

    }

    public static Fragment newInstance(MyStock_Model myStock_model) {
        Fragment f = new SpecificationFragment();
        Bundle b = new Bundle();
        b.putSerializable("data", myStock_model);
        f.setArguments(b);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.specification_fragment, container, false);
        initViews();
        displayData();
        return view;
    }

    private void initViews() {

        //   sl_no = (TextView) view.findViewById(R.id.specification_sl_no);
        reg_no = (TextView) view.findViewById(R.id.specification_reg_no);
        make = (TextView) view.findViewById(R.id.specification_make);
        model = (TextView) view.findViewById(R.id.specification_model);
        kms = (TextView) view.findViewById(R.id.specification_kms);
        year = (TextView) view.findViewById(R.id.specification_year);
        variant = (TextView) view.findViewById(R.id.specification_varient);
        fuel = (TextView) view.findViewById(R.id.specification_fuel);
        color = (TextView) view.findViewById(R.id.specification_colour);
        transmission = (TextView) view.findViewById(R.id.specification_transmission);
        owners = (TextView) view.findViewById(R.id.specification_owners);
        insurance = (TextView) view.findViewById(R.id.specification_insurance);
        expiry_date = (TextView) view.findViewById(R.id.specification_expiry_date);
        expected_price = (TextView) view.findViewById(R.id.specification_expected_price);
        notes = (TextView) view.findViewById(R.id.specification_Notes);
    }

    private void displayData() {


        reg_no.setText(myStock_model.getReg_no());
        //brand_name.setText(myStock_model.getMake());
        make.setText(myStock_model.getMake());
        kms.setText(myStock_model.getMileage());
        model.setText(myStock_model.getModel());
        year.setText(myStock_model.getYear());
        variant.setText(myStock_model.getVarient());
        fuel.setText(myStock_model.getFuel());
        color.setText(myStock_model.getColor());
        transmission.setText(myStock_model.getTransmission());
        owners.setText(myStock_model.getOwner());
        insurance.setText(myStock_model.getInsurance());
        expiry_date.setText(myStock_model.getExp_date());
        expected_price.setText(myStock_model.getExp_price());
        notes.setText(myStock_model.getNotes());

    }
}
