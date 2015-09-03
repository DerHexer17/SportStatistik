package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.activities.SpielActivity;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.ht.sportstatistik.datahandling.Spiel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hendrik on 03.09.2015.
 */
public class ActionAdapter extends ArrayAdapter<Ereignis> {
    DatabaseHelper dbh;

    public ActionAdapter(Context context, int resource, List<Ereignis> objects) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_spiele, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);

        txtTitle.setText(getItem(position).getName());

        return convertView;
    }
}
