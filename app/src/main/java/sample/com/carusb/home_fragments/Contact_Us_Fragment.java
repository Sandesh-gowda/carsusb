package sample.com.carusb.home_fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import sample.com.carusb.R;
import sample.com.carusb.json_parser.OkHttp_Class;
import sample.com.carusb.main.MyApplication;

/**
 * Created by Dell on 4/5/2016.
 */
public class Contact_Us_Fragment extends Fragment {
    private static View view;

    private static EditText name, email, phone, message;
    private static Button contact_us_submit_button;
    private ProgressDialog dialog;

    public Contact_Us_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contactus_fragment, container, false);
        initViews();
        return view;
    }

    private void initViews() {


        name = (EditText) view.findViewById(R.id.contact_us_name);
        email = (EditText) view.findViewById(R.id.contact_us_email);
        phone = (EditText) view.findViewById(R.id.contact_us_phone);
        message = (EditText) view.findViewById(R.id.contact_us_message);


        contact_us_submit_button = (Button) view.findViewById(R.id.contact_us_submit_button);
        contact_us_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {


        String getName = name.getText().toString().trim();
        String getEmail = email.getText().toString().trim();
        String getPhone = phone.getText().toString().trim();
        String getMessage = message.getText().toString().trim();


        if (getName.equals("") || getEmail.equals("") || getPhone.equals("") || getMessage.equals(""))
            Toast.makeText(getActivity(), "All fields are required.", Toast.LENGTH_SHORT).show();
        else {
            if (MyApplication.getInstance().IsInternetConnected())
                new ContactUsTask(getName, getEmail, getMessage, getPhone).execute();
            else
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private class ContactUsTask extends AsyncTask<Void, Void, JSONObject> {
        private String getName, getEmail, getMessage, getPhone;


        public ContactUsTask(String getName, String getEmail, String getMessage, String getPhone) {
            this.getName = getName;
            this.getEmail = getEmail;
            this.getMessage = getMessage;
            this.getPhone = getPhone;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Contacting...Please wait...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json = null;
            try {
                json = new OkHttp_Class().contact_us(getName, getEmail, getPhone, getMessage);
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
                    resetAllViews();
                    Toast.makeText(getActivity(), "Contact us done.", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getActivity(), "Failed to Contact right now. Try again.", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Unknown error occurred. Try again.", Toast.LENGTH_SHORT).show();

            }
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void resetAllViews() {
        name.setText("");
        email.setText("");
        phone.setText("");
        message.setText("");


    }
}