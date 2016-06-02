package sample.com.carusb.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import sample.com.carusb.R;
import sample.com.carusb.json_parser.OkHttp_Class;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;

/**
 * Created by Dell on 3/25/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private static EditText dealer_mobile, dealer_password;
    private static String getDealerMobile = "", getPassword = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initViews();
    }

    private void initViews() {
        dealer_mobile = (EditText) findViewById(R.id.dealer_mobile_number);
        dealer_password = (EditText) findViewById(R.id.dealer_password);
    }

    public void dpOnClick(View v) {
        if (v.getId() == R.id.dealer_loginButton) {
            if (MyApplication.getInstance().IsInternetConnected())
               // loginSuccessful();
               checkValidation();
            else
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();


        }
    }

    private void checkValidation() {
        getDealerMobile = dealer_mobile.getText().toString().trim();
        getPassword = dealer_password.getText().toString().trim();

        if (!getDealerMobile.equals("") && !getPassword.equals(""))
            new LoginTask().execute();
        else
            Toast.makeText(LoginActivity.this, "Both fields are required.", Toast.LENGTH_SHORT).show();
    }

    private void loginSuccessful() {
        Toast.makeText(LoginActivity.this,
                "Login Successful.", Toast.LENGTH_SHORT)
                .show();
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void suOnclick(View v) {
        if (v.getId() == R.id.dealer_signup) {
            Intent i = new Intent(LoginActivity.this, SignUp.class);
            startActivity(i);
        }
    }

    public void fpOnclick(View v) {
        if (v.getId() == R.id.dealer_forgotpwd) {
            Intent i = new Intent(LoginActivity.this, Fpassword.class);
            startActivity(i);
        }
    }

    private class LoginTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginActivity.this);
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage(getResources().getString(R.string.login_message));
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                json = new OkHttp_Class().loginUser(getDealerMobile, getPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (pd.isShowing())
                pd.dismiss();
            try {

                if (jsonObject != null) {

                    int success = jsonObject.getInt("success");
                    if (success == 1) {

                        String name = jsonObject.getString("dealer_name");
                        String email = jsonObject.getString("email");
                        String mobile = jsonObject.getString("mobile");
                        String state = jsonObject.getString("state");
                        String city = jsonObject.getString("city");
                        String dealer_id = jsonObject.getString("user_id");
                        String user_id = jsonObject.getString("dealer_login");
                        String created_at = jsonObject.getString("created_date");
                     //   String pin_code = jsonObject.getString("pin_code");
Log.e("Data",city+" - -"+ state);
                        MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).storeUser(new User(dealer_id,user_id, name, mobile,"", email, state, city,created_at));
                        loginSuccessful();
                    } else {
                        String message = jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(LoginActivity.this, "Failed to login right now. Try again.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();


            }
        }
    }


}