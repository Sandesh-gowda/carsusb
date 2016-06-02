package sample.com.carusb.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import sample.com.carusb.adapters.Car_Model_Adapter;
import sample.com.carusb.model.Dialog_Models;

/**
 * Created by Dell on 4/13/2016.
 */
public class AlertDialog_Class {
    // List Item Dialog
    public static void selectStateDialog(Context context, final TextView textView, final String[] listData) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // builder.setTitle("Select state");
        builder.setCancelable(true);

        // Setting list items over dialog and there should be no message
        builder.setItems(listData, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int pos) {
                textView.setText(listData[pos]);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static String selectCarDialog(Context context, final TextView textView, final ArrayList<Dialog_Models> dialog_modelses) {
        final String[] id = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // builder.setTitle("Select state");
        builder.setCancelable(true);

        Car_Model_Adapter adapter = new Car_Model_Adapter(context, dialog_modelses);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(dialog_modelses.get(which).getTitle());
                id[0] = dialog_modelses.get(which).getId();
                Log.e("IDS", id[0] + " - - " + dialog_modelses.get(which).getId());

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return id[0];
    }


}
