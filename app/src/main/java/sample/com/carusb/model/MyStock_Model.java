package sample.com.carusb.model;

import java.io.Serializable;

/**
 * Created by Dell on 4/1/2016.
 */
public class MyStock_Model implements Serializable {
    private String id,car_dealer,reg_no,make,model,year,variant,fuel,notes,color,mileage,transmission,owner,insurance,exp_date,exp_price,rc_book_image,insurance_image,is_buy_car,sl_no,image,mobile,state,city,dealer_name;

    public MyStock_Model(String id, String dealer_name,String car_dealer, String reg_no, String make, String model, String year, String variant, String fuel, String notes, String color, String mileage, String transmission, String owner, String insurance, String exp_date, String exp_price, String rc_book_image, String insurance_image, String is_buy_car,String sl_no,String image,String mobile,String state,String city) {
        this.id = id;
        this.car_dealer = car_dealer;
        this.reg_no = reg_no;
        this.make = make;
        this.model = model;
        this.year = year;
        this.variant = variant;
        this.fuel = fuel;
        this.notes = notes;
        this.color = color;
        this.mileage = mileage;
        this.transmission = transmission;
        this.owner = owner;
        this.insurance = insurance;
        this.exp_date = exp_date;
        this.exp_price = exp_price;
        this.rc_book_image = rc_book_image;
        this.insurance_image = insurance_image;
        this.is_buy_car = is_buy_car;
        this.sl_no=sl_no;
        this.image=image;
        this.mobile = mobile;
        this.state=state;
        this.city=city;
        this.dealer_name=dealer_name;
    }

    public String getId() {
        return id;
    }

    public String getCar_dealer() {
        return car_dealer;
    }

    public String getReg_no() {
        return reg_no;
    }

    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }




    public String getYear() {
        return year;
    }

    public String getVariant() {
        return variant;
    }

    public String getFuel() {
        return fuel;
    }

    public String getNotes() {
        return notes;
    }

    public String getColor() {
        return color;
    }

    public String getMileage() {
        return mileage;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getOwner() {
        return owner;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getExp_date() {
        return exp_date;
    }

    public String getExp_price() {
        return exp_price;
    }

    public String getRc_book_image() {
        return rc_book_image;
    }

    public String getInsurance_image() {
        return insurance_image;
    }

    public String getIs_buy_car() {
        return is_buy_car;
    }

    public String getSl_no() {
        return sl_no;
    }

    public String getImage() {
        return image;
    }

    public String getMobile() {
        return mobile;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getDealer_name() {
        return dealer_name;
    }
}
