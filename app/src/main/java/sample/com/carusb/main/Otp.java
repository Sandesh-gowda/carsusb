package sample.com.carusb.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sample.com.carusb.R;


/**
 * Created by Dell on 3/22/2016.
 */
public class Otp extends AppCompatActivity {

    private String getotp = "";

    private static EditText otpBox;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        otpBox = (EditText) findViewById(R.id.otp_box);
    }

    public void submitButtonOnclick(View v) {
        if (v.getId() == R.id.otp_submit_button) {
            checkValidation();

        }
    }


    private void checkValidation() {
        Log.e("OTP", getIntent().getStringExtra("otp"));

        getotp = otpBox.getText().toString().trim();
//        if (getotp.equals(getIntent().getStringExtra("otp"))) {
//            if (MyApplication.getInstance().IsInternetConnected())
//                new SignUpTask().execute();
//            else
//                Toast.makeText(Otp.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
//        } else
//
//            Toast.makeText(Otp.this, "Wrong OTP entered.", Toast.LENGTH_SHORT).show();


    }
//
//    private class SignUpTask extends AsyncTask<Void, Void, JSONObject> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pd = new ProgressDialog(Otp.this);
//            pd.setCancelable(true);
//            pd.setCanceledOnTouchOutside(false);
//            pd.setMessage(getResources().getString(R.string.sign_up_message));
//            pd.show();
//        }
//
//        @Override
//        protected JSONObject doInBackground(Void... params) {
//            JSONObject json = null;
//            try {
//                json = new OkHttp_Class().dealerSignup(getIntent().getStringExtra("name"), getIntent().getStringExtra("email"), getIntent().getStringExtra("phone"), getIntent().getStringExtra("pin"), getIntent().getStringExtra("city"), getIntent().getStringExtra("state"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            super.onPostExecute(jsonObject);
//            if (pd.isShowing())
//                pd.dismiss();
//            try {
//
//                if (jsonObject != null) {
//
//                    int success = jsonObject.getInt("success");
//                    if (success == 1) {
//                        String name = jsonObject.getString("dealer_name");
//                        String email = jsonObject.getString("dealer_emailid");
//                        String mobile = jsonObject.getString("dealer_mobileno");
//                        String state = jsonObject.getString("select_state");
//                        String city = jsonObject.getString("select_city");
//                        String pin_code = jsonObject.getString("pin_code");
//                        String user_id = jsonObject.getString("dealer_id");
//                      //  String dealer_id = jsonObject.getString("dealer_login");
//                        String created_at = jsonObject.getString("created_date");
//                        MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).storeUser(new User("",user_id, name, mobile, email, state, city,created_at,pin_code));
//                        successfulSignUp();
//                    } else {
//                        String message = jsonObject.getString("message");
//                        Toast.makeText(Otp.this, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                } else
//                    Toast.makeText(Otp.this, "Failed to register right now. Try again.", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(Otp.this, "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();
//
//
//            }
//        }
//    }

    private void successfulSignUp() {
        Toast.makeText(Otp.this,
                "Successfully Registered.", Toast.LENGTH_SHORT)
                .show();
        Intent i = new Intent(Otp.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}




