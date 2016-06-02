package sample.com.carusb.home_fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sample.com.carusb.R;
import sample.com.carusb.adapters.ViewPagerAdapter;
import sample.com.carusb.fragments.BaseFragment;

/**
 * Created by Dell on 3/30/2016.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private final String TABS[] = {"Hub", "Add Car", "My Stock", "Buy Car"};

    HubFragment hubFragment = new HubFragment();
    AddCarFragment addCarFragment = new AddCarFragment();
    MyStockFragment myStockFragment = new MyStockFragment();
    BuyCarFragment buyCar_fragment = new BuyCarFragment();

    private ArrayList<Fragment> list;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPager();
    }

    private void initPager() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);

        list = new ArrayList<>();
        list.add(hubFragment);
        list.add(addCarFragment);
        list.add(myStockFragment);
        list.add(buyCar_fragment);
        adapter = new ViewPagerAdapter(getChildFragmentManager(), list, TABS);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public Fragment getFragment(int pos) {
        return adapter.getItem(pos);
    }

    public void setCurrentFragment(int position) {
        viewPager.setCurrentItem(position);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.e("Activity", "Result OK");
            Fragment home = getFragment(1);
            if (home != null) {
                if (requestCode == AddCarFragment.REQUEST_CAMERA) {
                    addCarFragment.onCaptureImageResult(data, getActivity());
                    addCarFragment.showViewPager();
                }
                if (requestCode == AddCarFragment.SELECT_FILE) {
                    addCarFragment.onSelectFromGalleryResult(data, getActivity());
                    addCarFragment.showViewPager();
                }
                if (requestCode == AddCarFragment.PICK_INSURANCE_REQUEST_CODE)
                    addCarFragment.showInsuranceFilePath(data, getActivity());
                if (requestCode == AddCarFragment.PICK_RCBOOK_REQUEST_CODE)
                    addCarFragment.showRCBookFilePath(data, getActivity());
            }


        } else
            Log.e("Activity", "Result Fail");
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        try {
            Fragment fragment = list.get(position);
            if (fragment instanceof BaseFragment) {
                BaseFragment baseFragment = (BaseFragment) fragment;
                baseFragment.onFragmentVisible();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
