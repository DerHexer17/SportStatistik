package android.ht.sportstatistik.helper;

import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Stats;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by heth on 28.01.2016.
 */
public class StatsTeamAdaper extends BaseExpandableListAdapter {

    private Context context;
    private List<Integer> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Integer, List<Stats>> listDataChild;
    DatabaseHelper dbh;

    public StatsTeamAdaper(Context context, List<Integer> listDataHeader,
                           HashMap<Integer, List<Stats>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Stats getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ex_list_group_stats_team, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.label);
        DecimalFormat f = new DecimalFormat("#0.00");
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        Stats stat = dbh.getStatsActionForTeam((Integer) getGroup(groupPosition), null);
        lblListHeader.setText(stat.getTitle()+" | Summe: " +stat.getSum()+" | Schnitt: " + f.format(stat.getAverage()));
        Log.d("AVG", "Double: "+stat.getAverage());

        Log.d("Stats", dbh.getStatsActionForTeam(1, dbh.getTeam(1)).getTitle() + " Summe: " + dbh.getStatsActionForTeam(1, dbh.getTeam(1)).getSum());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //final String childText = "Child ID: "+getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ex_list_item_stats_team, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.label);
        Stats stat = getChild(groupPosition, childPosition);

        txtListChild.setText(stat.getSpieler().getVorname()+" | Summe: "+stat.getSum()+" | Schnitt: "+stat.getAverage());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        if(getChildrenCount(groupPosition) > 0){
            return true;
        }else{
            return false;
        }
    }
}
