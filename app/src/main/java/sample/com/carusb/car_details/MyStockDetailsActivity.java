package sample.com.carusb.car_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
    import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import me.relex.circleindicator.CircleIndicator;
import sample.com.carusb.R;
import sample.com.carusb.adapters.ImageAdapter;
import sample.com.carusb.adapters.ViewPagerAdapter;
import sample.com.carusb.model.Stock;
import sample.com.carusb.preference_manager.MyPreferenceManager;

/**
 * Created by Dell on 4/1/2016.
 */
public class MyStockDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    /*modified from here*/


    private LinearLayout contact_click, buttons_link;

    //    private ViewPager mPager;
//    private int currentPage = 0;
//    private Toolbar toolbar;
//    private int NUM_PAGES = 0;
    //  private ArrayList<String> ImagesArray;
    private Stock myStock_model;
    private final String TABS[] = {"Specifications", "Document Images"};

    //    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView mobile, dealer_car_name, brand_name, car_model_name, car_varient_name, car_kms, car_fuel, car_color, car_reg_no, car_owner, car_price, car_full_summary, car_myImageViewText, car_dealer_name, car_dealer_city, car_dealer_state;
    private Button shortlist, call, share;
    private boolean isMyStock;
    private boolean isSoldCar,isShortList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myStock_model = (Stock) getIntent().getSerializableExtra("Car_Data");
        isMyStock = getIntent().getBooleanExtra("isMyStock", false);
        //not showing call share shortlist.

        isSoldCar = getIntent().getBooleanExtra("isSoldCar",false);


       isShortList = getIntent().getBooleanExtra("isShortList",false);


        setContentView(R.layout.car_details_activity);

        setHeaderImages();
        initViews();
        initViewPager();
        setAllData();
    }

    private void setHeaderImages() {
        ViewPager imagePager = (ViewPager) findViewById(R.id.car_details_gallery_view_pager);

        /*sumeeth modified for circle indicator*/

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        ArrayList<String> imageList = myStock_model.getImage();
        if (imageList != null && imageList.size() > 0) {
            imagePager.setAdapter(new ImageAdapter(MyStockDetailsActivity.this, imageList));
            indicator.setViewPager(imagePager);

        }

    }

    private void initViewPager() {
        ViewPager tab_viewPager = (ViewPager) findViewById(R.id.car_details_view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.car_details_tabLayout);
        SpecificationFragment specificationFragment = new SpecificationFragment(myStock_model);
        CarDetailsDocumentFragment carDetailsDocumentFragment = new CarDetailsDocumentFragment(myStock_model);
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(specificationFragment);
        list.add(carDetailsDocumentFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), list, TABS);
        tab_viewPager.setAdapter(adapter);

        /*sumeeth code for circle adapter */


        tabLayout.setupWithViewPager(tab_viewPager);
    }

    private void setAllData() {
        dealer_car_name.setText(myStock_model.getMake());
        mobile.setText(myStock_model.getMobile());
        car_dealer_city.setText(myStock_model.getCity());
        car_dealer_name.setText(myStock_model.getDealer_name());
    }


    private void initViews() {
        dealer_car_name = (TextView) findViewById(R.id.dealer_car_name);

        buttons_link = (LinearLayout) findViewById(R.id.mystock_buttons_link);
        contact_click = (LinearLayout) findViewById(R.id.contact_click);
        mobile = (TextView) findViewById(R.id.mobile_no);
        car_dealer_city = (TextView) findViewById(R.id.car_dealer_city);
        car_dealer_name = (TextView) findViewById(R.id.car_dealer_name);

        call = (Button) findViewById(R.id.call_car_dealer);

        /* sumeeth  modified */
        shortlist = (Button) findViewById(R.id.car_short_listed);
        share = (Button) findViewById(R.id.share_screen);
        /*hide the button sumeeth*/



        if (isMyStock) {
            shortlist.setVisibility(View.GONE);
            call.setVisibility(View.GONE);
         //   share.setVisibility(View.VISIBLE);

        }
        else {
            shortlist.setVisibility(View.VISIBLE);
            call.setVisibility(View.VISIBLE);
        }



        call.setOnClickListener(this);
        share.setOnClickListener(this);
        shortlist.setOnClickListener(this);


        if (isMyStock) {
            contact_click.setVisibility(View.GONE);
            //  buttons_link.setVisibility(View.VISIBLE);
        } else {
            contact_click.setVisibility(View.VISIBLE);
            //     buttons_link.setVisibility(View.GONE);
        }


        /*removing shortlist, call, share  in soldcar*/

        if (isSoldCar){
            shortlist.setVisibility(View.GONE);
            call.setVisibility(View.GONE);


        //   share.setVisibility(View.INVISIBLE);
         //   contact_click.setVisibility(View.GONE);


        }



        // removing shortlist only in shortlist ;



    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_car:
                break;
            case R.id.delete_car:
                break;
            case R.id.sold_car:
                break;
            case R.id.car_short_listed:
                MyPreferenceManager preferenceManager = new MyPreferenceManager(getApplicationContext(), "shortlist");
                ArrayList<Stock> shortList = preferenceManager.getShortList();
                if (shortList == null) {
                    shortList = new ArrayList<>();
                }
                shortList.add(myStock_model);


               /* //now convert it to linkedhashmap
                Set<Stock> lhs = new LinkedHashSet<Stock>();
                    lhs.addAll(shortList);
                //and again send to arraylist

                ArrayList<Stock>  shortListw = new ArrayList<Stock>(lhs);
*/

                preferenceManager.setShortlistItems(shortList);
                Toast.makeText(MyStockDetailsActivity.this, "Short listed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.call_car_dealer:
                makecall(myStock_model.getMobile());
                break;
            case R.id.share_screen:
                shareData();
                break;
        }
    }

    private void makecall(String number) {
        String tel = "tel:" + number;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
        startActivity(Intent.createChooser(intent, "Choose an application"));
    }

    private void shareData() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");// Plain format text
        StringBuilder builder = new StringBuilder();
        builder.append("DealerName:" + " " + myStock_model.getDealer_name() + "  " +
                "CarBrand:" + " " + myStock_model.getMake() + " " +
                "CarModel:" + " " + myStock_model.getModel() + " " +
                "Variant" + " " + myStock_model.getVarient() + " " +
                "Year:" + " " + myStock_model.getYear() + " " +
                "Contact:" + myStock_model.getMobile() + " " +
                "city:" + " " + myStock_model.getCity() + " " +
                "Owner" + myStock_model.getOwner() +
                "Insurance" + myStock_model.getInsurance() + "Fuel"
                + myStock_model.getFuel() +
                "Color" + myStock_model.getColor() + "Kms" + myStock_model.getMileage() +
                "Transmission" + myStock_model.getTransmission() + "CarRc" + myStock_model.getRc_image()
        );
        sharingIntent.putExtra(Intent.EXTRA_TEXT, builder.toString());
        startActivity(Intent.createChooser(sharingIntent, "Share product using"));

    }
}




