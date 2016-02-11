package hight.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by heth on 27.01.2016.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    public NavigationDrawerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(hight.ht.sportstatistik.R.layout.list_item_navigation_drawer, null);
        }

        TextView txt = (TextView) convertView.findViewById(hight.ht.sportstatistik.R.id.label);
        txt.setText(getItem(position));

        return convertView;

    }
}
