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
import hight.ht.sportstatistik.datahandling.Team;

/**
 * Created by heth on 24.02.2017.
 */

public class TeamStatsFilterSpinner implements SpinnerAdapter {

    Context context;
    List<Team> teams;

    public TeamStatsFilterSpinner(Context c, List<Team> t){
        this.context = c;
        Team all = new Team();
        all.setLang_name("Alle");
        all.setKurz_name("all");
        t.add(0, all);
        this.teams = t;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_team, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.label);
        Team team = (Team) getItem(position);
        txt.setText(team.getLang_name());

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
        return teams.size();
    }

    @Override
    public Object getItem(int position) {
        return teams.get(position);
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
            convertView = mInflater.inflate(R.layout.list_item_team, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.label);
        Team team = (Team) getItem(position);
        txt.setText(team.getLang_name());

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
