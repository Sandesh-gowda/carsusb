package sample.com.carusb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sample.com.carusb.R;
import sample.com.carusb.model.Dialog_Models;

/**
 * Created by Dell on 4/18/2016.
 */
public class Car_Model_Adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Dialog_Models> dialog_modelses;

    public Car_Model_Adapter(Context context, ArrayList<Dialog_Models> dialog_modelses) {
        this.context = context;
        this.dialog_modelses = dialog_modelses;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dialog_modelses.size();
    }

    @Override
    public Object getItem(int position) {
        return dialog_modelses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.car_selection_custom_view, parent, false);
        TextView title = (TextView) convertView.findViewById(R.id.car_select_text);
        title.setText(dialog_modelses.get(position).getTitle());
        return convertView;
    }


}
