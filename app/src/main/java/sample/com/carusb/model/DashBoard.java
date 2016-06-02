package sample.com.carusb.model;

/**
 * Created by kalyan_pvs on 15/5/16.
 */

public class DashBoard {
    private String current_month_total_sold;

    private String current_inventory_total_sold_cars;

    private String current_month_total_stock;


    private String current_inventory_total_stock;


    public String getCurrentMonthTotalSold() {
        return current_month_total_sold;
    }

    public void setCurrent_month_total_sold(String current_month_total_sold) {
        this.current_month_total_sold = current_month_total_sold;
    }

    public String getCurrentInventoryTotalSoldCars() {
        return current_inventory_total_sold_cars;
    }

    public void setCurrent_inventory_total_sold_cars(String current_inventory_total_sold_cars) {
        this.current_inventory_total_sold_cars = current_inventory_total_sold_cars;
    }

    public String getCurrentMonthTotalStock() {
        return current_month_total_stock;
    }

    public void setCurrent_month_total_stock(String current_month_total_stock) {
        this.current_month_total_stock = current_month_total_stock;
    }


    public String getCurrentInventoryTotalStock() {
        return current_inventory_total_stock;
    }

    public void setCurrent_inventory_total_stock(String current_inventory_total_stock) {
        this.current_inventory_total_stock = current_inventory_total_stock;
    }

}

