package sample.com.carusb.main;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import sample.com.carusb.R;
import sample.com.carusb.json_parser.OkHttp_Class;


/**
 * Created by Dell on 3/22/2016.
 */
public class Fpassword extends AppCompatActivity {

    private static String getMobileNumber = "";
    private ProgressDialog pd;
    private static EditText mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fppassword);
    }

    public void submitButtonOnclick(View v) {
        if (v.getId() == R.id.SUBMITBUTTON) {
            if (MyApplication.getInstance().IsInternetConnected())
                checkValidation();
            else
                Toast.makeText(Fpassword.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }


    private void checkValidation() {

        mobileNumber = (EditText) findViewById(R.id.dealer_emailid);
        getMobileNumber = mobileNumber.getText().toString().trim();



        if (getMobileNumber.equals(""))
            Toast.makeText(Fpassword.this, "Please enter your registered mobile number.", Toast.LENGTH_SHORT).show();
        else if (getMobileNumber.length()!=10)
            Toast.makeText(Fpassword.this, "Invalid mobile number.", Toast.LENGTH_SHORT).show();
        else
            new FpasswordTask().execute();

    }

    private class FpasswordTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Fpassword.this);
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage(getResources().getString(R.string.forgot_password_message));
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                json = new OkHttp_Class().forgotPassword(getMobileNumber);
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
                        String message = jsonObject.getString("message");
                        Toast.makeText(Fpassword.this, message, Toast.LENGTH_SHORT).show();
                        mobileNumber.setText("");
                    } else {
                        String message = jsonObject.getString("message");
                        Toast.makeText(Fpassword.this, message, Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(Fpassword.this, "Failed to get password right now. Try again.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Fpassword.this, "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();


            }
        }
    }


}

