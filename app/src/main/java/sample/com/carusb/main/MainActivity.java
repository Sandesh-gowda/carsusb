package sample.com.carusb.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import sample.com.carusb.R;
import sample.com.carusb.home_fragments.Contact_Us_Fragment;
import sample.com.carusb.home_fragments.DashboardFragment;
import sample.com.carusb.home_fragments.HomeFragment;
import sample.com.carusb.home_fragments.MyProfileFragment;
import sample.com.carusb.home_fragments.SearchFragment;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.Share_Rate_App_Class;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        initViews();
        setUpHeaderView();
        onMenuItemSelected();

        //At start set home fragment
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.home);
            MenuItem item = navigationView.getMenu().findItem(R.id.home);
            Fragment homeFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
            if (homeFragment == null)
                fragmentManager.beginTransaction().replace(R.id.frame_container, new HomeFragment(), item.getTitle().toString()).commit();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    /*  Init all views  */

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.slider_menu);
        fragmentManager = getSupportFragmentManager();
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, // nav menu toggle icon
                R.string.drawer_open, // nav drawer open - description for
                // accessibility
                R.string.drawer_close // nav drawer close - description for
                // accessibility
        ) {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /**
     * For using header view use this method
     **/
    private void setUpHeaderView() {
        View headerView = navigationView.inflateHeaderView(R.layout.headerview);
//        TextView textOne = (TextView) headerView.findViewById(R.id.username);
//        TextView textTwo = (TextView) headerView.findViewById(R.id.email_address);
    }


    /*  Method for Navigation View item selection  */
    private void onMenuItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                //Check and un-check menu item if they are checkable behaviour
                if (item.isCheckable()) {
                    if (item.isChecked()) item.setChecked(false);
                    else item.setChecked(true);
                }

                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {

                    case R.id.home:
                        Fragment homeFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (homeFragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new HomeFragment(), item.getTitle().toString()).commit();
                        break;
                    case R.id.dashboard:
                        //Replace fragment
                        Fragment home1Fragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (home1Fragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new DashboardFragment(), item.getTitle().toString()).commit();

                        break;


                    case R.id.my_profile:
                        //Replace fragment
                        Fragment myProfileFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (myProfileFragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new MyProfileFragment(), item.getTitle().toString()).commit();

                        break;
                    case R.id.shortlist:

                        Fragment shortListFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (shortListFragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new ShortListFragment(), item.getTitle().toString()).commit();

                        //Replace fragment
                        break;

                    //sumeeth had done sold car

                    case R.id.sold:

                        Fragment SoldFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (SoldFragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new SoldFragment(), item.getTitle().toString()).commit();

                        //Replace fragment
                        break;

                    case R.id.settings:
                        //Replace fragment


                        break;
                    case R.id.Reports:
                        //Replace fragment


                        break;
                    case R.id.Contact_us:
                        //Replace fragment
                        Fragment contactUsFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (contactUsFragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new Contact_Us_Fragment(), item.getTitle().toString()).commit();

                        break;
                    case R.id.logout:
                        //Replace fragment
                        doLogout();
                        break;
                    case R.id.search_car_link:
                        Fragment search_car_fragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (search_car_fragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new SearchFragment(), item.getTitle().toString()).commit();
                        break;
                  /*  case R.id.Leads:
                        //Replace fragment
                        Fragment LeadsFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (LeadsFragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new Leads_Fragment(), item.getTitle().toString()).commit();

                        break;
                    case R.id.Requirements:
                        //Replace fragment
                        Fragment RequirementsFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
                        if (RequirementsFragment == null)
                            fragmentManager.beginTransaction().replace(R.id.frame_container, new Requirements_Fragment(), item.getTitle().toString()).commit();

                        break;*/
                    /*case R.id.AUCTION:
                        //Replace fragment


                        break;
                    case R.id.Finance:
                        //Replace fragment


                        break;
                    case R.id.Market:
                        //Replace fragment


                        break;

                    case R.id.Loans:
                        //Replace fragment


                        break;

                    case R.id.insurance:
                        //Replace fragment


                        break;*/


                    case R.id.share_app:

                        //Start new Activity or do your stuff
                        new Share_Rate_App_Class().shareText(MainActivity.this);

                        Toast.makeText(MainActivity.this, "You Clicked on \"" + item.getTitle().toString() + "\" menu item.", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rate_app:
                        //Start new Activity or do your stuff
                        new Share_Rate_App_Class().RateApp(MainActivity.this);

                        Toast.makeText(MainActivity.this, "You Clicked on \"" + item.getTitle().toString() + "\" menu item.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about_us:
                        Intent intent = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(intent);
                        break;
                    //Start new Activity or do your stuff
                    case R.id.privacy_and_policy:
                        Intent intent1 = new Intent(MainActivity.this, PrivacyPolicy.class);
                        startActivity(intent1);
                        //Start new Activity or do your stuff


                        Toast.makeText(MainActivity.this, "You Clicked on \"" + item.getTitle().toString() + "\" menu item.", Toast.LENGTH_SHORT).show();

                        break;

                }

                return false;
            }
        });
    }

    private void doLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).logoutUser();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }

    private void replaceDashboardFragment(MenuItem item) {
        Fragment dashboardFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
        if (dashboardFragment == null)
            fragmentManager.beginTransaction().replace(R.id.frame_container, new HomeFragment(), item.getTitle().toString()).commit();
    }


    //On backs press check if drawer is open and closed
    @Override
    public void onBackPressed() {
        MenuItem item = navigationView.getMenu().findItem(R.id.home);

        Fragment dashBoardFragment = fragmentManager.findFragmentByTag(item.getTitle().toString());
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
            mDrawerLayout.closeDrawers();
        else if (dashBoardFragment == null) {
            navigationView.setCheckedItem(R.id.home);
            replaceDashboardFragment(item);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchMenu = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);
        searchView.setQueryHint("Search your cars here....");
        SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals(""))
                    startActivity(new Intent(MainActivity.this, SearchResultActivity.class).putExtra("query", query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(textListener);
        return super.onCreateOptionsMenu(menu);
    }


}
