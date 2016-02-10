package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.ht.sportstatistik.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by heth on 10.02.2016.
 */
public class TeamColorSpinnerAdapter implements SpinnerAdapter {

    Context context;
    ArrayList<String> colors;
    Map<String, String> map;

    public TeamColorSpinnerAdapter(Context c, ArrayList<String> list, Map<String, String> m){
        this.context = c;
        this.colors = list;
        this.map = m;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_team_color_item, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.label);
        txt.setText(String.valueOf(map.get(getItem(position))));

        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_team_color_item, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.label);
        txt.setText(String.valueOf(map.get(getItem(position))));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
