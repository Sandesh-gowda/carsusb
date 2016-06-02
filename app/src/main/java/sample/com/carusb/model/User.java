package sample.com.carusb.model;

import java.io.Serializable;

/**
 * Created by Dell on 4/6/2016.
 */
public class User implements Serializable {

    private String user_id, user_name, user_mobile, user_email, user_state, user_city, dealer_id, created_at,user_pin_code;

    public User(String dealer_id, String user_id, String user_name, String mobile, String user_pin_code,String email, String user_state, String city ,String created_at) {
        this.dealer_id = dealer_id;
        this.user_city = city;
        this.user_email = email;
        this.user_id = user_id;
        this.user_mobile = mobile;
        this.user_name = user_name;
       this.user_state = user_state;
        this.user_pin_code = user_pin_code;
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_state() {
        return user_state;
    }

    public String getUser_city() {
        return user_city;
    }

    public String getUser_pin_code() {
        return user_pin_code;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public String getCreated_at() {
        return created_at;
    }
}
