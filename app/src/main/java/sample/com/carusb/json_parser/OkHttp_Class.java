package sample.com.carusb.json_parser;

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.End_Points;

/**
 * Created by SONU on 13/10/15.
 */
public class OkHttp_Class {

    public static final String TAG = "OkHTTP";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public JSONObject loginUser(String mobile, String password) throws IOException {
        try {

            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("mobile", mobile)
                    .add("password", password)
                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.Login_Url)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            Log.e(TAG, jsonString);
            JSONObject jsonData = new JSONObject(jsonString);
            //  Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject forgotPassword(String mobile) throws IOException {
        try {

            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("mobile", mobile)
                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.Forgot_Url)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            Log.e(TAG, jsonString);
            JSONObject jsonData = new JSONObject(jsonString);


            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray dealerSignup(String dealer_name, String dealer_emailid, String dealer_mobileno, String dealer_pincode, String select_city, String select_state) throws IOException {
        try {

            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("dealer_name", dealer_name)
                            //.add("dealer_emailid", dealer_emailid)
                    .add("mobile", dealer_mobileno)
                    .add("pincode", dealer_pincode)
                    .add("city", select_city)
                    .add("state", select_state)
                    .add("terms_c", "true")
                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.SignUp_Url)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            Log.e(TAG, jsonString);
            return new JSONArray(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject contact_us(String name, String email, String phone, String message) throws IOException {
        try {

            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("name", name)
                    .add("email", email)
                    .add("phone", phone)
                    .add("message", message)
                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.Contact_us_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject dashboard(String id) throws IOException {
        try {


            Log.e("Id in Dash", id);
            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("dealer_id", id)
                    .build();
            Request request = new Request.Builder()
                    .url(End_Points.DASHBOARD_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public JSONObject dashboardcars(String id) throws IOException {
        try {


            Log.e("casrs in Dash", id);
            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("dealer_id", id)
                    .build();
            Request request = new Request.Builder()
                    .url(End_Points.DASHBOARD_CAR_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public JSONObject fetchCities(String state) throws IOException {
        try {


            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();

            formBody = new FormEncodingBuilder()
                    .add("state", state)
                    .build();
            Request request = new Request.Builder()
                    .url(End_Points.City_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public JSONObject addCartask(String notes, String reg_no, String make, String model, String year, String variant, String fuel, String color, String kms, String transmission, String
            owners, String insurance, String expiry_date, String expected_price, String image_gallery, String insurance_file, String rc_book_file, String is_buy_car) throws IOException, JSONException {

        /*sumeeth has done this code*/
        User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        Log.e("Delaer Id", user.getUser_id());
        JSONObject json = null;






            OkHttpClient client = new OkHttpClient();

            String galleryImage = new File(image_gallery).getName();
            String insuranceImage = new File(insurance_file).getName();
            String rcBookImage = new File(rc_book_file).getName();
        /*sumeeth code */


            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=---011000010111000001101001");
            RequestBody body = RequestBody.create(mediaType, "-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"dealer_id\"\r\n\r\n"+user.getUser_id()+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; " +
                    "name=\"add_car_notes\"\r\n\r\n"+notes+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_reg_no\"\r\n\r\n"+reg_no+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_make\"\r\n\r\n"+make+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_model\"\r\n\r\n"+model+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_year\"\r\n\r\n"+year+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_variant\"\r\n\r\n"+variant+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_fuel\"\r\n\r\n"+fuel+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_color\"\r\n\r\n"+color+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_kms\"\r\n\r\n"+kms+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_transmission\"\r\n\r\n"+transmission+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_owners\"\r\n\r\n"+owners+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_insurance\"\r\n\r\n"+insurance+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_expirydate\"\r\n\r\n"+expiry_date+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"add_car_expiryprice\"\r\n\r\n"+expected_price+"\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"is_buy_car\"\r\n\r\nis_buy_car\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"upload_rc_book_file_name\"; filename=\"" + rcBookImage + "\"\r\nContent-Type: false\r\n\r\n\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"upload_insurance_file_name\"; filename=\"" + insuranceImage + "\"\r\nContent-Type: false\r\n\r\n\r\n-----011000010111000001101001\r\nContent-Disposition: form-data;" +
                    " name=\"image_gallery\"; filename=\"" + galleryImage + "\"\r\nContent-Type: false\r\n\r\n\r\n-----011000010111000001101001--");
            Request request = new Request.Builder()
                    .url("http://www.carsusb.com/api/addcar.php")
                        .post(body)
                    .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "98357b03-2f0a-85e7-ebc1-727bd2915177")
                    .build();

            Response response = client.newCall(request).execute();
        String jsonString = response.body().string();
        JSONObject jsonData = new JSONObject(jsonString);
        Log.e("Respinse o Imaye",response.body().string());
        return jsonData;
       }

    public JSONObject addCar(String notes, String reg_no, String make, String model, String year, String variant, String fuel, String color, String kms, String transmission, String
            owners, String insurance, String expiry_date, String expected_price, String image_gallery, String insurance_file, String rc_book_file, String is_buy_car) {

        User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        Log.e("Delaer Id", user.getUser_id());
        JSONObject json = null;

        String galleryImage = new File(image_gallery).getName();
        String insuranceImage = new File(insurance_file).getName();
        String rcBookImage = new File(rc_book_file).getName();

        Log.e("Image Name", galleryImage + "\n" + insuranceImage + "\n" + rcBookImage);
        Log.e("Car Image Full Path",image_gallery+"\nCar Image Name - "+galleryImage);
        int serverResponseCode = 0;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(image_gallery);
        if (!sourceFile.isFile()) {
            Log.e("sourceFile", "galleryImage File Does not exist");
            //return json;
        }
        File insuranceFile = new File(insurance_file);
        if (!insuranceFile.isFile()) {
            Log.e("insuranceFile", "insuranceFile File Does not exist");
            //  return json;
        }
        File rcFile = new File(rc_book_file);
        if (!rcFile.isFile()) {
            Log.e("rcFile", "rcFile File Does not exist");
            // return json;
        }
        try { // open a URL connection to the Servlet


          FileInputStream sourceFileInputStream = new FileInputStream(sourceFile);
           FileInputStream insuranceFileInputStream = new FileInputStream(insuranceFile);
           FileInputStream rcBookFileInputStream = new FileInputStream(rcFile);




            URL url = new URL(End_Points.ADD_CAR_URL);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
             conn.setRequestProperty("upload_insurance_file_name", insuranceImage);
            conn.setRequestProperty("image_gallery",galleryImage);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"dealer_id\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(user.getUser_id());
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_reg_no\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(reg_no);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_make\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(make);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_model\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(model);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_year\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(year);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_variant\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(variant);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_fuel\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(fuel);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_color\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(color);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_kms\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(kms);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_transmission\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(transmission);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_owners\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(owners);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_insurance\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(insurance);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_expirydate\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(expiry_date);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_expiryprice\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(expected_price);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"is_buy_car\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(is_buy_car);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"add_car_notes\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(notes);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"image_gallery\";filename=\"" + galleryImage + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"upload_insurance_file_name\";filename=\"" + insuranceImage + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"upload_rc_book_file_name\";filename=\"" + rcBookImage + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            // Car Images
            bytesAvailable = sourceFileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = sourceFileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = sourceFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = sourceFileInputStream.read(buffer, 0, bufferSize);
            }

            //Insurance File
            bytesAvailable = insuranceFileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = insuranceFileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = insuranceFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = insuranceFileInputStream.read(buffer, 0, bufferSize);
            }

            //RC Book File
            bytesAvailable = rcBookFileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = rcBookFileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = rcBookFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = rcBookFileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necessary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            if (serverResponseCode == 200) {

                try {
                    DataInputStream inStream = new DataInputStream(conn.getInputStream());
                    String str;
                    while ((str = inStream.readLine()) != null) {
                        String yourmessage = str;
                        json = new JSONObject(yourmessage);
                        Log.e("Json Data of Add car", json.toString());
                    }
                    inStream.close();
                    return json;
                } catch (IOException ioex) {
                    Log.e("SD card doFile upload: ", "" + ioex.getMessage());
                }
            }

            //close the streams //


//            sourceFileInputStream.close();
//            insuranceFileInputStream.close();
 //           rcBookFileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Upload file Exception", e.getMessage(), e);
        }
        return json;
    }


    public JSONObject fetchCarModels(String id) throws IOException {
        try {


            OkHttpClient client = new OkHttpClient();
            RequestBody formBody;
            formBody = new FormEncodingBuilder()
                    .add("id", id)
                    .build();
            Request request = new Request.Builder()
                    .url(End_Points.GET_ALL_MODEL_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*sumeeth code for sold */
    public JSONObject sold(String id) throws IOException{
        try{

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody;
            formBody = new FormEncodingBuilder()
                    .add("id", id)
                    .add("sold","sold")
                    .build();
            Request request = new Request.Builder()
                    .url(End_Points.SOLD)
                    .post(formBody)
                    .build();
            Response responses = null;
            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;



        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*delete the car*/


    public JSONObject delete(String id) throws IOException{
        try{

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody;
            formBody = new FormEncodingBuilder()
                    .add("id", id)
                    .add("delete","delete")
                    .build();
            Request request = new Request.Builder()
                    .url(End_Points.SOLD)
                    .post(formBody)
                    .build();
            Response responses = null;
            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;



        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject fetchCarMake() throws IOException {
        try {


            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(End_Points.GET_ALL_MAKE_URL)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject fetchCarVariant(String id) throws IOException {
        try {


            OkHttpClient client = new OkHttpClient();

            RequestBody formBody;
            formBody = new FormEncodingBuilder()
                    .add("id", id)
                    .build();
            Request request = new Request.Builder()
                    .url(End_Points.GET_ALL_VARIANT_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONObject jsonData = new JSONObject(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public JSONObject fetchAllMyStockData() throws IOException {
//        try {
//
//            User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
//            OkHttpClient client = new OkHttpClient();
//            RequestBody formBody;
//            formBody = new FormEncodingBuilder()
//                    .add("dealer_id", user.getUser_id())
//                    .build();
//
//
//            Request request = new Request.Builder()
//                    .url(End_Points.MY_STOCK_URL)
//                    .post(formBody)
//                    .build();
//            Response responses = null;
//
//            try {
//                responses = client.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String jsonString = responses.body().string();
//            Log.e(TAG, jsonString);
//            JSONObject jsonData = new JSONObject(jsonString);
//
//
//            return jsonData;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public JSONObject fetchAllBuyCarData() throws IOException {
//        try {
//
//
//            User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
//            Log.e("Id", user.getUser_id() + "\nState - " + user.getUser_state());
//            OkHttpClient client = new OkHttpClient();
//            RequestBody formBody;
//            formBody = new FormEncodingBuilder()
//                    .add("dealer_id", user.getUser_id())
//                    .add("state", user.getUser_state())
//                    .build();
//
//
//            Request request = new Request.Builder()
//                    .url(End_Points.BUY_CAR_URL)
//                    .post(formBody)
//                    .build();
//            Response responses = null;
//
//            try {
//                responses = client.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String jsonString = responses.body().string();
//            Log.e(TAG, jsonString);
//            JSONObject jsonData = new JSONObject(jsonString);
//
//
//            return jsonData;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public JSONObject fetchAllHubData() throws IOException {
//        try {
//
//            User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
//            Log.e("Id", user.getDealer_id() + "\nCity - " + user.getUser_city());
//            OkHttpClient client = new OkHttpClient();
//            RequestBody formBody;
//            formBody = new FormEncodingBuilder()
//                    .add("dealer_id", user.getUser_id())
//                    .add("city", user.getUser_city())
//                    .build();
//
//
//            Request request = new Request.Builder()
//                    .url(End_Points.HUB_URL)
//                    .post(formBody)
//                    .build();
//            Response responses = null;
//
//            try {
//                responses = client.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String jsonString = responses.body().string();
//            Log.e(TAG, jsonString);
//            JSONObject jsonData = new JSONObject(jsonString);
//
//
//            return jsonData;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public JSONArray sendOTP(String mobileNumber, String message) throws IOException {
        try {


            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("http://login.vsginfotech.com:/sendSMS?username=carsusb&message=" + URLEncoder.encode(message) + "&sendername=carusb&smstype=TRANS&numbers=" + mobileNumber + "&apikey=f388e042-18d6-41f5-98f6-376f2b003949")
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();
            JSONArray jsonData = new JSONArray(jsonString);
            Log.e(TAG, jsonData.toString());

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String search_car(String message) throws IOException {
        try {


            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();
            User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
            formBody = new FormEncodingBuilder()
                    .add("dealer_id", user.getUser_id())
                    .add("search", message)
                    .add("city",user.getUser_city())
                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.SEARCH_CAR_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();

            Log.e(TAG, jsonString);

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String dashboard_car() throws IOException {
        try {
            RequestBody formBody;
            OkHttpClient client = new OkHttpClient();
            User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
            formBody = new FormEncodingBuilder()
                    .add("dealer_id", user.getUser_id())

                    .build();


            Request request = new Request.Builder()
                    .url(End_Points.DASHBOARD_CAR_URL)
                    .post(formBody)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonString = responses.body().string();

            Log.e(TAG, jsonString);

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
