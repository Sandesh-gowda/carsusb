package sample.com.carusb.home_fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import sample.com.carusb.R;
import sample.com.carusb.fragments.BaseFragment;
import sample.com.carusb.main.MyApplication;
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.End_Points;

/**
 * Created by Dell on 4/13/2016.
 */
public class BuyCarFragment extends BaseFragment {

    private View view;

    public BuyCarFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_stock_fragment, container, false);
        initRecyclerView();
        return view;
    }

    @Override
    public void onFragmentVisible() {
        super.onFragmentVisible();
        fetchDataTask();
    }


    private void fetchDataTask() {
        if (MyApplication.getInstance().IsInternetConnected()) {
            if (responseData == null) {
                makebuyCarCall();
            }
        } else {
            noStock();
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }


    private void initRecyclerView() {
        progressBar = (ProgressBar) view.findViewById(R.id.my_stock_progressbar);
        empty_stock = (TextView) view.findViewById(R.id.empty_stock);

        recyclerView = (RecyclerView)
                view.findViewById(R.id.my_stock_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new GridLayoutManager(getActivity(), 1));// Here 2 is no. of columns to be displayed
    }

    private void makebuyCarCall() {
        progressBar.setVisibility(View.VISIBLE);
        final User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("dealer_id", user.getUser_id());
        params.put("state", user.getUser_state());
        makeCall(End_Points.BUY_CAR_URL, params);
    }


}
