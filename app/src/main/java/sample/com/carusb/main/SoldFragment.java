package sample.com.carusb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import sample.com.carusb.model.User;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.End_Points;

/**
 * Created by Dell on 5/19/2016.
 */
public class SoldFragment extends BaseFragment {

    private View view;

    public  SoldFragment(){

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_stock_fragment, container, false);
        initRecyclerView();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchDataTask();
    }



    private void fetchDataTask() {
        if (MyApplication.getInstance().IsInternetConnected()) {
            if (responseData == null) {
                makeSoldCall();
            } else {
                setData(responseData);
            }
        } else {
            noStock();
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }



    private void makeSoldCall() {
        progressBar.setVisibility(View.VISIBLE);
        User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dealer_id", user.getUser_id());
       
        makeCall(End_Points.CAR_SOLD, params);
    }


    private void initRecyclerView() {

        progressBar = (ProgressBar) view.findViewById(R.id.my_stock_progressbar);
        empty_stock = (TextView) view.findViewById(R.id.empty_stock);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView)
                view.findViewById(R.id.my_stock_recycler_view);
        recyclerView
                .setLayoutManager(layoutManager);
    }




}
