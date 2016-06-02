package sample.com.carusb.requests;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kalyan_pvs on 27/2/16.
 */
public interface OnitemClickLIstener {

    void onItemClick(RecyclerView.Adapter adapter, View view, int position);

}
