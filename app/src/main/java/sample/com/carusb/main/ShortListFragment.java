package sample.com.carusb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sample.com.carusb.R;
import sample.com.carusb.adapters.StockAdapter;
import sample.com.carusb.model.Stock;
import sample.com.carusb.preference_manager.MyPreferenceManager;

/**
 * Created by kalyan_pvs on 14/5/16.
 */
public class ShortListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView empty_stock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_stock_fragment, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
//        arrayList = new ArrayList<>();

//        progressBar = (ProgressBar) getView().findViewById(R.id.my_stock_progressbar);
        empty_stock = (TextView) getView().findViewById(R.id.empty_stock);

        recyclerView = (RecyclerView)
                getView().findViewById(R.id.my_stock_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new GridLayoutManager(getActivity(), 1));// Here 2 is no. of columns to be displayed

        MyPreferenceManager preferenceManager = new MyPreferenceManager(getActivity(), "shortlist");
        final ArrayList<Stock> shortList = preferenceManager.getShortList();
        if (shortList != null && shortList.size() > 0) {
            final StockAdapter adapter = new StockAdapter(getActivity(), shortList, false);
            havingSomeStock();
            recyclerView.setAdapter(adapter);



              /*sumeeth code for swipe to dismiss */


            // init swipe to dismiss logic
            ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    // callback for drag-n-drop, false to skip this feature
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    // callback for swipe to dismiss, removing item from data and adapter
                    shortList.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

//                  here it is clearing all of it .

                    MyPreferenceManager preferenceManager = new MyPreferenceManager(getActivity(), "shortlist");
                    preferenceManager.clearShortlIst();
                }
            });
            swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);


        } else {
            noStock();
        }






    }


    private void noStock() {
        empty_stock.setVisibility(View.VISIBLE);
        empty_stock.setText("Currently you don\'t have any Stock.");
        recyclerView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
    }

    private void havingSomeStock() {
        empty_stock.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.GONE);
    }


}
