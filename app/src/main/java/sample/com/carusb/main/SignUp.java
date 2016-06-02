package sample.com.carusb.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sample.com.carusb.R;
import sample.com.carusb.helper.AlertDialog_Class;
import sample.com.carusb.json_parser.OkHttp_Class;
import sample.com.carusb.utils.Constants;

/**
 * Created by Dell on 3/22/2016.
 */
public class SignUp extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, View.OnClickListener {
private ProgressDialog dialog;
  private static   ArrayList<String>  cityArray;
    String[] city = {};

    String[] state = {


    };

    private ProgressDialog pd;
    private static  CheckBox checkbox;
    private static TextView selectState, selectCity,chekterms;
    private static EditText name, email_id, mobile_number, pin_code;
    private static String getName = "",getMobilenNumber = "", getPinCode = "", getState = "", getCity = "",getEmailId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        city = getResources().getStringArray(R.array.india_top_places);

        state = getResources().getStringArray(R.array.india_states);
        initViews();
         checkbox = (CheckBox)findViewById(R.id.checkBox1);
        chekterms = (TextView)findViewById(R.id.checkbox_terms);

        checkbox.setText("");
       /* chekterms.setText(Html.fromHtml("I have read and agree to the " +
                "<a href='id.web.Tandc://Kode'>TERMS & CONDITIONS</a>"));
        chekterms.setClickable(true);
        chekterms.setMovementMethod(LinkMovementMethod.getInstance());*/

        setClickListeners();
    }

    public void termsOnclick(View v)
    {
        Intent in =new Intent(SignUp.this,Tandc.class);
        startActivity(in);
    }
    public void textLinkSignIn(View v)
    {
        Intent i =new Intent(SignUp.this,LoginActivity.class);
        startActivity(i);
    }
    private void setClickListeners() {
        selectState.setOnClickListener(this);
        selectCity.setOnClickListener(this);
    }

    private void initViews() {
        name = (EditText) findViewById(R.id.dealer_name);
        mobile_number = (EditText) findViewById(R.id.dealer_mobileno);
        pin_code = (EditText) findViewById(R.id.dealer_pincode);
        selectState = (TextView) findViewById(R.id.select_state);
        selectCity = (TextView) findViewById(R.id.select_city);
        email_id = (EditText) findViewById(R.id.dealer_emailid);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // List Item Dialog
    private void selectStateDialog() {

        // Items to be displayed in list

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setTitle("Select state");

        builder.setCancelable(true);

        // Setting list items over dialog and there should be no message
        builder.setItems(state, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int pos) {
                selectState.setText(state[pos]);


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    // List Item Dialog
    private void selectCityDialog() {

        // Items to be displayed in list

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setTitle("Select city");

        builder.setCancelable(true);

        // Setting list items over dialog and there should be no message
        builder.setItems(city, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int pos) {
                selectCity.setText(city[pos]);


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void signUpButtonOnClick(View v) {
        if (v.getId() == R.id.dealer_SiButton) {
            if (MyApplication.getInstance().IsInternetConnected())
                checkValidation();
            else
                Toast.makeText(SignUp.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.select_city:
               new GetCityTask().execute();
                break;
            case R.id.select_state:
                selectStateDialog();
                break;

        }
    }

    private void checkValidation() {
        getName = name.getText().toString().trim();
        getMobilenNumber = mobile_number.getText().toString().trim();
        getPinCode = pin_code.getText().toString().trim();
        getState = selectState.getText().toString().trim();
        getCity = selectCity.getText().toString().trim();
        //getEmailId = email_id.getText().toString().trim();
        Pattern p = Pattern.compile(Constants.regEx);

        Matcher m = p.matcher(getEmailId);

        if (getName.equals(""))
        {
            Toast.makeText(SignUp.this, "Your name field is empty.", Toast.LENGTH_SHORT).show();
        }
        else if (getMobilenNumber.equals(""))
        {
            Toast.makeText(SignUp.this, " your Mobile No is empty.", Toast.LENGTH_SHORT).show();
        }
        else if (getPinCode.equals(""))
        {
            Toast.makeText(SignUp.this, "your pincode is empty.", Toast.LENGTH_SHORT).show();
        }
        else if (getState.equals(""))
        {
            Toast.makeText(SignUp.this, "Please select your State", Toast.LENGTH_SHORT).show();
        }
        else if (getCity.equals(""))
        {
            Toast.makeText(SignUp.this, "Please select your City.", Toast.LENGTH_SHORT).show();
        }
       /* if (getName.equals("") || getMobilenNumber.equals("") || getPinCode.equals("") || getState.equals("")
                || getCity.equals(""))
            Toast.makeText(SignUp.this, "Some of your fields are empty.", Toast.LENGTH_SHORT).show();*/
       /* else if (!m.find())
            Toast.makeText(SignUp.this, "Invalid email id.", Toast.LENGTH_SHORT).show();*/
        else if (mobile_number.length() != 10)
            Toast.makeText(SignUp.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
        else if (getPinCode.length() != 6)
            Toast.makeText(SignUp.this, "Invalid pin code.", Toast.LENGTH_SHORT).show();
        else if(!checkbox.isChecked())
            Toast.makeText(SignUp.this, "Please check terms and conditions.", Toast.LENGTH_SHORT).show();
        else
            new SignUpTask().execute();

    }

    private class SignUpTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SignUp.this);
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage(getResources().getString(R.string.sign_up_message));
            pd.show();
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray json = null;
            try {
                json = new OkHttp_Class().dealerSignup(getName, getEmailId, getMobilenNumber, getPinCode, getCity, getState);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            if (pd.isShowing())
                pd.dismiss();
            try {

                if (jsonArray != null) {

                    String success = jsonArray.getString(0);
                    if ( success.equalsIgnoreCase("success")) {
                        String message = jsonArray.getString(1);
                        Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();

                        name.setText("");
                        email_id.setText("");
                        mobile_number.setText("");
                        pin_code.setText("");
                        successfulSignUp();

//                        String name = jsonObject.getString("dealer_name");
//                        String email = jsonObject.getString("dealer_emailid");
//                        String mobile = jsonObject.getString("dealer_mobileno");
//                        String state = jsonObject.getString("select_state");
//                        String city = jsonObject.getString("select_city");
//                        String pin_code = jsonObject.getString("pin_code");
//                        String user_id = jsonObject.getString("dealer_id");
//                        //  String dealer_id = jsonObject.getString("dealer_login");
//                        String created_at = jsonObject.getString("created_date");
//                        MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).storeUser(new User("",user_id, name, mobile, email, state, city,created_at,pin_code));
//                        successfulSignUp();
                    } else {
                        String message = jsonArray.getString(1);
                        Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(SignUp.this, "Failed to register right now. Try again.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SignUp.this, "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();


            }
        }
    }

    private void successfulSignUp() {
        Toast.makeText(SignUp.this,
                "Successfully Registered.", Toast.LENGTH_SHORT)
                .show();
        Intent i = new Intent(SignUp.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

//
//    private class SendOTPTask extends AsyncTask<Void, Void, JSONArray> {
//        String otp = "";
//        String message = "";
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pd = new ProgressDialog(SignUp.this);
//            pd.setCancelable(true);
//            pd.setCanceledOnTouchOutside(false);
//            pd.setMessage("Authenticating your mobile number..Please wait...");
//            pd.show();
//        }
//
//        @Override
//        protected JSONArray doInBackground(Void... params) {
//            JSONArray json = null;
//            try {
//                otp = String.valueOf(generateRandomNumber());
//                message = "Your OTP for CarUsb is : " + otp;
//                json = new OkHttp_Class().sendOTP(getMobilenNumber, message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute(JSONArray jsonArray) {
//            super.onPostExecute(jsonArray);
//            if (pd.isShowing())
//                pd.dismiss();
//            try {
//
//                if (jsonArray != null) {
//                    if (jsonArray.length() > 1) {
//                        Log.e("OTP", otp);
//                        Intent in = new Intent(SignUp.this, Otp.class);
//                        in.putExtra("otp", otp);
//                        in.putExtra("name", getName);
//                        in.putExtra("email", getEmailId);
//                        in.putExtra("phone", getMobilenNumber);
//                        in.putExtra("pin", getPinCode);
//                        in.putExtra("city", getCity);
//                        in.putExtra("state", getState);
//                        startActivity(in);
//                        finish();
//                    } else {
//                        JSONObject json = jsonArray.getJSONObject(0);
//                        String response = json.getString("responseCode");
//                        Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                } else
//                    Toast.makeText(SignUp.this, "Failed to authenticate right now. Try again.", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(SignUp.this, "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();
//
//
//            }
//        }
//    }
//


    private int generateRandomNumber() {
        return (int) (Math.random() * 9000) + 1000;
    }

    private class GetCityTask extends AsyncTask<Void, Void, JSONObject> {
private String states;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cityArray = new ArrayList<>();
            dialog = new ProgressDialog(SignUp.this);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Please wait...");
            dialog.show();
            states= selectState.getText().toString().trim();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
            Log.e("City",states);
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
                    Toast.makeText(SignUp.this, "Failed to fetch cities. Make sure you selected some state or you have working internet. Try again.", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SignUp.this, "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();

            }
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void showCityDialog() {
        if (cityArray.size() > 0) {
            String[] cityStrings = new String[cityArray.size()];
            cityStrings = cityArray.toArray(cityStrings);
            AlertDialog_Class.selectStateDialog(SignUp.this, selectCity, cityStrings);
        }
    }
}
