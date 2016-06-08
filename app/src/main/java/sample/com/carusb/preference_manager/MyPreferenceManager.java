package sample.com.carusb.preference_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import sample.com.carusb.model.Stock;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;


public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Constructor
    public MyPreferenceManager(Context context, String pref_key_value) {
        this._context = context;
        pref = _context.getSharedPreferences(pref_key_value, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(User user) {
        editor.putString(Constants.User_Id, user.getUser_id());
        editor.putString(Constants.User_Name, user.getUser_name());
        editor.putString(Constants.User_EmailId, user.getUser_email());
        editor.putString(Constants.User_Mobile_Number, user.getUser_mobile());
        editor.putString(Constants.User_City, user.getUser_city());
        editor.putString(Constants.User_State, user.getUser_state());
        editor.putString(Constants.Dealer_Id, user.getDealer_id());
        editor.putString(Constants.User_CreatedAt, user.getCreated_at());
        editor.putString(Constants.User_Pin_code, user.getUser_pin_code());

        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getUser_name() + ", " + user.getUser_id());
    }

    public User getUser() {
        if (pref.getString(Constants.User_Id, null) != null) {

            String user_id = pref.getString(Constants.User_Id, null);
            String user_name = pref.getString(Constants.User_Name, null);
            String user_email = pref.getString(Constants.User_EmailId, null);
            String user_mobile = pref.getString(Constants.User_Mobile_Number, null);
            String city = pref.getString(Constants.User_City, null);
            String state = pref.getString(Constants.User_State, null);
            String dealer_id = pref.getString(Constants.Dealer_Id, null);
            String created_at = pref.getString(Constants.User_CreatedAt, null);
            String pin_code = pref.getString(Constants.User_Pin_code, null);
            Log.e(TAG, "Get User Data " + city + ", " + state);
            return new User(dealer_id, user_id, user_name, user_mobile, pin_code, user_email, state, city, created_at);
        }
        return null;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }


    public void setShortlistItems(ArrayList<Stock> list) {

        LinkedHashSet<Stock> lhs = new LinkedHashSet<Stock>();
        lhs.addAll(list);
        Gson gson = new Gson();
        String jsonString = gson.toJson(lhs);
        editor.putString("shortlist", jsonString).apply();
    }

    public ArrayList<Stock> getShortList() {
        Gson gson = new Gson();
        String shortlist = pref.getString("shortlist", "[]");
        Type listOfTestObject = new TypeToken<List<Stock>>() {
        }.getType();
        ArrayList<Stock> list = gson.fromJson(shortlist, listOfTestObject);

        return list;
    }

    public void clearShortlIst() {


        editor.clear();
        editor.commit();
    }

  /*  *//*sumeeth code for removing the shortlist *//*

    public ArrayList<Stock> removeShortList(int positon){
        int p = positon;
        Gson gson = new Gson();
        String shortlist = pref.getString("shortlist", "[]");
        Type listOfTestObject = new TypeToken<List<Stock>>() {
        }.getType();
        ArrayList<Stock> list = gson.fromJson(shortlist, listOfTestObject);
        list.remove(p);
        editor.commit();
        return list;

    }
*/
}
