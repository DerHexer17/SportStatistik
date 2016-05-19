package hight.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Player;

/**
 * Created by heth on 18.05.2016.
 */
public class PlayerSpinnerAdapter implements SpinnerAdapter {

    Context context;
    List<Player> players;
    DatabaseHelper dbh;

    public PlayerSpinnerAdapter(Context c, List<Player> players){
        context = c;
        this.players = players;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_team_color_item, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.label);
        txt.setText(getItem(position).getVorname()+" "+getItem(position).getNachname());

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
        return players.size();
    }

    @Override
    public Player getItem(int position) {
        return players.get(position);
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
        txt.setText(getItem(position).getVorname()+" "+getItem(position).getNachname());

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
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
