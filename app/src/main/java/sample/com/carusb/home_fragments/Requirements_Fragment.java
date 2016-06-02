package sample.com.carusb.home_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sample.com.carusb.R;


public class Requirements_Fragment extends Fragment {

    private static View view;




    private static ViewPager viewPager;

    public Requirements_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.requirements_fragment, container, false);
        // initViews();
        return view;
    }


}

