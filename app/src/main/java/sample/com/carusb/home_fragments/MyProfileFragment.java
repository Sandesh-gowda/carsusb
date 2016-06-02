package sample.com.carusb.home_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

import sample.com.carusb.R;
import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.BitmapDecoder;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.views.CircleImageView;

/**
 * Created by Dell on 4/5/2016.
 */
public class MyProfileFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private View view;
    private EditText name, address, email;
    private TextView user_name, dealer_id, reg_date, selectState, selectCity, mobile_no, pin_code;
    private Button my_profile_submit;
    private ImageView edit_profile;
    private CircleImageView profileImage;
    public final int SELECT_FILE = 8;

    public MyProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myprofile_fragment, container, false);
        initViews();
        setData();
        enableDisableEditing(false);
        setClickListeners();
        return view;
    }

    private void selectImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select Image"),
                SELECT_FILE);
    }

    private void setClickListeners() {
        profileImage.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        my_profile_submit.setOnClickListener(this);
        // selectCity.setOnClickListener(this);
//
        // my_profile_submit.setOnClickListener(this);
        //  user_name.setOnClickListener(this);
        //  dealer_id.setOnClickListener(this);
        // reg_date.setOnClickListener(this);
    }


    private void initViews() {

        profileImage = (CircleImageView) view.findViewById(R.id.my_profile_dp);
        edit_profile = (ImageView) view.findViewById(R.id.edit_profile);


        selectState = (TextView) view.findViewById(R.id.my_profile_name_state);
        selectCity = (TextView) view.findViewById(R.id.my_profile_city);
        user_name = (TextView) view.findViewById(R.id.my_profile_user_name);
        dealer_id = (TextView) view.findViewById(R.id.my_profile_dealer_id);
        reg_date = (TextView) view.findViewById(R.id.my_profile_reg_date);
        pin_code = (TextView) view.findViewById(R.id.my_profile_pincode);


        name = (EditText) view.findViewById(R.id.my_profile_name);
        address = (EditText) view.findViewById(R.id.my_profile_address);
        mobile_no = (TextView) view.findViewById(R.id.my_profile_mobile_no);
        email = (EditText) view.findViewById(R.id.my_profile_email);

        my_profile_submit = (Button) view.findViewById(R.id.my_profile_submit);


    }

    private void setData() {
        User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();

        selectCity.setText(user.getUser_city());
        selectState.setText(user.getUser_state());
        user_name.setText(user.getUser_name());
        if (!user.getDealer_id().equals("")) {
            dealer_id.setText(user.getDealer_id());
            dealer_id.setVisibility(View.VISIBLE);
        } else
            dealer_id.setVisibility(View.GONE);
        reg_date.setText(user.getCreated_at());
        name.setText(user.getUser_name());
        mobile_no.setText(user.getUser_mobile());
        email.setText(user.getUser_email());
        pin_code.setText(user.getUser_pin_code());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_profile_user_name:

                break;
            case R.id.my_profile_dealer_id:

                break;
            case R.id.my_profile_reg_date:
                break;
            /*case R.id.my_profile_name_state:
                String[] State = getActivity().getResources().getStringArray(R.array.india_states);
                AlertDialog_Class.selectStateDialog(getActivity(), selectState, State);
                break;
            case R.id.my_profile_city:
                String[] city = getActivity().getResources().getStringArray(R.array.india_top_places);
                AlertDialog_Class.selectStateDialog(getActivity(), selectCity, city);
                break;*/
            case R.id.my_profile_submit:
//                updateProfile();
                Toast.makeText(getContext(), "Sucessufully submitted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_profile_dp:
                selectImage();
                break;
            case R.id.edit_profile:
                enableDisableEditing(true);
                break;


        }
    }

//    private void updateProfile() {
//        if (isValid()) {
//            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
//                    End_Points.UPDATE,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d("BaseFragment", "onResponse : " + response);
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                    Log.d("BaseFragment", "onErrorResponse : 61");
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/x-www-form-urlencoded; charset=UTF-8";
//                }
//
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    return params;
//                }
//
//            };
//            RequestQueue requestQueue = MyApplication.getInstance().getRequestQueue();
//            requestQueue.cancelAll(End_Points.UPDATE);
//            jsonObjRequest.setTag(End_Points.UPDATE);
//            requestQueue.add(jsonObjRequest);
//
//
//        }
//    }

    private boolean isValid() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.e("Activity", "Result OK");

            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data, getActivity());
            }


        }


    }

    public void onSelectFromGalleryResult(Intent data, Context context) {

        Uri selectedImageUri = data.getData();
        Log.e("Select Prodile Image", selectedImageUri.toString());
        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader cursorLoader = new CursorLoader(context, selectedImageUri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        Bitmap b = null;
        try {
            b = new BitmapDecoder().decodeUri(context, selectedImagePath, 0);
            Log.e("Bitmap", "" + b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Oops! File is missing.", Toast.LENGTH_SHORT).show();
        }
        profileImage.setImageBitmap(b);


    }

    private void enableDisableEditing(boolean isEditable) {
        if (isEditable) {
            name.setEnabled(true);
            selectState.setEnabled(true);
            selectCity.setEnabled(true);
            address.setEnabled(true);
            email.setEnabled(true);
            profileImage.setEnabled(true);
            my_profile_submit.setVisibility(View.VISIBLE);
        } else {
            name.setEnabled(false);
            selectState.setEnabled(false);
            selectCity.setEnabled(false);
            address.setEnabled(false);
            email.setEnabled(false);
            profileImage.setEnabled(false);
            my_profile_submit.setVisibility(View.GONE);
        }
    }

}

