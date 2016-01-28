package android.ht.sportstatistik.activities;

import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Stats;
import android.ht.sportstatistik.helper.StatsTeamAdaper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.ht.sportstatistik.R;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsActivity extends ActionBarActivity {

    DatabaseHelper dbh;
    private StatsTeamAdaper adapter;
    List<Stats> testGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.expendableStatsTeam);
        dbh = DatabaseHelper.getInstance(getApplicationContext());

        //testGroups = statTestGroup();

        adapter = new StatsTeamAdaper(getApplicationContext(), dbh.getAllActionsFromTeam(dbh.getTeam(1)), statTestItem());
        Log.d("Stats", "Größe Adapter " + adapter.getGroupCount());
        elv.setAdapter(adapter);
    }

    public List<Integer> statTestGroup(){
        List<Integer> groupStats = new ArrayList<Integer>();

        groupStats.add(1);
        groupStats.add(2);
        groupStats.add(3);
        groupStats.add(4);
        return groupStats;
    }

    public HashMap<Integer, List<Integer>> statTestItem(){
        HashMap<Integer, List<Integer>> itemStats = new HashMap<Integer, List<Integer>>();
        List<Integer> temp = new ArrayList<Integer>();
        temp.add(1);
        temp.add(2);
        temp.add(3);
        for(Integer i : dbh.getAllActionsFromTeam(dbh.getTeam(1))){
            itemStats.put(i, temp);
        }

        return itemStats;
    }

}
