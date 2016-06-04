package sample.com.carusb.home_fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import sample.com.carusb.R;
import sample.com.carusb.adapters.Car_Model_Adapter;
import sample.com.carusb.helper.AlertDialog_Class;
import sample.com.carusb.json_parser.OkHttp_Class;
import sample.com.carusb.main.AdvanceSearchResult;
import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.Dialog_Models;


public class SearchFragment extends Fragment implements View.OnClickListener {
    private ArrayList<String> cityArray;

    private View view;
    private TextView selectState, selectCity, selectmake, selecttransmission, selectmodel, selectvariant, selectcolor, selectFuel;
    private TextView carprice, kms, age;
    private EditText mobile , regnumber;
    private Button search_car_submit_button;
    private ArrayList<Dialog_Models> modelArrayList;
    private ArrayList<Dialog_Models> makeArrayList;
    private ArrayList<Dialog_Models> variantArrayList;
    private ProgressDialog dialog;
    private String makeId = "", modelId = "", variant_id = "";

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        setClickListeners();
    }

    private void setClickListeners() {

        selectState.setOnClickListener(this);
        selectCity.setOnClickListener(this);
        selectmake.setOnClickListener(this);
        selecttransmission.setOnClickListener(this);
        selectmodel.setOnClickListener(this);
        selectvariant.setOnClickListener(this);
        selectFuel.setOnClickListener(this);
        search_car_submit_button.setOnClickListener(this);
        selectcolor.setOnClickListener(this);
        carprice.setOnClickListener(this);
        kms.setOnClickListener(this);
        age.setOnClickListener(this);


    }


    private void initViews() {

        modelArrayList = new ArrayList<>();
        makeArrayList = new ArrayList<>();
        variantArrayList = new ArrayList<>();

        selectState = (TextView) view.findViewById(R.id.search_car_state);
        selectCity = (TextView) view.findViewById(R.id.search_car_city);
        selectmake = (TextView) view.findViewById(R.id.search_car_make);
        selecttransmission = (TextView) view.findViewById(R.id.search_car_transmission);
        selectmodel = (TextView) view.findViewById(R.id.search_car_model);
        selectvariant = (TextView) view.findViewById(R.id.search_car_variant);
        selectcolor = (TextView) view.findViewById(R.id.search_car_color);

        selectFuel = (TextView) view.findViewById(R.id.search_car_fuel);

        carprice = (TextView) view.findViewById(R.id.search_car_carprice);
        kms = (TextView) view.findViewById(R.id.search_car_kms);
        age = (TextView) view.findViewById(R.id.search_car_age);

        search_car_submit_button = (Button) view.findViewById(R.id.search_car_submit_button);

        mobile = (EditText) view.findViewById(R.id.search_car_mobile);

        regnumber = (EditText) view.findViewById(R.id.search_car_regnumber);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_car_state:
                String[] state = getActivity().getResources().getStringArray(R.array.india_states);
                AlertDialog_Class.selectStateDialog(getActivity(), selectState, state);

                break;
            case R.id.search_car_kms:
                String[] kms_ = getActivity().getResources().getStringArray(R.array.addcar_select_kms);
                AlertDialog_Class.selectStateDialog(getActivity(), kms, kms_);
                break;
            case R.id.search_car_carprice:
                String[] price = getActivity().getResources().getStringArray(R.array.addcar_select_expected_price);
                AlertDialog_Class.selectStateDialog(getActivity(), carprice, price);
                break;
            case R.id.search_car_age:
                String[] age_ = getActivity().getResources().getStringArray(R.array.addcar_select_age);
                AlertDialog_Class.selectStateDialog(getActivity(), age, age_);
                break;
            case R.id.search_car_city:
                new GetCityTask().execute();
                break;
            case R.id.search_car_make:
                if (makeArrayList.size() == 0) {
                    if (MyApplication.getInstance().IsInternetConnected())
                        new GetMake_Task().execute();
                    else
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else
                    showMakeDialog();
                break;
            case R.id.search_car_transmission:
                String[] transmission = getActivity().getResources().getStringArray(R.array.addcar_select_transmission);
                AlertDialog_Class.selectStateDialog(getActivity(), selecttransmission, transmission);
                break;
            case R.id.search_car_model:
                if (modelArrayList.size() == 0) {
                    if (MyApplication.getInstance().IsInternetConnected())
                        new GetModel_Task().execute();
                    else
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else
                    showModelDialog();
                break;
            case R.id.search_car_variant:
                if (variantArrayList.size() == 0) {
                    if (MyApplication.getInstance().IsInternetConnected())
                        new GetVariant_Task().execute();
                    else
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else
                    showVariantDialog();
                break;
            case R.id.search_car_color:
                String[] color = getActivity().getResources().getStringArray(R.array.addcar_select_color);
                AlertDialog_Class.selectStateDialog(getActivity(), selectcolor, color);
                break;

            case R.id.search_car_fuel:
                String[] fuel = getActivity().getResources().getStringArray(R.array.search_select_fuel);
                AlertDialog_Class.selectStateDialog(getActivity(),selectFuel, fuel);
                break;

            case R.id.search_car_submit_button:
                    Toast.makeText(getContext(),"Loading..........",Toast.LENGTH_LONG).show();

                String getState = selectState.getText().toString().trim();
                String getCity = selectCity.getText().toString().trim();
              //  String getMake = selectmake.getText().toString().trim();
              //  String getModel = selectmodel.getText().toString().trim();
              //  String getVariant = selectvariant.getText().toString().trim();
                String getMobile = mobile.getText().toString().trim();
                String getReg = regnumber.getText().toString().trim();
                String getFuel = selectFuel.getText().toString().trim();
                String getCarprice = carprice.getText().toString().trim();
                String getKms = kms.getText().toString().trim();
                String getAge = age.getText().toString().trim();
                if(getAge.equals("0 - 2 years") ){
                    getAge="2014-2016";
                }else if (getAge.equals("2 - 4 years")){
                    getAge="2012-2014";
                }else if (getAge.equals("4 - 6 years")){
                    getAge="2010-2012";
                }else if (getAge.equals("6 - 8 years")){
                    getAge="2008-2010";
                }else if (getAge.equals("8 +years")){
                    getAge="1900-2008";
                }else {
                    getAge="";
                }
                String getTransmission = selecttransmission.getText().toString().trim();
                String getColor = selectcolor.getText().toString().trim();
                Intent intent = new Intent(getActivity(), AdvanceSearchResult.class);
                intent.putExtra("s",getState);
                intent.putExtra("c",getCity);
                intent.putExtra("m",makeId);
                intent.putExtra("mo",modelId);
                intent.putExtra("va",variant_id);
                intent.putExtra("num",getMobile);
                intent.putExtra("reg",getReg);
                intent.putExtra("fu",getFuel);
                intent.putExtra("km",getKms);

                intent.putExtra("tr",getTransmission);
                intent.putExtra("co",getColor);
                intent.putExtra("ag",getAge);
              intent.putExtra("carprice",getCarprice);


                startActivity(intent);
                resetAllViews();
                break;


        }

    }



    private void resetAllViews() {
        regnumber.setText("");
        selectState.setText("");
        selectCity.setText("");
        selectmake.setText("");
        selectmodel.setText("");
        age.setText("");
        selectvariant.setText("");
        selectFuel.setText("");
        selectcolor.setText("");
        kms.setText("");
        selecttransmission.setText("");
        mobile.setText("");



        carprice.setText("");
        // insurance_file_name = null ;

        //  rc_book_file_name = null;




        variant_id = "";
        makeId = "";
        modelId = "";
    }

        /**
         * Getting Make, Model and Varinat Tasks
         **/

    private class GetModel_Task extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            modelArrayList = new ArrayList<>();
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                Log.e("Make Id", " - " + makeId);
                json = new OkHttp_Class().fetchCarModels(makeId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                if (jsonObject != null) {

                    JSONArray carArray = jsonObject.getJSONArray("model");
                    if (carArray != null) {
                        for (int i = 0; i < carArray.length(); i++) {
                            JSONObject innerObject = carArray.getJSONObject(i);
                            String modelName = innerObject.getString("model").trim();
                            String id = innerObject.getString("id").trim();
                            if (!modelName.equals(""))
                                modelArrayList.add(new Dialog_Models(id, modelName));
                        }
                        showModelDialog();
                    }

                } else
                    Toast.makeText(getActivity(), "Failed to load Car Models right now. Try again.", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();

            }
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void showModelDialog() {
        if (modelArrayList.size() > 0) {
//            String[] models = new String[modelArrayList.size()];
//            models = modelArrayList.toArray(models);
            // String[] models = getActivity().getResources().getStringArray(R.array.addcar_select_model);
            selectModel(getActivity(), selectmodel, modelArrayList);
        }
    }

    private class GetMake_Task extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                json = new OkHttp_Class().fetchCarMake();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                if (jsonObject != null) {
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray carArray = jsonObject.getJSONArray("car_make");
                        if (carArray != null) {
                            for (int i = 0; i < carArray.length(); i++) {
                                JSONObject innerObject = carArray.getJSONObject(i);
                                String makeName = innerObject.getString("make_name").trim();
                                String id = innerObject.getString("id").trim();
                                if (!makeName.equals(""))
                                    makeArrayList.add(new Dialog_Models(id, makeName));
                            }
                            showMakeDialog();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed to load Car Makes form right now. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getActivity(), "Failed to load Car Makes right now. Try again.", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();

            }
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void showMakeDialog() {
        if (makeArrayList.size() > 0) {
//            String[] makes = new String[makeArrayList.size()];

//            makes = makeArrayList.toArray(makes);
            selectMake(getActivity(), selectmake, makeArrayList);
        }
    }

    private class GetVariant_Task extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            variantArrayList = new ArrayList<>();
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                Log.e("Model Id", " - " + modelId);
                json = new OkHttp_Class().fetchCarVariant(modelId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                if (jsonObject != null) {

                    JSONArray carArray = jsonObject.getJSONArray("variant");
                    if (carArray != null) {
                        for (int i = 0; i < carArray.length(); i++) {
                            JSONObject innerObject = carArray.getJSONObject(i);
                            String variantName = innerObject.getString("variant").trim();
                            String id = innerObject.getString("id").trim();
                            if (!variantName.equals(""))
                                variantArrayList.add(new Dialog_Models(id, variantName));
                        }
                        showVariantDialog();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to load Car Variants form right now. Try again.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();

            }
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void showVariantDialog() {
        if (variantArrayList.size() > 0) {
            //String[] variants = new String[variantArrayList.size()];
            // variants = variantArrayList.toArray(variants);
            selectVariant(getActivity(), selectvariant, variantArrayList);
        }
    }


    public void selectMake(Context context, final TextView textView, final ArrayList<Dialog_Models> dialog_modelses) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // builder.setTitle("Select state");
        builder.setCancelable(true);

        Car_Model_Adapter adapter = new Car_Model_Adapter(context, dialog_modelses);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(dialog_modelses.get(which).getTitle());
                makeId = dialog_modelses.get(which).getId();


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void selectModel(Context context, final TextView textView, final ArrayList<Dialog_Models> dialog_modelses) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // builder.setTitle("Select state");
        builder.setCancelable(true);

        Car_Model_Adapter adapter = new Car_Model_Adapter(context, dialog_modelses);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(dialog_modelses.get(which).getTitle());
                modelId = dialog_modelses.get(which).getId();


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void selectVariant(Context context, final TextView textView, final ArrayList<Dialog_Models> dialog_modelses) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // builder.setTitle("Select state");
        builder.setCancelable(true);

        Car_Model_Adapter adapter = new Car_Model_Adapter(context, dialog_modelses);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(dialog_modelses.get(which).getTitle());
                variant_id = dialog_modelses.get(which).getId();


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private class GetCityTask extends AsyncTask<Void, Void, JSONObject> {
        private String states;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cityArray = new ArrayList<>();
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Please wait...");
            dialog.show();
            states = selectState.getText().toString().trim();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                Log.e("City", states);
                json = new OkHttp_Class().fetchCities(states);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                if (jsonObject != null) {

                    JSONArray carArray = jsonObject.getJSONArray("pincode1");
                    if (carArray != null) {
                        for (int i = 0; i < carArray.length(); i++) {
                            JSONObject innerObject = carArray.getJSONObject(i);
                            String cityName = innerObject.getString("district").trim();

                            if (!cityName.equals(""))
                                cityArray.add(cityName);
                        }
                        showCityDialog();
                    }

                } else
                    Toast.makeText(getActivity(), "Failed to fetch cities. Make sure you selected some state or you have working internet. Try again.", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();

            }
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void showCityDialog() {
        if (cityArray.size() > 0) {
            String[] cityStrings = new String[cityArray.size()];
            cityStrings = cityArray.toArray(cityStrings);
            AlertDialog_Class.selectStateDialog(getActivity(), selectCity, cityStrings);
        }
    }


    /*network call for advance search */



}




