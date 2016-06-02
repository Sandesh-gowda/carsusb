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
import sample.com.carusb.model.Stock;
import sample.com.carusb.model.User;
import sample.com.carusb.requests.OnitemClickLIstener;
import sample.com.carusb.utils.Constants;
import sample.com.carusb.utils.End_Points;


public class MyStockFragment extends BaseFragment implements OnitemClickLIstener {

    private View view;

    public MyStockFragment() {
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
                makeHubCall();
            } else {
                setData(responseData);
            }
        } else {
            noStock();
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void setData(String response) {
        super.setData(response);
        if (adapter != null) {
            adapter.setOnItemClickListener(this);
        }
    }

    private void makeHubCall() {
        progressBar.setVisibility(View.VISIBLE);
        User user = MyApplication.getInstance().getPrefManager(Constants.Login_Preferences).getUser();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dealer_id", user.getUser_id());
        makeCall(End_Points.MY_STOCK_URL, params);

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

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
        try {
            Stock stock = carsList.get(position);
            HomeFragment fragment = (HomeFragment) getParentFragment();
            fragment.addCarFragment.setEditData(stock);


            fragment.setCurrentFragment(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

