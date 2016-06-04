package sample.com.carusb.home_fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sample.com.carusb.R;
import sample.com.carusb.adapters.CarImages_Adapter;
import sample.com.carusb.adapters.Car_Model_Adapter;
import sample.com.carusb.helper.AlertDialog_Class;
import sample.com.carusb.json_parser.OkHttp_Class;
import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.Dialog_Models;
import sample.com.carusb.model.Stock;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.ImageHelper;
import sample.com.carusb.utils.StringUtils;


public class AddCarFragment extends Fragment implements View.OnClickListener {


/*sumeeth fragment*/
 private FragmentManager fragmentManager ;
    private ViewPager viewPager;


    private View view;
    private EditText notes, Regno, kms, exp_date, exp_price;

    private Button add_new_car, submit_button;

    private ArrayList<String> list = new ArrayList<>();

    private TextView upload_rc_book,
            upload_insurance, Make, model, year, variant, fuel, color, transmission, owners, insurance;
    //    private ViewPager viewPager_Image;

    private LinearLayout iamgeLyt;

    private ProgressDialog progressDialog;

    private ImageView image_Gallery, insurance_file_name, rc_book_file_name;
    private ArrayList<String> listpager_Array = new ArrayList<>();
    //    private FloatingActionButton floatButton;
    public static final int REQUEST_CAMERA = 0, SELECT_FILE = 1, PICK_INSURANCE_REQUEST_CODE = 2, PICK_RCBOOK_REQUEST_CODE = 3;
    private CarImages_Adapter adapter;
    private CheckBox sell_to_dealer;
    private ProgressDialog dialog;
    private ArrayList<Dialog_Models> modelArrayList;
    private ArrayList<Dialog_Models> makeArrayList;
    private ArrayList<Dialog_Models> variantArrayList;
    //    private final int Date_id = 7;
    private String makeId = "", modelId = "", variant_id = "";

    private String rcImagePath, insImagePath;

    public AddCarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_car_fragment, container, false);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("updating car..");
    }

    private void initViews() {

        makeArrayList = new ArrayList<>();
        sell_to_dealer = (CheckBox) view.findViewById(R.id.sell_to_dealer);
        notes = (EditText) view.findViewById(R.id.add_car_notes);
        Regno = (EditText) view.findViewById(R.id.add_car_reg_no);
        Make = (TextView) view.findViewById(R.id.add_car_make);
        model = (TextView) view.findViewById(R.id.add_car_model);
        year = (TextView) view.findViewById(R.id.add_car_year);
        variant = (TextView) view.findViewById(R.id.add_car_variant);
        fuel = (TextView) view.findViewById(R.id.add_car_fuel);
        color = (TextView) view.findViewById(R.id.add_car_color);
        kms = (EditText) view.findViewById(R.id.add_car_kms);
        transmission = (TextView) view.findViewById(R.id.add_car_transmission);
        owners = (TextView) view.findViewById(R.id.add_car_owners);
        insurance = (TextView) view.findViewById(R.id.add_car_insurance);
        exp_date = (EditText) view.findViewById(R.id.add_car_expirydate);
        exp_price = (EditText) view.findViewById(R.id.add_car_expected_price);


        upload_insurance = (TextView) view.findViewById(R.id.upload_insurace_button);
        upload_rc_book = (TextView) view.findViewById(R.id.upload_rc_book_button);
        rc_book_file_name = (ImageView) view.findViewById(R.id.upload_rc_book_file_name);
        insurance_file_name = (ImageView) view.findViewById(R.id.upload_insurance_file_name);


        submit_button = (Button) view.findViewById(R.id.add_car_submit_button);
        add_new_car = (Button) view.findViewById(R.id.add_new_car_button);
        // bulk_upload_button = (Button) view.findViewById(R.id.add_car_bulkupload_button);


//        viewPager_Image = (ViewPager) view.findViewById(R.id.add_car_view_pager);
        //GridView gridview
        image_Gallery = (ImageView) view.findViewById(R.id.image_gallery);
        iamgeLyt = (LinearLayout) view.findViewById(R.id.imageLyt);
//        floatButton = (FloatingActionButton) view.findViewById(R.id.fab);
        image_Gallery.setOnClickListener(this);

//        floatButton.setOnClickListener(this);

        submit_button.setOnClickListener(this);

        upload_insurance.setOnClickListener(this);

        upload_rc_book.setOnClickListener(this);

        Make.setOnClickListener(this);
        model.setOnClickListener(this);
        year.setOnClickListener(this);
        variant.setOnClickListener(this);
        fuel.setOnClickListener(this);
        color.setOnClickListener(this);
        transmission.setOnClickListener(this);
        owners.setOnClickListener(this);
        insurance.setOnClickListener(this);
        exp_date.setOnClickListener(this);
       add_new_car.setOnClickListener(this);
        iamgeLyt.removeAllViews();
        list.clear();
    }

    private void checkValidation() {

        Log.e("Ids", modelId + "\n" + makeId + "\n" + variant_id);


        String getNotes = notes.getText().toString().trim();
        String getRegNo = Regno.getText().toString().trim();
//        String getMake = Make.getText().toString().trim();
//        String getModel = model.getText().toString().trim();
        String getYear = year.getText().toString().trim();
//        String getVariant = variant.getText().toString().trim();
        String getFuel = fuel.getText().toString().trim();
        String getColor = color.getText().toString().trim();
        String getKms = kms.getText().toString().trim();
        String getTransmission = transmission.getText().toString().trim();
        String getOwners = owners.getText().toString().trim();
        String getExpiryDate = exp_date.getText().toString().trim();
        String getInsurance = insurance.getText().toString().trim();
        String getExpectedPrice = exp_price.getText().toString().trim();
        String getInsuranceFile = insImagePath;
        String getRcBookFile = rcImagePath;
        String getCarImages = "";
        if (listpager_Array.size() > 0)
            getCarImages = StringUtils.convertToString(listpager_Array);
        Log.e("Car Gallery : ", " - " + getCarImages + "\nInsu File - " + getInsuranceFile + "\nRC Book file - " + getRcBookFile);


        if (makeId.equals("")) {
            Toast.makeText(getActivity(), "Make field is empty", Toast.LENGTH_SHORT).show();
        } else if (modelId.equals("")) {
            Toast.makeText(getActivity(), "Model field is empty", Toast.LENGTH_SHORT).show();
        } else if (getYear.equals("")) {
            Toast.makeText(getActivity(), "Year field is empty", Toast.LENGTH_SHORT).show();
        } else if (variant_id.equals("")) {
            Toast.makeText(getActivity(), "Variant field is empty", Toast.LENGTH_SHORT).show();
        } else if (getFuel.equals("")) {
            Toast.makeText(getActivity(), "Fuel field is empty", Toast.LENGTH_SHORT).show();
        } else if (getColor.equals("")) {
            Toast.makeText(getActivity(), "Color field is empty", Toast.LENGTH_SHORT).show();
        } else if (getKms.equals("")) {
            Toast.makeText(getActivity(), "Kms field is empty", Toast.LENGTH_SHORT).show();
        } else if (getTransmission.equals("")) {
            Toast.makeText(getActivity(), "Transmission field is empty", Toast.LENGTH_SHORT).show();
        } else if (getOwners.equals("")) {
            Toast.makeText(getActivity(), "Owner field is empty", Toast.LENGTH_SHORT).show();
        } else if (getInsurance.equals("")) {
            Toast.makeText(getActivity(), "Insurance field is empty", Toast.LENGTH_SHORT).show();
        } else if (getExpectedPrice.equals("")) {
            Toast.makeText(getActivity(), "ExpectedPrice field is empty", Toast.LENGTH_SHORT).show();
        } else {
            if (MyApplication.getInstance().IsInternetConnected())
                //       if (submit_button.getTag().toString().equals("Submit"))
                addcar(getNotes, getRegNo, makeId, modelId, getYear, variant_id, getFuel, getColor, getKms, getTransmission, getOwners,
                        getInsurance, getExpiryDate, getExpectedPrice, getCarImages, getInsuranceFile, getRcBookFile);
              /*  else
                    updateCar(getNotes, getRegNo, makeId, modelId, getYear, variant_id, getFuel, getColor, getKms, getTransmission, getOwners,
                            getInsurance, getExpiryDate, getExpectedPrice, getCarImages, getInsuranceFile, getRcBookFile);
*/
            else
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_car_make:
                if (makeArrayList.size() == 0) {
                    if (MyApplication.getInstance().IsInternetConnected())
                        new GetMake_Task().execute();
                    else
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else
                    showMakeDialog();
                break;
            case R.id.add_car_model:
                // if (modelArrayList.size() == 0) {
                if (!makeId.equals("0") && !makeId.equals("")) {
                    if (MyApplication.getInstance().IsInternetConnected())
                        new GetModel_Task().execute();
                    else
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Select Makes first.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_car_fuel:
                String[] fuels = getActivity().getResources().getStringArray(R.array.addcar_select_fuel);
                AlertDialog_Class.selectStateDialog(getActivity(), fuel, fuels);
                break;
            case R.id.add_car_color:
                String[] colors = getActivity().getResources().getStringArray(R.array.addcar_select_color);
                AlertDialog_Class.selectStateDialog(getActivity(), color, colors);
                break;

            case R.id.add_car_insurance:
                String[] insurances = getActivity().getResources().getStringArray(R.array.addcar_select_insurance);
                AlertDialog_Class.selectStateDialog(getActivity(), insurance, insurances);
                break;
            case R.id.add_car_owners:
                String[] owner = getActivity().getResources().getStringArray(R.array.addcar_select_owners);
                AlertDialog_Class.selectStateDialog(getActivity(), owners, owner);
                break;
            case R.id.add_car_transmission:
                String[] Transmissions = getActivity().getResources().getStringArray(R.array.addcar_select_transmission);
                AlertDialog_Class.selectStateDialog(getActivity(), transmission, Transmissions);
                break;

            case R.id.add_car_year:
                String[] years = getActivity().getResources().getStringArray(R.array.addcar_select_year);
                AlertDialog_Class.selectStateDialog(getActivity(), year, years);

                break;
            case R.id.add_car_variant:
                if (!model.equals("0") && !model.equals("")) {
                    if (MyApplication.getInstance().IsInternetConnected())
                        new GetVariant_Task().execute();
                    else
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Select Model first.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_gallery:
                selectImage();
                break;

            case R.id.fab:
                selectImage();
                break;
            case R.id.upload_insurace_button:
                openFileSelector(true);

                break;
            case R.id.upload_rc_book_button:
                openFileSelector(false);
                break;
            case R.id.add_car_submit_button:

                checkValidation();

                break;

            case R.id.add_car_expirydate:

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
                exp_date.setText("");

                break;

            case R.id.add_new_car_button:

                resetAllViews();
                submit_button.setText("Submit");
                add_new_car.setVisibility(View.GONE);
                break;


        }
    }


    private void openFileSelector(boolean isInsurance) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (isInsurance) {

            getParentFragment().startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    PICK_INSURANCE_REQUEST_CODE);

        } else {

            getParentFragment().startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    PICK_RCBOOK_REQUEST_CODE);

        }
    }

    public void showInsuranceFilePath(Intent data, Context context) {

        insurance_file_name.setVisibility(View.VISIBLE);
        String originalPath = getOriginalPath(data, context);
        insImagePath = originalPath;
        ImageHelper.loadImage(getActivity(), originalPath, insurance_file_name);
    }

    public void showRCBookFilePath(Intent data, Context context) {
        rc_book_file_name.setVisibility(View.VISIBLE);
        String originalPath = getOriginalPath(data, context);
        rcImagePath = originalPath;
        ImageHelper.loadImage(getActivity(), originalPath, rc_book_file_name);
    }

    private String getOriginalPath(Intent data, Context context) {
        Uri selectedImageUri = data.getData();
        Log.e("Select File", selectedImageUri.toString());
        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader cursorLoader = new CursorLoader(context, selectedImageUri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public void onSelectFromGalleryResult(Intent data, Context context) {

        Uri selectedImageUri = data.getData();
        Log.e("Select Gallery", selectedImageUri.toString());
        String[] projection = {MediaStore.MediaColumns.DATA};
        CursorLoader cursorLoader = new CursorLoader(context, selectedImageUri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        setviewPager(selectedImagePath, context);


    }


// CAMERA AND GALLERY

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getParentFragment().startActivityForResult(intent, REQUEST_CAMERA);
                    // takePhoto();
                } else if (items[item].equals("Choose from Library")) {

                    //  startActivityForResult(new Intent(getActivity(), Gallery_Activity.class), CustomGallerySelectId);
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    getParentFragment().startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void showViewPager() {
        Log.e("Data", "Recicved");
//        image_Gallery.setVisibility(View.GONE);
//        viewPager_Image.setVisibility(View.VISIBLE);
//        floatButton.setVisibility(View.VISIBLE);
    }


    public void onCaptureImageResult(Intent data, Context context) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory().getPath(),
                System.currentTimeMillis() + ".jpg");

        // List<String> myList = new ArrayList<String>(Collections.singletonList(String.valueOf(destination.getAbsoluteFile())));
        setviewPager(String.valueOf(destination.getAbsoluteFile()), context);

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setviewPager(final String selectedImagePath, final Context context) {
        try {
            if (list.size() >= 4) {
                image_Gallery.setVisibility(View.GONE);
            } else {
                image_Gallery.setVisibility(View.VISIBLE);
            }
            final ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.image_list, null);
            list.add(selectedImagePath);
            ImageHelper.loadImage(context, selectedImagePath, imageView);
            iamgeLyt.addView(imageView);

            /*  sumeeth code tried for deleting the photo  it worked */


  imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
          builder.setTitle("Delete image");
          builder.setCancelable(false);
          builder.setMessage("Do you want to delete this image");
          builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  list.remove(selectedImagePath);
                  iamgeLyt.removeView(imageView);
              }
          });

          builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  builder.setCancelable(true);
              }
          });
          AlertDialog dialog = builder.create();
          dialog.show();
      }
  });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }
                break;
        }
    }

    public void takePhoto() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
                //   take_picture();
            }
        }
//        else{
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CAMERA);
//        }
    }


    public void hideViewPager() {
//        viewPager_Image.setVisibility(View.GONE);
//        floatButton.setVisibility(View.GONE);
        image_Gallery.setVisibility(View.VISIBLE);
    }

    private void resetAllViews() {
        Regno.setText("");
        Make.setText("");
        model.setText("");
        year.setText("");
        variant.setText("");
        fuel.setText("");
        color.setText("");
        kms.setText("");
        transmission.setText("");
        owners.setText("");
        insurance.setText("");
        exp_date.setText("");
        exp_price.setText("");
       // insurance_file_name = null ;
        notes.setText("");
        insImagePath = null;
        rcImagePath = null;

      //  rc_book_file_name = null;
        hideViewPager();
        listpager_Array.clear();
        sell_to_dealer.setChecked(false);

        variant_id = "";
        makeId = "";
        modelId = "";









    }


    /*code to move from one fragment to another */
   /* void changePostion(){
       *//* Fragment f = new MyStockFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id., f);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*//*





        fragmentManager.beginTransaction().replace(R.id.frame_container, new MyStockFragment()).commit();

    }*/


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
            selectModel(getActivity(), model, modelArrayList);
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
            selectMake(getActivity(), Make, makeArrayList);
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
            selectVariant(getActivity(), variant, variantArrayList);
            //  AlertDialog_Class.selectCarDialog(getActivity(), variant, variantArrayList);
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

    private void populateSetDate(int year, int month, int day) {
        exp_date.setText(month + "/" + day + "/" + year);
    }

    @SuppressLint("ValidFragment")
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }
    }


    public void addcar(final String notes, final String regNo, final String makeId, final String modelId, final String getYear, final String variant_id,
                       final String getFuel, final String getColor, final String getKms, final String getTransmission, final String getOwners,
                       final String getInsurance, final String getExpiryDate, final String getExpectedPrice, final String getCarImages, final String getInsuranceFile,
                       final String getRcBookFile) {
        progressDialog.show();
        String url = "http://www.carsusb.com/api/addcar.php";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    int success = result.getInt("success");
//                    String message = result.getString("message");

                    /*if (status.equals(Constant.REQUEST_SUCCESS)) {
                        // tell everybody you have succed upload image and post strings
                        Log.i("Messsage", message);
                    } else {
                        Log.i("Unexpected", message);
                    }*/

                    if (success == 1) {
                        Toast.makeText(getActivity(), "Successfully added", Toast.LENGTH_SHORT).show();
                      //  changePostion();
                      // Toast.makeText(getActivity(), "waiting added", Toast.LENGTH_SHORT).show();
                      //  resetAllViews();



                        /*  code for moving from addcar to mystock */
                        HomeFragment fragment = (HomeFragment) getParentFragment();
                        fragment.addCarFragment.resetAllViews();
                        fragment.setCurrentFragment(2);












                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Failed to add", Toast.LENGTH_SHORT).show();
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                /*sumeeth code for addding car*/
                User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
                Log.e("Delaer Id", user.getUser_id());
                String is_checked = "";


                params.put("dealer_id", user.getUser_id());  //user id
                params.put("add_car_notes", notes); //notes
                params.put("add_car_reg_no", regNo);
                params.put("add_car_make", makeId);
                params.put("add_car_model", modelId);

                params.put("add_car_year", getYear);
                params.put("add_car_variant", variant_id);
                params.put("add_car_fuel", getFuel);
                params.put("add_car_color", getColor);
                params.put("add_car_kms", getKms);
                params.put("add_car_transmission", getTransmission);
                params.put("add_car_owners", getOwners);
                params.put("add_car_insurance", getInsurance);
                params.put("add_car_expirydate", getExpiryDate);
                params.put("add_car_expiryprice", getExpectedPrice);


                /* for check box */
                if (sell_to_dealer.isChecked())
                    is_checked = "1";
                else
                    is_checked = "";
                params.put("is_buy_car", is_checked);


                return params;
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected Map<String, DataPart> getByteData() {


                Map<String, DataPart> params = new HashMap<>();

                File rcbookImage = null, insuranceImage = null, carImage = null;
                Bitmap bmpRc = null, bmpCar = null, bmpIns = null;
                try {
                    Log.e("Rc Image", getRcBookFile);
                    if (!getRcBookFile.equals("")) {
                        rcbookImage = new File(getRcBookFile);

                        bmpRc = BitmapFactory.decodeFile(rcbookImage.getAbsolutePath());
                        params.put("upload_rc_book_file_name", new DataPart(new File(getRcBookFile).getName(), AppHelper.bitmapToByteArray(bmpRc), "image/jpeg"));
                    } else {
                        bmpRc = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                        params.put("upload_rc_book_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpRc), "image/jpeg"));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    bmpRc = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                    params.put("upload_rc_book_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpRc), "image/jpeg"));


                }
                try {
                    if (!getInsuranceFile.equals("")) {
                        insuranceImage = new File(getInsuranceFile);
                        bmpIns = BitmapFactory.decodeFile(insuranceImage.getAbsolutePath());
                        params.put("upload_insurance_file_name", new DataPart(new File(getInsuranceFile).getName(), AppHelper.bitmapToByteArray(bmpIns), "image/jpeg"));
                    } else {
                        bmpIns = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                        params.put("upload_insurance_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpIns), "image/jpeg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bmpIns = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                    params.put("upload_insurance_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpIns), "image/jpeg"));
                }
                try {
                    for (int i = 0; i < list.size(); i++) {
                        String path = list.get(i);
                        if (!TextUtils.isEmpty(path)) {
                            File file = new File(path);
                            bmpCar = BitmapFactory.decodeFile(path);
                            params.put("image_gallery" + i, new DataPart(file.getName(), AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));
                        } else {
                            bmpCar = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                            params.put("image_gallery" + i, new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));
                        }
                    }
//
//
//                    if (!getCarImages.equals("")) {
//                        carImage = new File(getCarImages);
//                        bmpCar = BitmapFactory.decodeFile(carImage.getAbsolutePath());
//                        params.put("image_gallery", new DataPart(new File(getCarImages).getName(), AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));
//                    } else {
//                        bmpCar = BitmapFactory.decodeResource(getResources(), R.drawable.a);
//                        params.put("image_gallery", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bmpCar = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                    params.put("image_gallery0", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));

                }


                return params;
            }
        };


        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }


    //for edit text when onclick is done

    public void setEditData(Stock myStock_model) {
        Regno.setText(myStock_model.getReg_no());
        Make.setText(myStock_model.getMake());
        model.setText(myStock_model.getModel());
        year.setText(myStock_model.getYear());
        variant.setText(myStock_model.getVarient());
        fuel.setText(myStock_model.getFuel());
        color.setText(myStock_model.getColor());
        transmission.setText(myStock_model.getTransmission());
        owners.setText(myStock_model.getOwner());
        insurance.setText(myStock_model.getInsurance());
        exp_date.setText(myStock_model.getExp_date());
        exp_price.setText(myStock_model.getExp_price());
        kms.setText(myStock_model.getMileage());
        notes.setText(myStock_model.getNotes());

        try {
            int getBuyCar = Integer.parseInt(myStock_model.getIs_buy_car());
            if (getBuyCar == 1)
                sell_to_dealer.setChecked(true);
            else
                sell_to_dealer.setChecked(false);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        rc_book_file_name.setText(myStock_model.getRc_image());
//        insurance_file_name.setText(myStock_model.getInc_image());

        rcImagePath = myStock_model.getRc_image();
        insImagePath = myStock_model.getInc_image();
        ImageHelper.loadImage(getActivity(),rcImagePath, rc_book_file_name);
        ImageHelper.loadImage(getActivity(), myStock_model.getInc_image(), insurance_file_name);


        submit_button.setText("Update");

        //set visibility .of addNewCar :modified sumeeth

        add_new_car.setVisibility(View.GONE);


    }

    public void updateCar(final String notes, final String regNo, final String makeId, final String modelId, final String getYear, final String variant_id,
                          final String getFuel, final String getColor, final String getKms, final String getTransmission, final String getOwners,
                          final String getInsurance, final String getExpiryDate, final String getExpectedPrice, final String getCarImages, final String getInsuranceFile,
                          final String getRcBookFile) {
        String url = "http://www.carsusb.com/api/addcar.php";//Put update car API Over here
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    int success = result.getInt("success");
                    String message = result.getString("message");

                    /*if (status.equals(Constant.REQUEST_SUCCESS)) {
                        // tell everybody you have succed upload image and post strings
                        Log.i("Messsage", message);
                    } else {
                        Log.i("Unexpected", message);
                    }*/

                    if (success == 1) {



                        /* code for moving to mystock when it is updated */

                        HomeFragment fragment = (HomeFragment) getParentFragment();
                        fragment.addCarFragment.resetAllViews();
                        fragment.setCurrentFragment(2);




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                /*sumeeth code for addding car*/
                User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
                Log.e("Delaer Id", user.getUser_id());
                String is_checked = "";


                params.put("dealer_id", user.getUser_id());  //user id
                params.put("add_car_notes", notes); //notes
                params.put("add_car_reg_no", regNo);
                params.put("add_car_make", makeId);
                params.put("add_car_model", modelId);

                params.put("add_car_year", getYear);
                params.put("add_car_variant", variant_id);
                params.put("add_car_fuel", getFuel);
                params.put("add_car_color", getColor);
                params.put("add_car_kms", getKms);
                params.put("add_car_transmission", getTransmission);
                params.put("add_car_owners", getOwners);
                params.put("add_car_insurance", getInsurance);
                params.put("add_car_expirydate", getExpiryDate);
                params.put("add_car_expiryprice", getExpectedPrice);


                /* for check box */
                if (sell_to_dealer.isChecked())
                    is_checked = "1";
                else
                    is_checked = "";
                params.put("is_buy_car", is_checked);


                return params;
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected Map<String, DataPart> getByteData() {


                Map<String, DataPart> params = new HashMap<>();

                File rcbookImage = null, insuranceImage = null, carImage = null;
                Bitmap bmpRc = null, bmpCar = null, bmpIns = null;
                try {
                    Log.e("Rc Image", getRcBookFile);
                    if (!getRcBookFile.equals("")) {
                        rcbookImage = new File(getRcBookFile);

                        bmpRc = BitmapFactory.decodeFile(rcbookImage.getAbsolutePath());
                        params.put("upload_rc_book_file_name", new DataPart(new File(getRcBookFile).getName(), AppHelper.bitmapToByteArray(bmpRc), "image/jpeg"));
                    } else {
                        bmpRc = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                        params.put("upload_rc_book_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpRc), "image/jpeg"));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    bmpRc = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                    params.put("upload_rc_book_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpRc), "image/jpeg"));


                }
                try {
                    if (!getInsuranceFile.equals("")) {
                        insuranceImage = new File(getInsuranceFile);
                        bmpIns = BitmapFactory.decodeFile(insuranceImage.getAbsolutePath());
                        params.put("upload_insurance_file_name", new DataPart(new File(getInsuranceFile).getName(), AppHelper.bitmapToByteArray(bmpIns), "image/jpeg"));
                    } else {
                        bmpIns = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                        params.put("upload_insurance_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpIns), "image/jpeg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bmpIns = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                    params.put("upload_insurance_file_name", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpIns), "image/jpeg"));
                }
                try {
                    if (!getCarImages.equals("")) {
                        carImage = new File(getCarImages);
                        bmpCar = BitmapFactory.decodeFile(carImage.getAbsolutePath());
                        params.put("image_gallery", new DataPart(new File(getCarImages).getName(), AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));
                    } else {
                        bmpCar = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                        params.put("image_gallery", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bmpCar = BitmapFactory.decodeResource(getResources(), R.drawable.a);
                    params.put("image_gallery", new DataPart("a.jpg", AppHelper.bitmapToByteArray(bmpCar), "image/jpeg"));

                }


                return params;
            }
        };


        MyApplication.getInstance().addToRequestQueue(multipartRequest);
    }

}

